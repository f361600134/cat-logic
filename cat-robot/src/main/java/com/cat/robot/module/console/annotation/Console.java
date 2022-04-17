package com.cat.robot.module.console.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * GM命令
 * @author Jeremy
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Console {
    /**
     * GM名
     * 
     * @return
     */
    String value();
}
