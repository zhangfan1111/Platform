package com.k2data.k2app.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {
    private Long id;
    //操作用户
    private String createBy;
    //操作模块
    private String modelName;
    //操作的类
    private String className;
    //方法
    private String method;
    //时间
    private Date createDate;
    //日志类型  normal(正常日志),error(错误日志)
    private String type;
    //信息
    private String message;
    //详情
    private String detail;

}