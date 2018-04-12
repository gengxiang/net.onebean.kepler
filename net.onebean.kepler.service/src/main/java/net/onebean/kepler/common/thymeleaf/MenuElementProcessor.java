package net.onebean.kepler.common.thymeleaf;

import net.onebean.kepler.service.SysPermissionService;
import net.onebean.util.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 0neBean
 * 自定义标签 转义org树的选择结果
 */
@Component
public class MenuElementProcessor extends AbstractMarkupSubstitutionElementProcessor {

    /**
     * 自定义方言
     */
    public MenuElementProcessor() {
        super("menu");
    }


    /**
     * @param arguments
     * @param element
     * @return
     */
    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        final ApplicationContext appCtx = ((SpringWebContext)arguments.getContext()).getApplicationContext();
        final String pid = element.getAttributeValue("pid");
        final String selfId = element.getAttributeValue("selfId");
        final String disabled = element.getAttributeValue("disabled");
        final Element input = new Element("input");
        final SysPermissionService sysPermissionService =appCtx.getBean(SysPermissionService.class);
        input.setAttribute("type","text");
        input.setAttribute("class","tpl-form-input treeSelector");
        input.setAttribute("placeholder","请选择上级菜单");
        input.setAttribute("onclick","modalMenuTree("+selfId+")");
        input.setAttribute("name","menuTree");

        if (pid == null || pid.equals("0")) {
                input.setAttribute("value","未选择菜单");
        } else {
            input.setAttribute("value",sysPermissionService.findById(pid).getDescritpion());
        }
        if (StringUtils.isNotEmpty(disabled) && disabled.equals("disabled")) {
            input.setAttribute("disabled",disabled);
        }
        /*
         * The abstract IAttrProcessor implementation we are using defines
         * that a list of nodes will be returned, and that these nodes
         * will substitute the tag we are processing.
         */
        final List<Node> nodes = new ArrayList<>();
        nodes.add(input);
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
