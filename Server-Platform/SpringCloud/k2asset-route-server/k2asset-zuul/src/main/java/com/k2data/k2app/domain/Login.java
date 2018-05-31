package com.k2data.k2app.domain;

import lombok.Data;

/**
 * @author lidong9144@163.com 17-6-7.
 */
@Data
public class Login {

    private String accessToken;
    private Long expiresIn;
    private Long userId;

}
