package com.wplatform.wizardcraft.engine;

import com.wplatform.wizardcraft.network.NetworkTransport;
import com.wplatform.wizardcraft.handler.PacketProcessor;

public interface PacketHandlerDispatcher {

    PacketProcessor dispatchHandler(NetworkTransport transport);

}
