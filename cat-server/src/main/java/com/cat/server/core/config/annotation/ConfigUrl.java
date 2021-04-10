package com.cat.server.core.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 远程配置文件地址<br>
 * 用于读取远程配置文件 并定期刷新
 * 
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigUrl {

    /**
     * 文件名
     * @return
     */
    String value();

    /**
     * 是否阻塞加载<br>
     * 若为true 则服务器启动时出错时 停止启动
     * 
     * @return
     */
    boolean blockStartup() default true;

}
