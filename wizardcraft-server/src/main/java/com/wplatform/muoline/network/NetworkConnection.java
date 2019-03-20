package com.wplatform.muoline.network;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkConnection implements AutoCloseable {


    private final static short MAX_SERIAL_NUMBER = 255;
    private final static AtomicInteger CLIENT_ID_GENERATOR = new AtomicInteger(0);

    private static final AttributeKey<NetworkConnection> CHANNEL_SESSION_KEY = AttributeKey.valueOf("_CHANNEL_SESSION_KEY");
    private int clientId = CLIENT_ID_GENERATOR.incrementAndGet();
    private int serialNumber;
    private int maxSerialNumber = MAX_SERIAL_NUMBER;
    private Map<String, Object> attachments = Maps.newHashMap();
    private Channel channel;


    public int getAndPlusSerialNumber() {
        int result = serialNumber;
        if (serialNumber < maxSerialNumber) {
            this.serialNumber++;
        }
        return result;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int plusAndGetSerialNumber() {
        this.serialNumber++;
        if (serialNumber > maxSerialNumber) {
            serialNumber = 0;
        }
        return serialNumber;
    }

    @SuppressWarnings("unchecked")
    public <T> T setAttachment(String key, T value) {
        T old = (T) attachments.get(key);
        attachments.put(key, value);
        return old;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttachment(String key) {
        T val = (T) attachments.get(key);
        return val;
    }

    public int getConnectionId() {
        return clientId;
    }

    public NetworkTransport getOutgoingNetworkTransport() {
        return new NetworkTransport(this, channel.alloc().buffer());
    }

    public NetworkTransport newNetworkTransport(ByteBuf input) {
        return new NetworkTransport(this, input, channel.alloc().buffer());
    }

    public Channel getChannel() {
        return channel;
    }

    public static NetworkConnection wrapped(Channel channel) {
        NetworkConnection client = channel.attr(CHANNEL_SESSION_KEY).get();
        if (client == null) {
            client = new NetworkConnection();
            client.channel = channel;
            channel.attr(CHANNEL_SESSION_KEY).set(client);
        }
        return client;
    }


    @Override
    public void close() {
        if (channel != null && channel.isOpen()) {
            channel.attr(CHANNEL_SESSION_KEY).set(null);
            channel.close();
        }

    }

}
