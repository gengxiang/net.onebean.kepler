package net.onebean.kepler.service.impl;

import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.kepler.dao.CodeDatabaseTableDao;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.model.CodeDatabaseTable;
import net.onebean.kepler.service.CodeDatabaseFieldService;
import net.onebean.kepler.service.CodeDatabaseTableService;
import net.onebean.util.CollectionUtil;
import net.onebean.util.PropUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeDatabaseTableServiceImpl extends BaseBiz<CodeDatabaseTable, CodeDatabaseTableDao> implements CodeDatabaseTableService{

    @Autowired
    private CodeDatabaseFieldService codeDatabaseFieldService;

    @Override
    public List<String> findDatabaseTableList() {
        return baseDao.findDatabaseTableList(PropUtil.getConfig("database.name"));
    }

    @Override
    public void deleteChildList(Object id) {
        Condition condition = Condition.parseCondition("table_id@int@eq$");
        condition.setValue(id);
        List<CodeDatabaseField> childList = codeDatabaseFieldService.find(null,condition);
        if(CollectionUtil.isNotEmpty(childList)){
            List<Long> ids = new ArrayList<>();
            for (CodeDatabaseField codeDatabaseField : childList) {
                ids.add(codeDatabaseField.getId());
            }
            codeDatabaseFieldService.deleteByIds(ids);
        }
    }
}