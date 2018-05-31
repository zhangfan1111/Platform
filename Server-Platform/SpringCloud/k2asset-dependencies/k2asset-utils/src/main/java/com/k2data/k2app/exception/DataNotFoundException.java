package com.k2data.k2app.exception;

import com.k2data.k2app.constant.ResponseCode;

/**
 * data not found exception
 *
 * @author WangShengguo
 */
public class DataNotFoundException extends K2ResponseException {

    private static final long serialVersionUID = -5999409754794135010L;
    private static final String SEPARATOR = ": ";

    public DataNotFoundException() {
        super(ResponseCode.GENERAL_RESULT_NOT_FOUND, ResponseCode.MSG_RESULT_NOT_FOUND);
    }

    public DataNotFoundException(String extraMessage) {
        super(ResponseCode.GENERAL_RESULT_NOT_FOUND,
                ResponseCode.MSG_RESULT_NOT_FOUND + SEPARATOR + extraMessage);
    }

}