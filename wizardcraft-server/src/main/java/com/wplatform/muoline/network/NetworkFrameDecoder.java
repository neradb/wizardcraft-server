/**
 * Copyright (C) 2013-2014 Project-Vethrfolnir
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wplatform.muoline.network;

import com.wplatform.muoline.network.crypt.S6EP3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public final class NetworkFrameDecoder extends ByteToMessageDecoder {


    private static final int FRAME_LENGTH_FIELD_LENGTH = 3;

    /* (non-Javadoc)
     * 0xc1:
     *    * commonly used for fixed packet formats (e.g., moving,NPC conversations, using items, picking, normal attacks, etc.)
     *    * characteristics: the length of such packets is generally short
     *    * the structure of baotou is as follows:
     *    _Buffer = the Packed Record
     *       Head: Byte;//0xc1
     *       Len: Byte;//packet length
     *    The end;
     * 0xc2:
     *    * commonly used for dynamic length packets, commonly found in return packets (e.g. : monster list, backpack list, damage pack)
     *    * characteristics: the length of such packets is generally long
     *    * the structure of baotou is as follows:
     *    _Buffer = the Packed Record
     *      Head: Byte;// 0xc2
     *      Len: Short;// packet length
     *    The end;
     * 0xc3:
     *    *C1 secondary encryption packet
     * 0xc4:
     *    *C2 secondary encryption packet
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        // Make sure if the length field was received.
        if (in.readableBytes() < FRAME_LENGTH_FIELD_LENGTH) {
            // The length field was not received yet - return.
            // This method will be invoked again when more packets are
            // received and appended to the buffer.
            return;
        }
        // The length field is in the buffer.
        // Mark the current buffer position before reading the length field
        // because the whole frame might not be in the buffer yet.
        // We will reset the buffer position to the marked position if
        // there's not enough bytes in the buffer.
        in.markReaderIndex();
        short frameLength = Packets.getPacketSize(in);

        if (in.readableBytes() < frameLength) {
            in.resetReaderIndex();
            return;
        }
        // There's enough bytes in the buffer. Read it.
        ByteBuf frame = in.slice(0, frameLength);
        int serialNumber = NetworkConnection.wrapped(ctx.channel()).getAndPlusSerialNumber();
        ByteBuf packet = S6EP3.deCrypt(frame, (short) serialNumber);
        out.add(packet);
    }

}
