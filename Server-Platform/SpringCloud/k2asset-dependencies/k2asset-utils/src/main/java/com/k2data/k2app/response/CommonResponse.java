package com.k2data.k2app.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.k2data.k2app.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "code", "message"})
public class CommonResponse implements Serializable {

    /**
     * 通用的没有返回实体的response
     */
    private static final long serialVersionUID = -2564445585181441109L;

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String message;

    private final static transient CommonResponse successResponse =
            new CommonResponse(ResponseCode.GENERAL_SUCCESS, ResponseCode.SUCCESS);

    public static CommonResponse buildSuccessCommonResponse() {
        return successResponse;
    }


}
