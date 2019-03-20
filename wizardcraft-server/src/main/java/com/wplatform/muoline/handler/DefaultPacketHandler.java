package com.wplatform.muoline.handler;

import com.wplatform.muoline.network.NetworkTransport;

/**
 * @author jorgie.li
 */
public class DefaultPacketHandler implements PacketHandler {

    @Override
    public void handlePacket(NetworkTransport io) {
        //log.warn("Unknown packet[opcode = 0x"+PrintData.fillHex(opcode, 2)+"]. Dump: ");
        //log.warn(PrintData.printData(buff.nioBuffer(0, buff.writerIndex())));
    }

}
