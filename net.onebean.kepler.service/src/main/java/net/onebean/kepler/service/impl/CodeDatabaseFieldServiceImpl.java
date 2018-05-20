package net.onebean.kepler.service.impl;
import net.onebean.util.PropUtil;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.service.CodeDatabaseFieldService;
import net.onebean.kepler.dao.CodeDatabaseFieldDao;

import java.util.List;

@Service
public class CodeDatabaseFieldServiceImpl extends BaseBiz<CodeDatabaseField, CodeDatabaseFieldDao> implements CodeDatabaseFieldService{

    @Override
    public List<CodeDatabaseField> findAllTableFieldbyTableName(String tablename) {
        return baseDao.findAllTableFieldbyTableName(PropUtil.getConfig("database.name"),tablename);
    }
}