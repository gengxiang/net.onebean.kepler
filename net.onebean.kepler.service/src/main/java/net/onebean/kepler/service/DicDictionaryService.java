package net.onebean.kepler.service;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.DicDictionary;

public interface DicDictionaryService extends IBaseBiz<DicDictionary> {
    /**
     * 根据code  查询组下一个排序值
     * @param code
     * @return
     */
    Integer findGroupOrderNextNum(String code);
}