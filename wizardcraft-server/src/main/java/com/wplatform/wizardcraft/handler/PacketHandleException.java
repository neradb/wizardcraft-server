package com.wplatform.wizardcraft.handler;

public class PacketHandleException extends RuntimeException {

    public PacketHandleException() {
    }

    public PacketHandleException(String message) {
        super(message);
    }

    public PacketHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketHandleException(Throwable cause) {
        super(cause);
    }

    public PacketHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
