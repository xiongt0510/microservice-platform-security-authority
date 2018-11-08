package com.anjuxing.platform.authority.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
public class AuthorityException extends RuntimeException {

   private Integer code;

   public AuthorityException(){}

   public AuthorityException(ResultEnum resultEnum){
       super(resultEnum.getMsg());
       this.code = resultEnum.getCode();
   }
}
