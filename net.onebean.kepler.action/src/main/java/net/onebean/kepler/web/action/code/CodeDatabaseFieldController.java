package net.onebean.kepler.web.action.code;


import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.service.CodeDatabaseFieldService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("databasefield")
public class CodeDatabaseFieldController extends BaseController<CodeDatabaseField,CodeDatabaseFieldService> {
}
