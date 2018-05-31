package com.k2data.k2app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "code", "message", "result", "pageInfo" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResultResponse<T> extends CommonResponse implements Serializable {

    /**
     * 有返回信息的response
     */
    private static final long serialVersionUID = 2523377728598055414L;
    @Setter
    @Getter
    private T result;
    @Setter
    @Getter
    private PageInfo pageInfo;

}
