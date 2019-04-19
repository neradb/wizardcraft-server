package com.wplatform.wizardcraft.handler;

import com.wplatform.wizardcraft.network.NetworkTransport;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jorgie.li
 */
@Slf4j
public class DefaultPacketHandler implements PacketProcessor {

    @Override
    public void handlePacket(NetworkTransport io) {
        log.error("Feature is not available, packet dump: \n{}",
                ByteBufUtil.hexDump(io.getInput(), 0, io.getInput().writerIndex()));
    }

}
