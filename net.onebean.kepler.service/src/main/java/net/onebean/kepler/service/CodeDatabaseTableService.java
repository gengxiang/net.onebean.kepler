package net.onebean.kepler.service;

import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.CodeDatabaseTable;

import java.util.List;
import java.util.Map;

public interface CodeDatabaseTableService extends IBaseBiz<CodeDatabaseTable> {
    /**
     * 查询数据库所有表名
     * @return
     */
    List<String> findDatabaseTableList();

    /**
     * 根据ID删除子列表
     * @param id
     */
    void deleteChildList(Object id);
}