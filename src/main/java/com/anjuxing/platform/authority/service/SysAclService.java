package com.anjuxing.platform.authority.service;


import com.anjuxing.platform.authority.model.SysAcl;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysAclService extends BaseService<SysAcl>{

    /**
     * 根据角色查权限
     * @param roleId
     * @return
     */
    List<SysAcl> findAclsByRoleId(int roleId);

    String generateCode();

    boolean checkExist(int aclModuleId, String name, Integer id);

    List<SysAcl> findByAclModuleId(int aclModuleId);






}
