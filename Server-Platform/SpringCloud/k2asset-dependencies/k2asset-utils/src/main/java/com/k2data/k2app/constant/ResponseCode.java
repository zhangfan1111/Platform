package com.k2data.k2app.constant;

/**
 * 设置六位返回码, 第一位表示错误类型, 后面两位表示项目标号, 最后三位表示具体错误信息
 */
public class ResponseCode {

    // 错误类型
    public static String TYPE_SUCCESS = "2";
    public static String TYPE_CLIENT_ERROR = "4";
    public static String TYPE_SERVER_ERROR = "5";

    // 模块
    public static String K2ALPHA = "00";
    public static String K2ALPHA_SYS = "01";
    public static String K2ALPHA_DEVICE = "02";
    public static String K2ALPHA_MOBILE = "03";
    public static String K2ALPHA_MAIL = "04";
    public static String K2ALPHA_HEALTH = "05";

    // 错误信息
    public static String CLIENT_ERROR = "Client error.";
    public static String SERVER_ERROR = "Server error";
    public static String SUCCESS = "Success";

    public static String GENERAL = "000";
    public static String MSG_GENERAL = "";

    public static String RESULT_NOT_FOUND_BY_ID = "001";
    public static String MSG_RESULT_NOT_FOUND_BY_ID = "Result not found by id, plz check your input.";

    public static String RESULT_NOT_FOUND = "002";
    public static String MSG_RESULT_NOT_FOUND = "Result not found, plz check your input.";


    public static int GENERAL_SUCCESS = getCode(TYPE_SUCCESS, K2ALPHA, GENERAL);
    public static int GENERAL_CLIENT_ERROR = getCode(TYPE_CLIENT_ERROR, K2ALPHA, GENERAL);
    public static int GENERAL_RESULT_NOT_FOUND = getCode(TYPE_CLIENT_ERROR, K2ALPHA, RESULT_NOT_FOUND);
    public static int GENERAL_SERVER_ERROR = getCode(TYPE_SERVER_ERROR, K2ALPHA, GENERAL);

    /**
     * 拼接错误代码
     */
    public static int getCode(String type, String module, String error) {
        return Integer.valueOf(type + module + error);
    }

}
