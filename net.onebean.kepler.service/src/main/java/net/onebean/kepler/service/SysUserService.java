package net.onebean.kepler.service;

import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.SysUser;

public interface SysUserService extends IBaseBiz<SysUser> {
	
    public SysUser findByUserName(String username);
}