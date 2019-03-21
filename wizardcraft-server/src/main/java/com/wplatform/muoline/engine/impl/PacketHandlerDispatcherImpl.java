package com.wplatform.muoline.engine.impl;

import com.google.common.collect.Maps;
import com.wplatform.muoline.handler.Packet;
import com.wplatform.muoline.engine.PacketHandlerDispatcher;
import com.wplatform.muoline.network.NetworkTransport;
import com.wplatform.muoline.handler.DefaultPacketHandler;
import com.wplatform.muoline.handler.PacketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author jorgie.li
 *
 */
@Slf4j
@Component
public class PacketHandlerDispatcherImpl implements PacketHandlerDispatcher, ApplicationContextAware {

    private static final DefaultPacketHandler NOT_SUPPORT_PROCESSOR = new DefaultPacketHandler();
    private final Map<Integer, PacketHandler> cacheProcessor = Maps.newHashMap();



    @Override
    public PacketHandler dispatchHandler(NetworkTransport io) {
        int skipRead = io.readByte() % 2 == 1 ? 1 : 2;
        io.skipRead(skipRead);
        int opcode = io.readByte();
        PacketHandler processor = cacheProcessor.get(opcode);
        if (processor == null) {
            processor = NOT_SUPPORT_PROCESSOR;
        }
        return processor;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PacketHandler> beansOfType = applicationContext.getBeansOfType(PacketHandler.class);
        for (Map.Entry<String, PacketHandler> entry : beansOfType.entrySet()) {
            PacketHandler bean = entry.getValue();
            Packet packet = bean.getClass().getAnnotation(Packet.class);
            Optional.ofNullable(packet).ifPresent(e->{
                for (int opcode : e.opcode()) {
                    cacheProcessor.put(opcode, bean);
                }
            });
        }
    }
}
