package net.onebean.kepler.service;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.CodeDatabaseField;

import java.util.List;

public interface CodeDatabaseFieldService extends IBaseBiz<CodeDatabaseField> {

    /**
     * 根据表名和数据库名查出所有字段
     * @param tablename
     * @return
     */
    List<CodeDatabaseField> findAllTableFieldbyTableName(String tablename);
}