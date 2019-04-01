package com.wplatform.wizardcraft.handler;

import com.wplatform.wizardcraft.network.NetworkTransport;

/**
 * @author jorgie.li
 */
public interface PacketHandler {

    void handlePacket(NetworkTransport io) throws PacketHandleException;

}
