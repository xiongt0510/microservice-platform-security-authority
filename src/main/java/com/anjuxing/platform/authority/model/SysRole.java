package com.anjuxing.platform.authority.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SysRole extends BaseModel{



    /** 角色描述**/
    @Length(max = 200,message = "描述不能超过200字")
    private String desc;

    /** 角色名称**/
    @NotBlank
    @Length(min = 2,max = 200,message = "名字不能少于2个字符，超过200字")
    private String name;

    @Length(max = 200,message = "备注不能超过200字")
    private String remark;

//    @NotNull
    private Integer type;

    private Set<SysAcl> permissions = new HashSet<SysAcl>();


}
