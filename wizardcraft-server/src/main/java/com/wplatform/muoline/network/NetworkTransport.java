package com.wplatform.muoline.network;

import com.wplatform.muoline.util.SysProperties;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class NetworkTransport {

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

    public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
        return output.writeBytes(src, srcIndex, length);
    }

    public byte readByte() {
        return input == null ? 0 : input.readByte();
    }

    public short readUnsignedByte() {
        return input == null ? 0 : input.readUnsignedByte();
    }

    public int readUnsignedShort() {
        return input.readUnsignedShort();
    }

    public short readShort() {
        return input == null ? 0 : input.readShort();
    }

    public int readInt() {
        return input == null ? 0 : input.readInt();
    }

    public long readLong() {
        return input == null ? 0 : input.readLong();
    }

    public void skipRead(int len) {
        input.skipBytes(len);
    }

    public CharSequence readCharSequence(int length) {
        return input.readCharSequence(length, CHARSET);
    }


}
