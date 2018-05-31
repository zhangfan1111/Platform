package com.k2data.k2app;

/**
 * @author lidong9144@163.com 17-6-13.
 */
public class AuthorizeException extends RuntimeException {

    private static final long serialVersionUID = 6432113307625203880L;

    public AuthorizeException() {
        super();
    }

    public AuthorizeException(String msg) {
        super(msg);
    }

    public AuthorizeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public AuthorizeException(Throwable throwable) {
        super(throwable);
    }

}
