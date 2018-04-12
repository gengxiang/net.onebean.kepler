package net.onebean.kepler.web.action.dic;


import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.DicDictionary;
import net.onebean.kepler.service.DicDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dic")
public class DicDictionaryController extends BaseController<DicDictionary,DicDictionaryService> {

    /**
     * 添加子项
     * @param model
     * @return
     */
    @RequestMapping(value = "group/{id}")
    public String group(Model model, @PathVariable("id")Object id) {
        DicDictionary entity = baseService.findById(id);
        entity.setSort(baseService.findGroupOrderNextNum(entity.getCode()));
        model.addAttribute("entity",entity);
        model.addAttribute("group",true);
        return getView("group");
    }

}
