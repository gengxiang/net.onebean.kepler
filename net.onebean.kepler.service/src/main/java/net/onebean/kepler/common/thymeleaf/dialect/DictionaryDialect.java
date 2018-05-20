package net.onebean.kepler.common.thymeleaf.dialect;

import net.onebean.kepler.common.thymeleaf.processor.DictionaryElementProcessor;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 0neBean
 * 自定义thymeleaf方言 字典
 */
@Component
public class DictionaryDialect implements IProcessorDialect {


    @Override
    public String getPrefix() {
        return "dic";
    }

    @Override
    public int getDialectProcessorPrecedence() {
        return 1000;
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new DictionaryElementProcessor(dialectPrefix));
        return processors;
    }

    @Override
    public String getName() {
        return "dic";
    }
}