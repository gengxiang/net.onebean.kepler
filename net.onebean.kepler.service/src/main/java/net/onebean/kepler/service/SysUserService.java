package net.onebean.kepler.service;

import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.SysUser;

public interface SysUserService extends IBaseBiz<SysUser> {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUser findByUserName(String username);
}