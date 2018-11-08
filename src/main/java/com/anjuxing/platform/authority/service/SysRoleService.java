package com.anjuxing.platform.authority.service;


import com.anjuxing.platform.authority.model.SysRole;
import com.anjuxing.platform.authority.model.SysUser;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysRoleService extends BaseService<SysRole> {
    /**
     * 根据用户查询角色
     * @return
     */
    List<SysRole> findRolesByUserId(int userId);

    List<SysRole> findRolesByAclId(int aclId);

    List<SysUser> findUsersByRoles(List<SysRole> roles);


}
