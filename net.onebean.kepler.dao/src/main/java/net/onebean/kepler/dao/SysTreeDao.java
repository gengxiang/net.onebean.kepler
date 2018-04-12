package net.onebean.kepler.dao;
import net.onebean.core.BaseDao;
import net.onebean.kepler.model.SysTree;

import java.util.List;

public interface SysTreeDao extends BaseDao<SysTree> {

    public List<SysTree> findList(int parentid);
}