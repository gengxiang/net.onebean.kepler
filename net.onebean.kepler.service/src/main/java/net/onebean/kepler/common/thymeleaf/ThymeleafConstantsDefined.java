package net.onebean.kepler.common.thymeleaf;

import net.onebean.util.PropUtil;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * thymeleaf 常量定义
 * @author 0neBean
 */
@Component
public class ThymeleafConstantsDefined {


    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("oss_ctx", PropUtil.getConfig("aliyun.oss.host"));
            viewResolver.setStaticVariables(vars);
        }
    }
}
