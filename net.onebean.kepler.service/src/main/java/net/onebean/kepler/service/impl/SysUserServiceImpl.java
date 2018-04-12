package net.onebean.kepler.service.impl;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.service.SysUserService;
import net.onebean.kepler.dao.SysUserDao;

@Service
public class SysUserServiceImpl extends BaseBiz<SysUser, SysUserDao> implements SysUserService{

	public SysUser findByUserName(String username) {
		// TODO Auto-generated method stub
		return baseDao.findByUserName(username);
	}
}