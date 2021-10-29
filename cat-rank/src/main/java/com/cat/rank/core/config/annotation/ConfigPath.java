package com.cat.rank.core.config.annotation;

import java.lang.annotation.*;

/**
 * 配置文件路径, 用于本地路径
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPath {
    /**
     * 配置文件名
     * 
     * @return
     */
    String value();
}
