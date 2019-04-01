package com.wplatform.wizardcraft.network;

import com.wplatform.wizardcraft.util.SysProperties;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class NetworkTransport {

    private static final short[] XOR_3_KEYS = new short[]{0xFC, 0xCF, 0xAB};


    private static final Charset CHARSET = Charset.forName(SysProperties.TRANSPORT_CHARSET);

    private final ByteBuf input;
    private final ByteBuf output;
    private final NetworkConnection connection;


    public NetworkTransport(NetworkConnection connection, ByteBuf outputPacket) {
        this(connection, null, outputPacket);
    }

    public NetworkTransport(NetworkConnection connection, ByteBuf receivedPacket, ByteBuf outputPacket) {
        this.connection = connection;
        this.input = receivedPacket;
        this.output = outputPacket;
    }

    public NetworkConnection getConnection() {
        return connection;
    }

    public ByteBuf getInput() {
        return input;
    }

    public ByteBuf getOutput() {
        return output;
    }

    public void writeByte(int value) {
        output.writeByte(value);
    }

    public void writeShort(int value) {
        output.writeShort(value);
    }

    public void writeInt(int value) {
        output.writeInt(value);
    }

    public ByteBuf writeShortLE(int value) {
        return output.writeShortLE(value);
    }

    public void writeLong(long value) {
        output.writeLong(value);
    }

    public void writeFloat(float value) {
        output.writeFloat(value);
    }

    public void writeDouble(double value) {
        output.writeDouble(value);
    }

    public void writeBytes(byte[] src) {
        output.writeBytes(src);
    }

    public void writeString(String value) {
        output.writeBytes(value.getBytes(CHARSET));
    }

    public void writeFixLengthString(String value, int len) {
        byte[] targetByte = new byte[len];
        byte[] bytes = value.getBytes(CHARSET);
        int length = bytes.length > len ? len : bytes.length;
        System.arraycopy(bytes, 0, targetByte, 0, length);
        output.writeBytes(targetByte);
    }


    public short readUnsignedByte() {
        return input.readUnsignedByte();
    }

    public int readUnsignedShort() {
        return input.readUnsignedShort();
    }


    public String decodeAndReadString(int len) {
        len = input.readableBytes() > len ? len : input.readableBytes();
        ByteBuf byteBuf = input.readSlice(len);
        xor3(byteBuf);
        byte[] datas = new byte[len];
        byteBuf.readBytes(datas);
        input.skipBytes(len);
        return new String(datas, CHARSET);
    }

    public String readString(int len) {
        len = input.readableBytes() > len ? len : input.readableBytes();
        byte[] datas = new byte[len];
        input.readBytes(datas);
        return new String(datas, CHARSET);
    }

    public void skipRead(int len) {
        input.skipBytes(len);
    }



    private static void xor3(ByteBuf packet) {
        for (int i = 0; i < packet.readableBytes(); i++) {
            packet.setByte(i, packet.getUnsignedByte(i) ^ XOR_3_KEYS[i % 3]);
        }
    }

}
