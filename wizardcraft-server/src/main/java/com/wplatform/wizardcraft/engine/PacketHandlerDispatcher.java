package com.wplatform.wizardcraft.engine;

import com.wplatform.wizardcraft.network.NetworkTransport;
import com.wplatform.wizardcraft.handler.PacketHandler;

public interface PacketHandlerDispatcher {

    PacketHandler dispatchHandler(NetworkTransport transport);

}
