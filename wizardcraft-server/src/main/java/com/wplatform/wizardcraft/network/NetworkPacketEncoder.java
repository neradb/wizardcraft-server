
package com.wplatform.wizardcraft.network;

import com.wplatform.wizardcraft.network.crypt.S6EP3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;


public final class NetworkPacketEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int serialNumber = NetworkConnection.wrapped(ctx.channel()).getSerialNumber();
        ByteBuf output = S6EP3.enCrypt(msg, (short) serialNumber);
        out.add(output.slice().retain());
    }
}
