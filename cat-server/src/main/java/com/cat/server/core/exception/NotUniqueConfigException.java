package com.cat.server.core.exception;

/**
 * 不是唯一配置
 * @author Jeremy Feng
 */
public class NotUniqueConfigException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2699468488320306768L;

    public NotUniqueConfigException(String message) {
        super(message);
    }

}
