package com.stella.test.jcr;

/**
 * biz runtime exception
 *
 * @author sail
 * @date 11:30 2019-11-11.
 * @since 1.0
 */
public class BizRuntimeException extends RuntimeException {
    public BizRuntimeException(String msg){
        super(msg);
    }

    public BizRuntimeException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
