package com.wplatform.muoline.handler;

import com.wplatform.muoline.network.NetworkTransport;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jorgie.li
 */
@Slf4j
public class DefaultPacketHandler implements PacketHandler {

    @Override
    public void handlePacket(NetworkTransport io) {
        throw new UnsupportedOperationException("Feature is not available, packet dump:" +
                ByteBufUtil.hexDump(io.getInput(), 0 ,io.getInput().writerIndex()));
    }

}
