package com.xjtu.xjtuse.assistance.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 业务错误
     */
    public static final int ERR_CODE_BUSINESS = 500;

    public static final int ERR_CODE_UNLOGIN = 520;
    public static final int ERR_CODE_UNWITHDRAW = 530;

    public static final int ERR_CODE_UNREGISTER = 540;

    /**
     * 系统错误
     */
    public static final int ERR_CODE_SYS = 530;

    public static Result success() {
        return new Result(SUCCESS_CODE, true, null, null);
    }

    public static Result success(String message) {
        return new Result(200, true, message, null);
    }

    public static Result success(String message, Object data) {
        return new Result(SUCCESS_CODE, true, message, data);
    }

    public static Result successMsg(String message) {
        return new Result(SUCCESS_CODE, true, message, null);
    }

    public static Result successData(Object data) {
        return new Result(SUCCESS_CODE, true, null, data);
    }

    public static Result fail(int code, String message) {
        return new Result(code, false, message, null);
    }

    private int code;//200成功 500、530错误
    private Boolean success;//是否成功
    private String message;
    private Object data;

    private Result(int code, Boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(200, true, null, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
