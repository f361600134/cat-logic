package com.cat.rank.core.config.annotation;

import java.lang.annotation.*;

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
