package ${service_package_name}.impl;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import ${model_package_name}.${table_name};
import ${service_package_name}.${table_name}Service;
import ${dao_package_name}.${table_name}Dao;

/**
* @author ${author}
* @description ${description} serviceImpl
* @date ${create_time}
*/
@Service
public class ${table_name}ServiceImpl extends BaseBiz<${table_name}, ${table_name}Dao> implements ${table_name}Service{

}