package com.wplatform.wizardcraft.handler;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface Packet {
    int[] opcode() default {};
}
