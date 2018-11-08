package com.anjuxing.platform.authority.model;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SysUser extends BaseModel{



    /** 用户名**/
    @NotBlank
    @Length(min=3,max = 20,message = "用户名只能不得少于3，不能大于20")
    private String username;

    /** 用户密码**/
//    @NotBlank()
//    @Length(min=6,max = 20,message = "密码不能少于6，不能大于20")
    private String password;

    /** 手机号**/
    @NotBlank
//    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\\\d{8})$",message = "手机号不匹配")
    private String mobile;

    /** 性别**/
//    @NotNull
//    @Digits(integer = 0,fraction = 1)
    private Integer sex;

    /** 邮件**/
    @NotBlank
    @Email
    private String email;

    /** 备注**/
    @Length(min = 2,max = 200)
    private String remark;

    /** 用户级别**/
    @Length
    private String rank;

    @NotNull
    private Integer deptId;



    /** 图片路径**/
//    @NotBlank
    private String imageUrl;


    /** 账号是否被锁定**/
    private Boolean locked = Boolean.FALSE;


    private Set<SysRole> roles;

    private Set<SysAcl> permissions;




}
