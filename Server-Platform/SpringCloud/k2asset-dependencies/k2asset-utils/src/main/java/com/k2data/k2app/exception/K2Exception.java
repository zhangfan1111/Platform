package com.k2data.k2app.exception;

/**
 * @author lidong9144@163.com 17-3-10.
 */
public class K2Exception extends RuntimeException {

    private static final long serialVersionUID = 7303576033421753176L;

    public K2Exception(String msg) {
        super(msg);
    }

    public K2Exception(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public K2Exception(Throwable throwable) {
        super(throwable);
    }

}
