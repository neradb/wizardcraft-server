package com.wplatform.wizardcraft.proto;

import io.netty.buffer.ByteBuf;

public class Header {

    public static final int C1 = 0xC1;
    public static final int C2 = 0xC2;
    public static final int C3 = 0xC3;
    public static final int C4 = 0xC4;

    private int type;

    private int packetLength;

    private int opcode;

    public Header() {
    }

    public Header(int type, int opcode) {
        this.type = type;
        this.opcode = opcode;
    }

    public int getType() {
        return type;
    }

    public int getPacketLength() {
        return packetLength;
    }

    public int getOpcode() {
        return opcode;
    }



    public static Header readFrom(ByteBuf buff) {
        Header header = new Header();
        header.read(buff);
        return header;
    }

    public void read(ByteBuf buff) {
        type = buff.readUnsignedByte();
        switch (type) {
            case C1:
            case C3:
                packetLength = buff.readUnsignedByte();
                break;
            case C2:
            case C4:
                packetLength = buff.readShort();
                break;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(type));
        }
        opcode = buff.readUnsignedByte();

    }

    public void write(ByteBuf buff) {
        switch (type) {
            case C1:
            case C3:
                buff.writeByte(type);
                buff.writeByte(packetLength);
                break;
            case C2:
            case C4:
                buff.writeByte(type);
                buff.writeShort(packetLength);
                break;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(type));
        }
        buff.writeByte(opcode);
    }
}
