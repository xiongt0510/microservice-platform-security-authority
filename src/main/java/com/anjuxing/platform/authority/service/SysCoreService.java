package com.anjuxing.platform.authority.service;

import com.anjuxing.platform.authority.model.SysAcl;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysCoreService {

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    List<SysAcl> getUserAclList(int userId);

    boolean isSuperAdmin();


    boolean hasUrlAcl(String url);

    List<SysAcl> getCurrentUserAclListFromCache();
}
