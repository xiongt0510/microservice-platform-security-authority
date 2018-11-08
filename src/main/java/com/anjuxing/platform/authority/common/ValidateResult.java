package com.anjuxing.platform.authority.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiongt
 * @Description
 */
@Setter
@Getter
public class ValidateResult {

    private String code;

    private String msg;

    public ValidateResult(String msg){
        this.msg = msg;
    }

    public ValidateResult(String code , String msg){
        this(msg);
        this.code = code;
    }




}
