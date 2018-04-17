package net.onebean.kepler.common.thymeleaf;

import net.onebean.kepler.model.DicDictionary;
import net.onebean.kepler.common.dictionary.DictionaryUtils;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 0neBean
 * 自定义thymeleaf标签 dic字典标签 功能实现
 */
@Component
public class DictionaryElementProcessor extends AbstractMarkupSubstitutionElementProcessor {

    /**
     * 自定义方言
     */
    public DictionaryElementProcessor() {
        super("code");
    }

    /**
     * 自定义标签功能实现
     * @param arguments
     * @param element
     * @return
     */
    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        final String name = element.getAttributeValue("name");
        final String id = element.getAttributeValue("id");
        final String code = element.getAttributeValue("code");
        final String value = element.getAttributeValue("value");
        final String disabled = element.getAttributeValue("disabled");
        final String pattern = element.getAttributeValue("param-pattern");
        final String multiple = element.getAttributeValue("multiple");
        final String inChildList = element.getAttributeValue("inChildList");
        final Element select = new Element("select");
        select.setAttribute("data-am-selected","{btnSize: 'sm'}");
        select.setAttribute("name",name);
        select.setAttribute("id",id);
        StringBuffer classStr = new StringBuffer();

        if(StringUtils.isNotEmpty(inChildList) && inChildList.equals("true")){
            classStr.append(" onebean-child-list-item");
        }
        if (StringUtils.isNotEmpty(disabled) && disabled.equals("disabled")) {
            select.setAttribute("disabled",disabled);
        }
        if (StringUtils.isNotEmpty(pattern)) {
            classStr.append(" paramInput onebean-param-select-box");
            select.setAttribute("param-pattern",pattern);
        }
        if (StringUtils.isNotEmpty(multiple) && multiple.equals("true")){
            select.setAttribute("multiple","");
        }

        select.setAttribute("class",classStr.toString());
        List<DicDictionary> list = DictionaryUtils.getDicGroup(code);
        if(CollectionUtil.isNotEmpty(list)){
            if (StringUtils.isNotEmpty(pattern)) {
                final Element option = new Element("option");
                final Text text = new Text("未选择");
                option.addChild(text);
                option.setAttribute("value","  ");
                select.addChild(option);
            }
            for(DicDictionary d:list){

                final Element option = new Element("option");
                final Text text = new Text(d.getDic());
                if (StringUtils.isNotEmpty(value)) {
                    String [] valueArr = value.split(",");
                    for (String s : valueArr) {
                        if(s.equals(d.getVal())){
                            option.setAttribute("selected","");
                        }
                    }
                }
                option.setAttribute("value",d.getVal());
                option.addChild(text);
                select.addChild(option);
            }
        }
        /*
         * The abstract IAttrProcessor implementation we are using defines
         * that a list of nodes will be returned, and that these nodes
         * will substitute the tag we are processing.
         */
        final List<Node> nodes = new ArrayList<>();
        nodes.add(select);
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}