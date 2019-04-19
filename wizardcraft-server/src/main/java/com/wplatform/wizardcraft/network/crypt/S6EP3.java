package com.wplatform.wizardcraft.network.crypt;

import com.wplatform.wizardcraft.proto.Proto;
import com.wplatform.wizardcraft.util.SysProperties;
import io.netty.buffer.*;

public final class S6EP3 {

    private static short[] XOR_32_KEYS = new short[]{
            0xAB, 0x11, 0xCD, 0xFE, 0x18, 0x23, 0xC5, 0xA3,
            0xCA, 0x33, 0xC1, 0xCC, 0x66, 0x67, 0x21, 0xF3,
            0x32, 0x12, 0x15, 0x35, 0x29, 0xFF, 0xFE, 0x1D,
            0x44, 0xEF, 0xCD, 0x41, 0x26, 0x3C, 0x4E, 0x4D
    };

    private static final Keys C3C4_DECRYPT_KEY = Keys.createDecryptionKeys(new int[]{
            128079, 164742, 70235, 106898, 31544, 2047, 57011, 10183, 48413, 46165, 15171, 37433
    });


    private static final Keys C3C4_ENCRYPT_KEY = Keys.createEncryptionKeys(new int[]{
            73326, 109989, 98843, 171058, 13169, 19036, 35482, 29587, 62004, 64409, 35374, 64599
    });


    /// <summary>
    /// The decrypted block size.
    /// </summary>
    private static final int DECRYPTED_BLOCK_SIZE = 8;

    /// <summary>
    /// The encrypted block size.
    /// It is bigger than the decrypted size, because it contains the length of the actual data of the block and a checksum.
    /// </summary>
    private static final int ENCRYPTED_BLOCK_SIZE = 11;

    /// <summary>
    /// The xor key which is used as to 'encrypt' the size of each block.
    /// </summary>
    private static final byte BLOCK_SIZE_XOR_KEY = 0x3D;

    /// <summary>
    /// The xor key which is used as to 'encrypt' the checksum of each encrypted block.
    /// </summary>
    private static final short BLOCK_CHECK_SUM_XOR_KEY = 0xF8;

    private static final int BITS_PER_BYTE = 8;

    private static final int BITS_PER_VALUE = (BITS_PER_BYTE * 2) + 2;

    private static final ByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;


    public static ByteBuf deCrypt(ByteBuf packet, short serial) {
        packet = deCryptC3C4(packet, serial);
        packet = decryptC1C2(packet);
        return packet;
    }

    public static ByteBuf enCrypt(ByteBuf packet, short serial) {
        packet = enCryptC3C4(packet, serial);
        return packet;
    }


    private static ByteBuf deCryptC3C4(ByteBuf packet, short serial) {
        if (packet.getUnsignedByte(0) < 0xC3) {
            return packet;
        }
        try {
            int contentSize = getContentSize(packet, false);
            if ((contentSize % ENCRYPTED_BLOCK_SIZE) != 0) {
                throw new IllegalStateException(
                        "The packet has an unexpected content size " + contentSize + ". It must be a multiple of " + ENCRYPTED_BLOCK_SIZE);
            }
            int maximumDecryptedSize = getMaximumDecryptedSize(packet);
            int encryptedHeaderSize = Proto.getPacketHeaderSize(packet);
            int decryptedHeaderSize = encryptedHeaderSize - 1;
            ByteBuf result = alloc.buffer(maximumDecryptedSize, maximumDecryptedSize);
            packet.readerIndex(encryptedHeaderSize);
            result.writerIndex(decryptedHeaderSize);
            int decryptedContentSize = decryptPacketContent(packet, result, serial);
            int length = decryptedHeaderSize + decryptedContentSize;
            setPacketHead(result, packet.getUnsignedByte(0), length);
            result = result.slice(0, length);
            return result;
        } finally {
            packet.release();
        }
    }


    private static ByteBuf decryptC1C2(ByteBuf packet) {
        int headerSize = Proto.getPacketHeaderSize(packet);
        short packetSize = Proto.getPacketSize(packet);
        for (int i = packetSize - 1; i > headerSize; i--) {
            short b = (short) (packet.getUnsignedByte(i) ^ packet.getUnsignedByte(i - 1) ^ XOR_32_KEYS[i % 32]);
            packet.setByte(i, b & 0xFF);
        }
        return packet;
    }


    /// <inheritdoc />
    private static ByteBuf enCryptC3C4(ByteBuf packet, short serial) {
        if (packet.getUnsignedByte(0) < 0xC3) {
            return packet;
        }
        try {
            int encryptedSize = getEncryptedSize(packet);
            ByteBuf result = alloc.buffer(encryptedSize, encryptedSize);
            setPacketHead(result, packet.getUnsignedByte(0), encryptedSize);
            // encrypting the content:
            int headerSize = Proto.getPacketHeaderSize(packet);
            //var input = packet.Slice(headerSize);
            packet.readerIndex(headerSize);
            result.writerIndex(headerSize);
            encryptPacketContent(packet, result, serial);
            result.readerIndex(0);
            result = result.writerIndex(encryptedSize);

            return result;
        } finally {
            packet.release();
        }
    }


    private static int decryptPacketContent(ByteBuf input, ByteBuf output, short serial) {
        int sizeCounter = 0;
        long[] ringBuffer = new long[4];
        do {
            //rest.Slice(0, ENCRYPTED_BLOCK_SIZE).CopyTo(this.inputBuffer);
            ByteBuf inputBlock = input.slice(input.readerIndex(), ENCRYPTED_BLOCK_SIZE);
            ByteBuf outputBlock = output.slice(output.writerIndex(), DECRYPTED_BLOCK_SIZE);
            int blockSize = decryptBlock(inputBlock, outputBlock, ringBuffer);
            short inputSerial = outputBlock.getUnsignedByte(0);
            if (sizeCounter == 0 && inputSerial != serial) {
                throw new IllegalArgumentException("invalid packet counter exception");
            }
            sizeCounter += blockSize;
            output.writerIndex(output.writerIndex() + blockSize);
            input.readerIndex(input.readerIndex() + ENCRYPTED_BLOCK_SIZE);
        }
        while (input.readableBytes() > 0);
        return sizeCounter;
    }

    /// <summary>
    /// Decrypts the block.
    /// </summary>
    /// <param name="outputBuffer">The output buffer array.</param>
    /// <returns>The decrypted length of the block.</returns>
    private static int decryptBlock(ByteBuf inputBlock, ByteBuf outputBlock, long[] ringBuffer) {
        ringBuffer[0] = readInputBuffer(inputBlock, 0);
        ringBuffer[1] = readInputBuffer(inputBlock, 1);
        ringBuffer[2] = readInputBuffer(inputBlock, 2);
        ringBuffer[3] = readInputBuffer(inputBlock, 3);

        //this.DecryptContent(outputBlock);
        Keys keys = C3C4_DECRYPT_KEY;
        ringBuffer[2] = ringBuffer[2] ^ keys.xorKey[2] ^ (ringBuffer[3] & 0xFFFF);
        ringBuffer[1] = ringBuffer[1] ^ keys.xorKey[1] ^ (ringBuffer[2] & 0xFFFF);
        ringBuffer[0] = ringBuffer[0] ^ keys.xorKey[0] ^ (ringBuffer[1] & 0xFFFF);

        //var output = MemoryMarshal.Cast<byte, ushort>(outputBuffer);

        int s0 = (int) (keys.xorKey[0] ^ ((ringBuffer[0] * keys.decryptKey[0]) % keys.modKey[0]));
        int s1 = (int) (keys.xorKey[1] ^ ((ringBuffer[1] * keys.decryptKey[1]) % keys.modKey[1]) ^ (ringBuffer[0] & 0xFFFF));
        int s2 = (int) (keys.xorKey[2] ^ ((ringBuffer[2] * keys.decryptKey[2]) % keys.modKey[2]) ^ (ringBuffer[1] & 0xFFFF));
        int s3 = (int) (keys.xorKey[3] ^ ((ringBuffer[3] * keys.decryptKey[3]) % keys.modKey[3]) ^ (ringBuffer[2] & 0xFFFF));

        outputBlock.writerIndex(0);
        outputBlock.writeShortLE(s0 & 0xFFFF);
        outputBlock.writeShortLE(s1 & 0xFFFF);
        outputBlock.writeShortLE(s2 & 0xFFFF);
        outputBlock.writeShortLE(s3 & 0xFFFF);
        //return this.DecodeFinal(outputBlock);

        short[] blockSuffix = new short[2];
        blockSuffix[0] = inputBlock.getUnsignedByte(ENCRYPTED_BLOCK_SIZE - 2);
        blockSuffix[1] = inputBlock.getUnsignedByte(ENCRYPTED_BLOCK_SIZE - 1);
        // blockSuffix[0] -> block size (encrypted)
        // blockSuffix[1] -> checksum
        short blockSize = (short) (blockSuffix[0] ^ blockSuffix[1] ^ BLOCK_SIZE_XOR_KEY);
        short checksum = BLOCK_CHECK_SUM_XOR_KEY;
        for (int i = 0; i < DECRYPTED_BLOCK_SIZE; i++) {

            checksum ^= outputBlock.getUnsignedByte(i);
        }

        if (blockSuffix[1] != checksum && !SysProperties.ACCEPT_WRONG_BLOCK_CHECKSUM) {
            throw new IllegalStateException("Block checksum invalid. Expected: " + checksum + ". Actual: " + blockSuffix[1] + ".");
        }
        return blockSize;

    }

    private static long readInputBuffer(ByteBuf inputBlock, int resultIndex) {
        int byteOffset = (resultIndex * BITS_PER_VALUE) / BITS_PER_BYTE;
        int bitOffset = (resultIndex * BITS_PER_VALUE) % BITS_PER_BYTE;
        int firstMask = 0xFF >> ((resultIndex * BITS_PER_VALUE) % BITS_PER_BYTE);
        long result = 0;
        result += (((long) inputBlock.getUnsignedByte(byteOffset++)) & firstMask) << (24 + bitOffset);
        result += (((long) inputBlock.getUnsignedByte(byteOffset++)) << (16 + bitOffset));
        result += (((long) inputBlock.getUnsignedByte(byteOffset)) & (0xFF << (8 - bitOffset))) << (8 + bitOffset);

        result = Integer.reverseBytes((int) result)/*result.SwapBytes()*/;

        int remainderMask = (0xFF << (6 - bitOffset) & 0xFF) - ((0xFF << (8 - bitOffset)) & 0xFF)/*GetRemainderBitMask(resultIndex)*/;
        int remainder = (inputBlock.getUnsignedByte(byteOffset)/*this.inputBuffer[byteOffset]*/ & remainderMask);
        result += (remainder << 16) >> (6 - bitOffset);

        return result;
    }

    private static void setPacketHead(ByteBuf packet, short head, int size) {
        packet.writerIndex(0);
        packet.writeByte(head & 0xFF);
        switch (head) {
            case 0xc3:
                packet.writeByte(size);
                break;
            case 0xC4:
                packet.writeShort(size);
                break;
        }
    }

    /// <summary>
    /// Gets the size of the content.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <param name="decrypted">if set to <c>true</c> it is decrypted. Encrypted packets additionally contain a counter.</param>
    /// <returns>The size of the actual content.</returns>
    private static int getContentSize(ByteBuf packet, boolean decrypted) {
        return Proto.getPacketSize(packet) - Proto.getPacketHeaderSize(packet) + (decrypted ? 1 : 0);
    }

    private static int getMaximumDecryptedSize(ByteBuf packet) {
        return ((getContentSize(packet, false) / ENCRYPTED_BLOCK_SIZE) * DECRYPTED_BLOCK_SIZE) + Proto.getPacketHeaderSize(packet) - 1;
    }


    /// <summary>
    /// Writes the encryption result into the target span.
    /// It basically squeezes the result (4 bytes) into 2 bytes and 2 bits (=18 bits).
    /// </summary>
    /// <param name="target">The target span.</param>
    /// <param name="resultIndex">Index of the result.</param>
    /// <param name="result">The encryption result value.</param>
    private static void writeBytesToResult(ByteBuf outputBlock, int resultIndex, long result) {
        int byteOffset = (resultIndex * BITS_PER_VALUE) / BITS_PER_BYTE;
        int bitOffset = (resultIndex * BITS_PER_VALUE) % BITS_PER_BYTE;
        int firstMask = 0xFF >> bitOffset;
        int swapped = Integer.reverseBytes((int) result);


        //target[byteOffset++] |= (byte) (swapped >> (24 + bitOffset) & firstMask);
        //target[byteOffset++] = (byte) (swapped >> (16 + bitOffset));
        //target[byteOffset] = (byte) ((swapped >> (8 + bitOffset)) & (0xFF << (8 - bitOffset)));
        int writeIndex = byteOffset;
        short b0 = (short) ((swapped >> (24 + bitOffset) & firstMask) | outputBlock.getUnsignedByte(writeIndex));
        short b1 = (short) (swapped >> (16 + bitOffset));
        short b2 = (short) ((swapped >> (8 + bitOffset)) & (0xFF << (8 - bitOffset)));


        int remainderMask = (0xFF << (6 - bitOffset) & 0xFF) - ((0xFF << (8 - bitOffset)) & 0xFF);
        long remainder = (result >> 16) << (6 - bitOffset);

        b2 |= (byte) (remainder & remainderMask);
        outputBlock.setByte(writeIndex, b0 & 0xFF);
        outputBlock.setByte(writeIndex + 1, b1 & 0xFF);
        outputBlock.setByte(writeIndex + 2, b2 & 0xFF);

    }


    private static int getEncryptedSize(ByteBuf packet) {
        int contentSize = getContentSize(packet, true);
        return (((contentSize / DECRYPTED_BLOCK_SIZE) + (((contentSize % DECRYPTED_BLOCK_SIZE) > 0) ? 1 : 0)) * ENCRYPTED_BLOCK_SIZE) + Proto.getPacketHeaderSize(packet);
    }

    private static void encryptPacketContent(ByteBuf input, ByteBuf output, short serial) {
        //int i = 0;
        //int sizeCounter = 0;
        //int size = input.readableBytes() + 1; // plus one for the counter

        // we handlePacket the first input block out of the loop, because we need to add the counter as prefix
        int size = input.readableBytes();
        int contentOfFirstBlockLength = Math.min(DECRYPTED_BLOCK_SIZE, input.readableBytes());
        long[] ringBuffer = new long[4];
        ByteBuf inputBlock = Unpooled.buffer(contentOfFirstBlockLength);
        //write serial number in first input block
        inputBlock.writeByte((byte) serial);
        inputBlock.writeBytes(input, input.readerIndex(), contentOfFirstBlockLength - 1);
        ByteBuf outputBlock = output.slice(output.writerIndex(), ENCRYPTED_BLOCK_SIZE);
        //encrypt first block
        encryptBlock(inputBlock, outputBlock, ringBuffer, contentOfFirstBlockLength);
        //i += DECRYPTED_BLOCK_SIZE;
        //sizeCounter += ENCRYPTED_BLOCK_SIZE;
        input.readerIndex(input.readerIndex() + contentOfFirstBlockLength - 1); //byte[0] is serial number.
        output.writerIndex(output.writerIndex() + ENCRYPTED_BLOCK_SIZE);

        // encrypt the rest of the blocks.
        while (input.readerIndex() <= size/*i < size*/) {
            int contentOfBlockLength = Math.min(DECRYPTED_BLOCK_SIZE, input.readableBytes());
            inputBlock = input.slice(input.readerIndex(), contentOfBlockLength);//input.Slice(i - 1, contentOfBlockLength).CopyTo(this.inputBuffer);
            //this.inputBuffer.AsSpan(contentOfBlockLength).Clear();
            outputBlock = output.slice(output.writerIndex(), ENCRYPTED_BLOCK_SIZE);
            encryptBlock(inputBlock, outputBlock, ringBuffer, contentOfBlockLength);
            //i += DECRYPTED_BLOCK_SIZE;
            //sizeCounter += ENCRYPTED_BLOCK_SIZE;
            input.readerIndex(input.readerIndex() + contentOfBlockLength);
            output.writerIndex(output.writerIndex() + ENCRYPTED_BLOCK_SIZE);
        }
    }

    private static void encryptBlock(ByteBuf inputBlock, ByteBuf outputBlock, long[] ringBuffer, int blockSize) {
        //outputBuffer.Clear(); // since the memory comes from the shared memory pool, it might not be initialized yet
        //this.EncryptContent();
        if (blockSize < DECRYPTED_BLOCK_SIZE) {
            inputBlock = Unpooled.wrappedBuffer(inputBlock,
                    Unpooled.wrappedBuffer(new byte[DECRYPTED_BLOCK_SIZE - blockSize]));
        }
        Keys keys = C3C4_ENCRYPT_KEY;
        //var input = MemoryMarshal.Cast < byte,ushort > (this.inputBuffer);
        int[] input = new int[4];
        input[0] = inputBlock.readUnsignedShortLE();
        input[1] = inputBlock.readUnsignedShortLE();
        input[2] = inputBlock.readUnsignedShortLE();
        input[3] = inputBlock.readUnsignedShortLE();


        ringBuffer[0] = ((keys.xorKey[0] ^ input[0]) * keys.encryptKey[0]) % keys.modKey[0];
        ringBuffer[1] = ((keys.xorKey[1] ^ (input[1] ^ (ringBuffer[0] & 0xFFFF))) * keys.encryptKey[1]) % keys.modKey[1];
        ringBuffer[2] = ((keys.xorKey[2] ^ (input[2] ^ (ringBuffer[1] & 0xFFFF))) * keys.encryptKey[2]) % keys.modKey[2];
        ringBuffer[3] = ((keys.xorKey[3] ^ (input[3] ^ (ringBuffer[2] & 0xFFFF))) * keys.encryptKey[3]) % keys.modKey[3];

        ringBuffer[0] = ringBuffer[0] ^ keys.xorKey[0] ^ (ringBuffer[1] & 0xFFFF);
        ringBuffer[1] = ringBuffer[1] ^ keys.xorKey[1] ^ (ringBuffer[2] & 0xFFFF);
        ringBuffer[2] = ringBuffer[2] ^ keys.xorKey[2] ^ (ringBuffer[3] & 0xFFFF);


        //outputBuffer.Clear(); // since the memory comes from the shared memory pool, it might not be initialized yet
        //this.EncryptContent();

        writeBytesToResult(outputBlock, 0, ringBuffer[0]);
        writeBytesToResult(outputBlock, 1, ringBuffer[1]);
        writeBytesToResult(outputBlock, 2, ringBuffer[2]);
        writeBytesToResult(outputBlock, 3, ringBuffer[3]);
        encryptFinalBlockByte(inputBlock, outputBlock, blockSize);
    }

    /// <summary>
    /// Encodes the final part of the block. It contains a checksum and the length of the block, which is needed for decryption.
    /// </summary>
    /// <param name="blockSize">The size of the block of decrypted data in bytes.</param>
    /// <param name="outputBuffer">The output buffer to which the encrypted result will be written.</param>
    private static void encryptFinalBlockByte(ByteBuf inputBlock, ByteBuf outputBlock, int blockSize) {
        short size = (short) (blockSize ^ BLOCK_SIZE_XOR_KEY);
        short checksum = BLOCK_CHECK_SUM_XOR_KEY;
        for (int i = 0; i < blockSize; i++) {
            checksum ^= inputBlock.getUnsignedByte(i);
        }
        size ^= checksum;
        outputBlock.setByte(outputBlock.capacity() - 2, size);
        outputBlock.setByte(outputBlock.capacity() - 1, checksum);
        //outputBuffer[outputBuffer.Length - 2] = size;
        //outputBuffer[outputBuffer.Length - 1] = checksum;
    }


    public static class Keys {

        // Gets the modulus key. These values are used for the modulus operation in the encryption/decryption handlePacket.
        public int[] modKey = new int[]{0, 0, 0, 0};

        // Gets the xor key. These values are used for a xor operation in the encryption/decryption handlePacket.

        public int[] xorKey = new int[]{0, 0, 0, 0};


        // Gets the encryption key. These values are used for the multiplication operation in the encryption handlePacket.
        public int[] encryptKey = new int[]{0, 0, 0, 0};


        // Gets the decrypt key. These values are used for the multiplication operation in the encryption handlePacket.
        public int[] decryptKey = new int[]{0, 0, 0, 0};


        // Creates an instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="DecryptKey"/>.

        // <param name="decryptionKey">The decryption key with 12 integers.</param>
        // <returns>An instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="DecryptKey"/>.</returns>
        public static Keys createDecryptionKeys(int[] decryptionKey) {
            Keys keys = new Keys();
            keys.modKey[0] = decryptionKey[0];
            keys.modKey[1] = decryptionKey[1];
            keys.modKey[2] = decryptionKey[2];
            keys.modKey[3] = decryptionKey[3];
            keys.decryptKey[0] = decryptionKey[4];
            keys.decryptKey[1] = decryptionKey[5];
            keys.decryptKey[2] = decryptionKey[6];
            keys.decryptKey[3] = decryptionKey[7];
            keys.xorKey[0] = decryptionKey[8];
            keys.xorKey[1] = decryptionKey[9];
            keys.xorKey[2] = decryptionKey[10];
            keys.xorKey[3] = decryptionKey[11];
            return keys;
        }


        // Creates an instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="EncryptKey"/>.

        // <param name="encryptionKey">The decryption key with 12 integers.</param>
        // <returns>An instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="EncryptKey"/>.</returns>
        public static Keys createEncryptionKeys(int[] encryptionKey) {
            Keys keys = new Keys();
            keys.modKey[0] = encryptionKey[0];
            keys.modKey[1] = encryptionKey[1];
            keys.modKey[2] = encryptionKey[2];
            keys.modKey[3] = encryptionKey[3];
            keys.encryptKey[0] = encryptionKey[4];
            keys.encryptKey[1] = encryptionKey[5];
            keys.encryptKey[2] = encryptionKey[6];
            keys.encryptKey[3] = encryptionKey[7];
            keys.xorKey[0] = encryptionKey[8];
            keys.xorKey[1] = encryptionKey[9];
            keys.xorKey[2] = encryptionKey[10];
            keys.xorKey[3] = encryptionKey[11];
            return keys;
        }

        // <inheritdoc />
        public String toString() {
            return String.format("Encryption Keys: %d, %d, %d, %d}, %d, %d, %d, %d, %d, %d, %d, %d\nDecryption Keys: %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d",
                    this.modKey[0], this.modKey[1], this.modKey[2], this.modKey[3], this.encryptKey[0], this.encryptKey[1], this.encryptKey[2], this.encryptKey[3], this.xorKey[0], this.xorKey[1], this.xorKey[2], this.xorKey[3],
                    this.modKey[0], this.modKey[1], this.modKey[2], this.modKey[3], this.decryptKey[0], this.decryptKey[1], this.decryptKey[2], this.decryptKey[3], this.xorKey[0], this.xorKey[1], this.xorKey[2], this.xorKey[3]);
        }


        // Gets the encryption key values for the <see cref="SimpleModulusEncryptor"/>.

        // <returns>The encryption key values for the <see cref="SimpleModulusEncryptor"/>.</returns>
        public long[] getEncryptionKeys() {
            long[] encryptionKeys = new long[12];
            System.arraycopy(modKey, 0, encryptionKeys, 0, modKey.length);
            System.arraycopy(encryptKey, 4, encryptionKeys, 0, modKey.length);
            System.arraycopy(xorKey, 8, encryptionKeys, 0, modKey.length);
            return encryptionKeys;
        }


        // Gets the decryption key values for the <see cref="SimpleModulusDecryptor"/>.

        // <returns>The decryption key values for the <see cref="SimpleModulusDecryptor"/>.</returns>
        public long[] getDecryptionKeys() {
            long[] encryptionKeys = new long[12];
            System.arraycopy(modKey, 0, encryptionKeys, 0, modKey.length);
            System.arraycopy(decryptKey, 4, encryptionKeys, 0, modKey.length);
            System.arraycopy(xorKey, 8, encryptionKeys, 0, modKey.length);
            return encryptionKeys;

        }
    }
}
