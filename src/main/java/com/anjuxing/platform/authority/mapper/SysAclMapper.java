package com.anjuxing.platform.authority.mapper;

import com.anjuxing.platform.authority.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysAclMapper extends BaseMapper<SysAcl> {

    /**
     * 根据角色查权限
     * @param roleId
     * @return
     */
    List<SysAcl> findByRoleId(@Param("roleId") int roleId);

    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    List<SysAcl> findByIds(@Param("idList") List<Integer> idList);

    List<SysAcl> findByUrl(@Param("url") String url);

    List<SysAcl> findByAclModuleId(@Param("aclModuleId") int aclModuleId);

}
