package net.onebean.kepler.service.impl;
import net.onebean.kepler.dao.SysTreeDao;
import net.onebean.kepler.model.SysTree;
import net.onebean.kepler.service.SysTreeService;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;

import java.util.List;

@Service
public class SysTreeServiceImpl extends BaseBiz<SysTree, SysTreeDao> implements SysTreeService {
    @Override
    public List<SysTree> findList(Integer parentid) {
        return baseDao.findList(parentid);
    }
}