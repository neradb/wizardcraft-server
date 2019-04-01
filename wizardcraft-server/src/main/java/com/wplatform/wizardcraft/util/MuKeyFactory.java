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
package com.wplatform.wizardcraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class MuKeyFactory {

    private static long[] XOR_TAB_DATFILE = new long[]{0x3F08A79B, 0xE25CC287, 0x93D27AB9, 0x20DEA7BF};

    private static long[] clientToServerPacketEncKeys;
    private static long[] serverToClientPacketEncKeys = new long[12];
    private static long[] clientToServerPacketDecKeys = new long[12];
    private static long[] serverToClientPacketDecKeys;

    static {
        readFile("system/static/keys/Enc2.dat", serverToClientPacketEncKeys);
        readFile("system/static/keys/Dec1.dat", clientToServerPacketDecKeys);
    }

    private static void readFile(String path, long[] out_dat) {
        File file = new File(SysProperties.APPLIACTION_HOME, path);
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            ByteBuf fileBuf = Unpooled.wrappedBuffer(fileContent);
            fileBuf.readerIndex(6);
            int pointer = 0;
            for (int i = 0; i < 3; i++) {
                long[] buf = new long[4];

                for (int j = 0; j < 4; j++) {
                    buf[j] = fileBuf.readUnsignedIntLE();
                }
                out_dat[pointer++] = buf[0] ^ (XOR_TAB_DATFILE[0]);
                out_dat[pointer++] = buf[1] ^ (XOR_TAB_DATFILE[1] & 0xFFFFFFFFL);
                out_dat[pointer++] = buf[2] ^ (XOR_TAB_DATFILE[2] & 0xFFFFFFFFL);
                out_dat[pointer++] = buf[3] ^ (XOR_TAB_DATFILE[3]);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load key file from location " + file);
        }
    }

    /**
     * @return the serverToClientPacketEncKeys
     */
    public static long[] getServerToClientPacketEncKeys() {
        return serverToClientPacketEncKeys;
    }

    /**
     * @return the clientToServerPacketDecKeys
     */
    public static long[] getClientToServerPacketDecKeys() {
        return clientToServerPacketDecKeys;
    }

    /**
     * @return the clientToServerPacketEncKeys
     */
    public static long[] getClientToServerPacketEncKeys() {
        if (clientToServerPacketEncKeys == null) {
            readFile("static_data/keys/Enc1.dat", clientToServerPacketEncKeys = new long[12]);
        }
        return clientToServerPacketEncKeys;
    }

    /**
     * @return the serverToClientPacketDecKeys
     */
    public static long[] getServerToClientPacketDecKeys() {
        if (serverToClientPacketDecKeys == null) {
            readFile("static_data/keys/Dec2.dat", serverToClientPacketDecKeys = new long[12]);
        }
        return serverToClientPacketDecKeys;
    }

}
