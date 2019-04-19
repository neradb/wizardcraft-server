package com.wplatform.wizardcraft.handler;

import com.wplatform.wizardcraft.network.NetworkTransport;

/**
 * @author jorgie.li
 */
public interface PacketProcessor {

    void handlePacket(NetworkTransport io) throws PacketHandleException;

}
