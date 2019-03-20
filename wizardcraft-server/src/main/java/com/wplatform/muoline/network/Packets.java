package com.wplatform.muoline.network;

import io.netty.buffer.ByteBuf;

public class Packets {


    public static int getPacketHeaderSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                return 2;
            case 0xC2:
            case 0xC4:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }

    public static short getPacketSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                return buff.getUnsignedByte(1);
            case 0xC2:
            case 0xC4:
                return buff.getShort(1);
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }

    public static void setPacketSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                buff.setByte(1, buff.readableBytes());
                break;
            case 0xC2:
            case 0xC4:
                buff.setShort(1, buff.readableBytes());
                break;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }

}
