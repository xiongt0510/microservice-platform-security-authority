package com.anjuxing.platform.authority.service;


import com.anjuxing.platform.authority.model.SysDept;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 根据用户id 查询用户组
     * @param userId
     * @return
     */
    List<SysDept> findDeptByUserId(int userId);


}
