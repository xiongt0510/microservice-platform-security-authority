package com.anjuxing.platform.authority.service;


import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.model.SysUserRole;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysUserRoleService {

   int  saveUserRoles(int userId, List<SysUserRole> roleIds);

   List<SysUser> findUsersByRoleId(int roleId);

   int changeUserRoles(int roleId, List<Integer> userIdList);
}
