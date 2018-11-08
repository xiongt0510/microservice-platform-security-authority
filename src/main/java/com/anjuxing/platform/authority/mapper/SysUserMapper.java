package com.anjuxing.platform.authority.mapper;



import com.anjuxing.platform.authority.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    SysUser findByName(@Param("userName") String userName);


    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    SysUser findByNamePassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据条件查询
     * @param user
     * @return
     */

    List<SysUser> findByCondition(SysUser user);


    /**
     * 根据手机查找用户
     * @param mobile
     * @return
     */
    SysUser findByMobile(@Param("mobile") String mobile);



    SysUser findRoleById(int id);

    /**
     * 根据组名查用户
     * @param deptId
     * @return
     */
    List<SysUser> findByDept(int deptId);


    int countByDeptId(@Param("deptId") int deptId);

    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByMobile(@Param("mobile") String mobile, @Param("id") Integer id);

    SysUser findByKeyword(@Param("keyword") String keyword);

    List<SysUser> findByIds(@Param("idList") List<Integer> idList);

}
