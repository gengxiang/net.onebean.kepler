package net.onebean.kepler.common.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("BeanDefineConfigue")
/**
 * @Auther 0neBean
 * spring 初始化资源
 */
public class BeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DictionaryUtils dicDictionaryUtils;

    //0neBean:当一个ApplicationContext被初始化或刷新触发 加载字典到内存中
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        dicDictionaryUtils.logger.debug("0neBean：START:spring初始化开始======================>");
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            dicDictionaryUtils.logger.debug("启动projectInit的start方法进行参数的初始化======================>");
            dicDictionaryUtils.init();
        } else {
            //为什么会执行两次：请参考博文：http://www.cnblogs.com/yucongblog/p/5437744.html
            dicDictionaryUtils.logger.debug("spring初始化时,执行onApplicationEvent:event.getApplicationContext().getParent() != null======================>");
        }
        dicDictionaryUtils.logger.debug("0neBean:END:spring初始化完毕======================>");
    }

}