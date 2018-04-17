package ${action_package_name};


import net.onebean.kepler.core.BaseController;
import ${model_package_name}.${table_name};
import ${service_package_name}.${table_name}Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/sysorg")
public class ${table_name}Controller extends BaseController<${table_name},${table_name}Service> {

}
