package net.onebean.kepler.service;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.SysTree;

import java.util.List;

public interface SysTreeService extends IBaseBiz<SysTree> {
    public List<SysTree> findList(Integer parentid);
}