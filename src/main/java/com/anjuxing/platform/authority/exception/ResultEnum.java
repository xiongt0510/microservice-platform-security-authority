package com.anjuxing.platform.authority.exception;

import lombok.Getter;

/**
 * @author xiongt
 * @Description
 */
@Getter
public enum ResultEnum {

    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    ERROR(1,"失败");

    private  Integer code;
    private String msg;

    ResultEnum (Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }


}
