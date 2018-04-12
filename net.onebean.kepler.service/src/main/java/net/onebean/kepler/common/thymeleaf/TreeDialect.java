package net.onebean.kepler.common.thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 0neBean
 * 自定义thymeleaf标签 机构树
 */
@Component
public class TreeDialect extends AbstractDialect {

    @Override
    public String getPrefix() {
        return "tree";
    }
    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new OrgElementProcessor());
        processors.add(new MenuElementProcessor());
        return processors;
    }
}