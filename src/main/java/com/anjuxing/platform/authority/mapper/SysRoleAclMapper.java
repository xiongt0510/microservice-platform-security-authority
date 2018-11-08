package com.anjuxing.platform.authority.mapper;

import com.anjuxing.platform.authority.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysRoleAclMapper {

    int saveRoleAcl(@Param("roleId") int roleId, @Param("roleAcls") List<SysRoleAcl> roleAcls);

    int deleteByRoleId(@Param("roleId") int roleId);

    int batchSave(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Integer> findRoleIdsByAclId(@Param("aclId") int aclId);

    List<Integer> findAclIdsByRoleIds(@Param("roleIdList") List<Integer> roleIdList);
}
