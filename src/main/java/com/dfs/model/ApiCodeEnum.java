package com.dfs.model;

/**
 * API接口响应码枚举
 *
 * @author zjj
 * @date 2019-02-13
 */
public enum ApiCodeEnum {

    SUCCESS("1", "操作成功"),
    FAIL("0", "操作失败"),
    EXCEPTION("0", "程序出现异常"),
    RELOGIN("2", "登录失效"),
    DUPLICATE_RECORD("10001", "重复记录");


    private String code;
    private String msg;

    private ApiCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getDescByValue(String code) {
        for (ApiCodeEnum enums : ApiCodeEnum.values()) {
            if (enums.getCode().equals(code)) {
                return enums.getMsg();
            }
        }
        return "";
    }

}
