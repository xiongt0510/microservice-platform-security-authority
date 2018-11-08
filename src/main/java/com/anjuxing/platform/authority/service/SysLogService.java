package com.anjuxing.platform.authority.service;

import com.anjuxing.platform.authority.beans.SearchLogParam;
import com.anjuxing.platform.authority.model.*;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysLogService extends BaseService<SysLogWithBLOBs> {

    void recover(int id);

    List<SysLogWithBLOBs> search(SearchLogParam logParam);

    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);
}
