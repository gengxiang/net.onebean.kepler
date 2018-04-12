package net.onebean.kepler.common.dictionary;

import net.onebean.kepler.model.DicDictionary;
import net.onebean.kepler.service.DicDictionaryService;
import net.onebean.util.CollectionUtil;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther 0neBean
 * 字典工具类
 */
@Service
public class DictionaryUtils {

    public static Logger logger = (Logger) LoggerFactory.getLogger(DictionaryUtils.class);

    @Autowired
    private DicDictionaryService dicDictionaryService;


    /**
     * 静态字典
     */
    private static Map<String,List<DicDictionary>> dictionary = new HashMap<>();

    public void init(){
        List<DicDictionary> list = dicDictionaryService.findAll();
        List<DicDictionary> templist;
        if(CollectionUtil.isNotEmpty(list)){
            for(DicDictionary d:list){
                if (dictionary.get(d.getCode()) != null) {
                    templist = dictionary.get(d.getCode());
                } else {
                    templist = new ArrayList<>();
                }
                templist.add(d);
                dictionary.put(d.getCode(),templist);
            }
        }
    }

    /**
     * 获取字典词组
     * @param code
     * @return
     */
    public static List<DicDictionary> getDicGroup(String code){
        return dictionary.get(code);
    }

    /**
     * 获取字典数据
     * @param code
     * @return
     */
    public static String dic(String code,String val){
        String result = "nullDic";
        List<DicDictionary> list = dictionary.get(code);
        for (DicDictionary dicDictionary : list) {
            if (dicDictionary.getVal().equals(val)) {
                result =  dicDictionary.getDic();
            }
        }
        return result;
    }

}
