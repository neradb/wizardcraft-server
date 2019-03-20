package com.wplatform.muoline.engine;


import com.google.common.collect.Maps;
import com.wplatform.muoline.network.NetworkConnection;
import com.wplatform.muoline.handler.PacketHandler;
import com.wplatform.muoline.network.NetworkTransport;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.nio.ch.Net;


import java.util.concurrent.ConcurrentMap;

@Component
public class GameEngine {


    @Autowired
    private PacketHandlerDispatcher processDispatcher;

    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);

    private ConcurrentMap<Object, NetworkConnection> connectedUsers = Maps.newConcurrentMap();



    public void onConnected(NetworkConnection connection) {

    }

    public void onConnectionClosed(NetworkConnection connection) {

    }

    public void onConnectionClose(NetworkTransport transport) {
        PacketHandler processor = processDispatcher.dispatchHandler(transport);
        processor.handlePacket(transport);
    }


    public void processPacket(NetworkTransport transport) {
        PacketHandler processor = processDispatcher.dispatchHandler(transport);
        processor.handlePacket(transport);
    }



}
