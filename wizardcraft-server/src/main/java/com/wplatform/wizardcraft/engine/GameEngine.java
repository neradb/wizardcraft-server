package com.wplatform.wizardcraft.engine;


import com.google.common.collect.Maps;
import com.wplatform.wizardcraft.network.NetworkConnection;
import com.wplatform.wizardcraft.handler.PacketHandler;
import com.wplatform.wizardcraft.network.NetworkTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@Component
public class GameEngine {


    @Autowired
    private PacketHandlerDispatcher processDispatcher;

    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);

    private ConcurrentMap<String, NetworkConnection> onlineUserConnections = Maps.newConcurrentMap();


    public Optional<NetworkConnection> getUserConnection(String accoutName) {
        return Optional.ofNullable(onlineUserConnections.get(accoutName));
    }

    public void addUserConnection(String accoutName, NetworkConnection connection) {
        onlineUserConnections.putIfAbsent(accoutName, connection);
    }


    public void onConnected(NetworkConnection connection) {

    }

    public void onConnectionClosed(NetworkConnection connection) {
        Session session = connection.getSession();
        Optional.ofNullable(session.getAccount()).ifPresent(e -> onlineUserConnections.remove(e.getAccountName()));
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
