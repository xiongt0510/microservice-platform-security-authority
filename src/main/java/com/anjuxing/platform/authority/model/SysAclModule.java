package com.anjuxing.platform.authority.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysAclModule extends BaseModel{



    @NotBlank
    private String name ;

    @Length(max = 200,message = "描述不能超过200字")
    private String desc;

    private String level;

    @NotNull
    private Integer seq;

    @NotNull
    private Integer parentId;

    @Length(max = 200,message = "备注不能超过200字")
    private String remark;


}
