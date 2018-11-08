package com.anjuxing.platform.authority.model;


import lombok.*;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserRole extends BaseModel{



    private Integer userId;

    private Integer roleId;

}
