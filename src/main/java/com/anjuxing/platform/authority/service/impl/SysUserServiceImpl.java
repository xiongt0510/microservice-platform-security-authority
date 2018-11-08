package com.anjuxing.platform.authority.service.impl;


import com.anjuxing.platform.authority.exception.ParamException;
import com.anjuxing.platform.authority.mapper.SysUserMapper;
import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.service.SysLogService;
import com.anjuxing.platform.authority.service.SysUserService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public SysUser findUserByName(String userName) {
        return sysUserMapper.findByName(userName);
    }


    @Override
    public SysUser findUserByMobile(String mobile) {
        return sysUserMapper.findByMobile(mobile);
    }

    @Override
    public SysUser findUserByNamePassword(String username, String password) {
        return sysUserMapper.findByNamePassword(username,password);
    }

    @Override
    public List<SysUser> findUsersByCondition(SysUser user) {
        return sysUserMapper.findByCondition(user);
    }


    @Override
    public SysUser findUserRoleById(int id) {
        return sysUserMapper.findRoleById(id);
    }


    @Override
    public int save(SysUser sysUser) {


        if(checkTelephoneExist(sysUser.getMobile(), sysUser.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(sysUser.getEmail(), sysUser.getId())) {
            throw new ParamException("邮箱已被占用");
        }


//        String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());

        SysUser user = SysUser.builder().deptId(sysUser.getDeptId()).email(sysUser.getEmail())
                .imageUrl(sysUser.getImageUrl()).locked(sysUser.getLocked()).mobile(sysUser.getMobile())
                .rank(sysUser.getRank()).remark(sysUser.getRemark()).sex(sysUser.getSex())
                .username(sysUser.getUsername()).build();

        if (!StringUtils.isEmpty(sysUser.getPassword())){
            user.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }
//        user.setCreator(RequestHolder.getCurrentUser().getUsername());
        user.setCreator("system");
        user.setCreateTime(new Date());

        //todo:sendEmail

        int result = sysUserMapper.save(user);
        sysLogService.saveUserLog(null, user);

        return result;
    }


    @Override
    public int update(SysUser sysUser) {

        if(checkTelephoneExist(sysUser.getMobile(), sysUser.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(sysUser.getEmail(), sysUser.getId())) {
            throw new ParamException("邮箱已被占用");
        }

        SysUser before = sysUserMapper.findById(sysUser.getId());

        Preconditions.checkNotNull(before, "待更新的用户不存在");

        SysUser after = SysUser.builder().username(sysUser.getUsername()).email(sysUser.getEmail())
                .deptId(sysUser.getDeptId()).imageUrl(sysUser.getImageUrl()).locked(sysUser.getLocked())
                .mobile(sysUser.getMobile()).rank(sysUser.getRank()).remark(sysUser.getRemark()).sex(sysUser.getSex()).build();
        after.setId(sysUser.getId());
        after.setCreateTime(new Date());
        after.setCreator("system");

        int result = sysUserMapper.update(after);

        sysLogService.saveUserLog(before, after);

        return result;
    }

    public boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByMobile(telephone, userId) > 0;
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }


    @Override
    public List<SysUser> findByDeptId(int deptId){
        List<SysUser> users = Lists.newArrayList();
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
             users = sysUserMapper.findByDept(deptId);
        }
        return users;
    }
}
