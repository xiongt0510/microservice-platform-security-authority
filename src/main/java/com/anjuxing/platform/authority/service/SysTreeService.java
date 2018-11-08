package com.anjuxing.platform.authority.service;

import com.anjuxing.platform.authority.dto.AclModuleLevelDto;
import com.anjuxing.platform.authority.dto.DeptLevelDto;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysTreeService {

    List<AclModuleLevelDto> userAclTree(int userId);

    List<AclModuleLevelDto> roleTree(int roleId);

    List<AclModuleLevelDto> aclModuleTree();


    List<DeptLevelDto> deptTree();


}
