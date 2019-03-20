package com.wplatform.muoline.engine;

import com.wplatform.muoline.network.NetworkTransport;
import com.wplatform.muoline.handler.PacketHandler;

public interface PacketHandlerDispatcher {

    PacketHandler dispatchHandler(NetworkTransport transport);

}
