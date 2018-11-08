package com.anjuxing.platform.authority.model;


import lombok.*;

import javax.validation.constraints.NotNull;

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
public class SysRoleAcl extends BaseModel {



    //角色Id
    @NotNull
    private Integer roleId;

    //权限Id
    @NotNull
    private Integer aclId;


}
