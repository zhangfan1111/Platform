package com.k2data.k2app.domain;

import lombok.Data;

/**
 * @author lidong9144@163.com 17-6-6.
 */
@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private Integer loginFlag; //是否冻结,０是 1否
}
