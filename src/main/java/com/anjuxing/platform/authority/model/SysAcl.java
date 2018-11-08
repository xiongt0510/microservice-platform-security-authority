package com.anjuxing.platform.authority.model;


import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * @author xiongt
 * @Description 权限
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAcl extends BaseModel{



    private Integer parentId;
//    @NotBlank
    @Length(min = 1,max = 20)
    private String code;
    @NotBlank
    @Length(min = 3,max = 100,message = "权限名不能超过100字")
    private String name;

    @Length(max = 200,message = "描述不能超过200字")
    private String desc;

    @NotNull
    private Integer aclModuleId;

    @NotBlank
    private String url;

    @NotNull
    private Integer type;
    @NotNull
    private Integer seq;

    @Length(max = 200,message = "备注不能超过200字")
    private String remark;



}
