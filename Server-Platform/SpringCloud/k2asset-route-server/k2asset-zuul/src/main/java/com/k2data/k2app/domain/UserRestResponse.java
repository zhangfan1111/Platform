package com.k2data.k2app.domain;

import lombok.Data;

/**
 * @author lidong9144@163.com 17-6-6.
 */
@Data
public class UserRestResponse {

    private Integer code;
    private String message;
    private User result;

}
