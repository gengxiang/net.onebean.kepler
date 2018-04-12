package net.onebean.kepler.service.impl;
import net.onebean.kepler.service.SysRoleService;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.SysRole;
import net.onebean.kepler.dao.SysRoleDao;

import java.util.List;

@Service
public class SysRoleServiceImpl extends BaseBiz<SysRole, SysRoleDao> implements SysRoleService {
    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return baseDao.findRolesByUserId(userId);
    }
}