package com.wplatform.muoline.handler;

import com.wplatform.muoline.network.NetworkTransport;

/**
 * @author jorgie.li
 */
public interface PacketHandler {

    void handlePacket(NetworkTransport io) throws PacketHandleException;

}
