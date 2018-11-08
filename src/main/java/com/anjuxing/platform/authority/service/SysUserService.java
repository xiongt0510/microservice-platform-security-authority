package com.anjuxing.platform.authority.service;




import com.anjuxing.platform.authority.model.SysUser;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    SysUser findUserByName(String userName);


    /**
     * 根据手机查找用户
     * @param mobile
     * @return
     */
    SysUser findUserByMobile(String mobile);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    SysUser findUserByNamePassword(String username, String password);

    /**
     * 根据条件查询
     * @param user
     * @return
     */
    List<SysUser> findUsersByCondition(SysUser user);

    SysUser findUserRoleById(int id);



    boolean checkEmailExist(String mail, Integer userId);

    boolean checkTelephoneExist(String telephone, Integer userId);

    SysUser findByKeyword(String keyword);

    List<SysUser> findByDeptId(int deptId);


}
