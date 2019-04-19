package com.wplatform.wizardcraft.engine.impl;

import com.google.common.collect.Maps;
import com.wplatform.wizardcraft.handler.Processor;
import com.wplatform.wizardcraft.engine.PacketHandlerDispatcher;
import com.wplatform.wizardcraft.network.NetworkTransport;
import com.wplatform.wizardcraft.handler.DefaultPacketHandler;
import com.wplatform.wizardcraft.handler.PacketProcessor;
import com.wplatform.wizardcraft.proto.Proto;
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
    private final Map<Integer, PacketProcessor> cacheProcessor = Maps.newHashMap();



    @Override
    public PacketProcessor dispatchHandler(NetworkTransport io) {
        int packetHeaderSize = Proto.getPacketHeaderSize(io.getInput());
        io.skipRead(packetHeaderSize);
        int opcode = io.readUnsignedByte();
        PacketProcessor processor = cacheProcessor.get(opcode);
        if (processor == null) {
            processor = NOT_SUPPORT_PROCESSOR;
        }
        return processor;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PacketProcessor> beansOfType = applicationContext.getBeansOfType(PacketProcessor.class);
        for (Map.Entry<String, PacketProcessor> entry : beansOfType.entrySet()) {
            PacketProcessor bean = entry.getValue();
            Processor packet = bean.getClass().getAnnotation(Processor.class);
            Optional.ofNullable(packet).ifPresent(e->{
                for (int opcode : e.opcode()) {
                    cacheProcessor.put(opcode, bean);
                }
            });
        }
    }
}
