package net.onebean.kepler.dao;
import net.onebean.core.BaseDao;
import net.onebean.kepler.model.CodeDatabaseTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CodeDatabaseTableDao extends BaseDao<CodeDatabaseTable> {
    /**
     * 查询数据库所有表名
     * @param databaseName
     * @return
     */
    List<String> findDatabaseTableList(@Param("databaseName")String databaseName);

}