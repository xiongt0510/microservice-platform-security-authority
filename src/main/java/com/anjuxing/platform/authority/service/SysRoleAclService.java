package com.anjuxing.platform.authority.service;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysRoleAclService {

    int saveRoleAcl(int roleId, List<Integer> aclIds);

    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}
