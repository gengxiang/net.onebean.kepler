package net.onebean.kepler.service.impl;
import net.onebean.kepler.service.DicDictionaryService;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.DicDictionary;
import net.onebean.kepler.dao.DicDictionaryDao;

@Service
public class DicDictionaryServiceImpl extends BaseBiz<DicDictionary, DicDictionaryDao> implements DicDictionaryService {

    @Override
    public Integer findGroupOrderNextNum(String code) {
        return baseDao.findGroupOrderNextNum(code);
    }
}