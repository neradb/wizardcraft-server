/*
 * Copyright 2014-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wplatform.muoline.util;

public class SysProperties {


    public static final String FILE_SEPARATOR = getProperty("file.separator", "/");

    public static final String LINE_SEPARATOR = getProperty("line.separator", "\n");

    public static final String USER_HOME = getProperty("user.home", "");

    public static final String APPLIACTION_HOME = getProperty("appliaction.home", "");


    public static final String TRANSPORT_CHARSET = getProperty("transport.charset", "US-ASCII");




    public static final int THREAD_QUEUE_SIZE = Integer.getInteger("request.threadqueue.size", 20480);

    public static final int THREAD_POOL_SIZE_CORE = getProperty("threadpool.size.core", Runtime.getRuntime().availableProcessors());

    public static final int THREAD_POOL_SIZE_MAX = getProperty("threadpool.size.max", Runtime.getRuntime().availableProcessors() * 20);

    public static final int CS_GROUPTHREADS = getProperty("cs.groupThreads", 1);
    public static final int CS_WORKERTHREADS = getProperty("cs.workerThreads", 1);
    public static final int GS_GROUPTHREADS = getProperty("gs.groupThreads", 1);
    public static final int GS_WORKERTHREADS = getProperty("gs.workerThreads", Runtime.getRuntime().availableProcessors());

    public static final int GS_IDLE_TIME_SECONDS = getProperty("gs.idleTimeout", 600);

    public static final String GS_LOWEST_CLIENT_VERSION = getProperty("gs.lowestClientVersion", "10404");


    /// Gets or sets a value indicating whether this decryptor instance accepts wrong block checksum, or throws an exception in this case.
    public static final boolean ACCEPT_WRONG_BLOCK_CHECKSUM = getProperty("crypt.acceptWrongBlockChecksum", false);




    private SysProperties() {
        // utility class
    }


    /**
     * Get the system property. If the system property is not set, or if a
     * security exception occurs, the default value is returned.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the value
     */
    public static String getProperty(String key, String defaultValue) {
        try {
            return System.getProperty(key, defaultValue);
        } catch (SecurityException se) {
            return defaultValue;
        }
    }

    /**
     * Get the system property. If the system property is not set, or if a
     * security exception occurs, the default value is returned.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the value
     */
    public static int getProperty(String key, int defaultValue) {
        String s = getProperty(key, null);
        if (s != null) {
            try {
                return Integer.decode(s).intValue();
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return defaultValue;
    }

    /**
     * Get the system property. If the system property is not set, or if a
     * security exception occurs, the default value is returned.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the value
     */
    public static boolean getProperty(String key, boolean defaultValue) {
        String s = getProperty(key, null);
        if (s != null) {
            try {
                return Boolean.parseBoolean(s);
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return defaultValue;
    }


    public static boolean isLinux() {
        return getProperty("os.name", "").toLowerCase().indexOf("linux") >= 0;
    }


}
