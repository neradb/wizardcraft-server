package com.wplatform.muoline.handler;

import org.springframework.stereotype.Component;

@Component
public @interface Packet {
    int[] opcode() default {};
}
