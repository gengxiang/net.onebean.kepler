package net.onebean.kepler.dao;
import org.apache.ibatis.annotations.Param;

import net.onebean.core.BaseDao;
import net.onebean.kepler.model.SysUser;

public interface SysUserDao extends BaseDao<SysUser> {
	
    public SysUser findByUserName(@Param("username")String username);

}