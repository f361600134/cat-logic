package com.cat.server.core.exception;

/**
 * 重复配置
 * 
 * @author hdh
 *
 */
public class RepeatConfigException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3828432294815520946L;

    public RepeatConfigException(String message) {
        super(message);
    }

}
