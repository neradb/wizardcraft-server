package com.wplatform.wizardcraft.proto;

import io.netty.buffer.ByteBuf;

public class LoginRequest extends Packet {

    private int subcode;//BYTE subcode;	// 3
    private String id;//char Id[10];	// 4
    private String password; //char Pass[20];	// E
    private String hwid;//char HWID[100]; // NEW
    private int tickCount;//DWORD TickCount;	// 18
    private byte[] version;//BYTE CliVersion[5];	// 1C
    private byte[] serial;//BYTE CliSerial[16];	// 21
    private byte serverSeason;//DWORD ServerSeason;

    public LoginRequest() {
        this.header = new Header(Header.C1,LoginLogoutGroup);
    }

    @Override
    protected void readPayload(ByteBuf buff) {

    }

    @Override
    protected void writePayload(ByteBuf buff) {

    }


}
