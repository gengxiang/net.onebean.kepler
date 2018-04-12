package net.onebean.kepler.service.impl;
import net.onebean.kepler.service.SysRoleUserService;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.SysRoleUser;
import net.onebean.kepler.dao.SysRoleUserDao;

@Service
public class SysRoleUserServiceImpl extends BaseBiz<SysRoleUser, SysRoleUserDao> implements SysRoleUserService {

    @Override
    public void deleteByUserId(Long userId) {
        baseDao.deleteByUserId(userId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        baseDao.deleteByRoleId(roleId);
    }
}