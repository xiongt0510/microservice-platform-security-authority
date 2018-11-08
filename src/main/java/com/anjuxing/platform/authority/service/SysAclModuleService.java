package com.anjuxing.platform.authority.service;


import com.anjuxing.platform.authority.model.SysAclModule;

/**
 * @author xiongt
 * @Description
 */
public interface SysAclModuleService extends BaseService<SysAclModule> {

    boolean checkExist(Integer parentId, String aclModuleName, Integer deptId);


}
