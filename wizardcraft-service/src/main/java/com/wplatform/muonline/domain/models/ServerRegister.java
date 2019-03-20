package com.wplatform.muonline.domain.models;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

public class ServerRegister {

    private static byte id = 0;

    private final Map<Integer, ServerInfo> registeredServer = Maps.newHashMap();


    public void registerServer(ServerInfo serverInfo) {
        registeredServer.put(serverInfo.getServerId(), serverInfo);
    }

    public ServerInfo getServerById(int serverId) {
        return registeredServer.get(serverId);
    }

    public Collection<ServerInfo> getRegisteredServer() {
        return registeredServer.values();
    }

    @Data
    @AllArgsConstructor
    public static class ServerInfo {

        private int serverId;

        private String host;

        private int port;

        private int serverLoad;

        public static ServerInfo create(String ip, int port) {
            return new ServerInfo(++id, ip, port, 20);
        }
    }

}
