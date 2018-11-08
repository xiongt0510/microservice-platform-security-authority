package com.anjuxing.platform.authority.mapper;

import com.anjuxing.platform.authority.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysUserRoleMapper {

    /**
     *
     * @param userId
     * @param userRoles
     * @return
     */
    int saveUserRoles(@Param("userId") int userId, @Param("userRoles") List<SysUserRole> userRoles);


    List<Integer> findRoleIdsByUserId(@Param("userId") int userId);

    List<Integer> findUserIdsByRoleId(@Param("roleId") int roleId);

    void deleteByRoleId(@Param("roleId") int roleId);

    int batchSave(@Param("roleUserList") List<SysUserRole> roleUserList);

    List<Integer> findUserIdsByRoleIds(@Param("roleIdList") List<Integer> roleIdList);

}
