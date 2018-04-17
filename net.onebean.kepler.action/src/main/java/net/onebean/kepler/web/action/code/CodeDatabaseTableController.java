package net.onebean.kepler.web.action.code;


import com.alibaba.fastjson.JSON;
import net.onebean.core.Condition;
import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.model.CodeDatabaseTable;
import net.onebean.kepler.service.CodeDatabaseFieldService;
import net.onebean.kepler.service.CodeDatabaseTableService;
import net.onebean.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 0neBean
 * 生成代码模型管理
 */
@Controller
@RequestMapping("/databasetable")
public class CodeDatabaseTableController extends BaseController<CodeDatabaseTable,CodeDatabaseTableService> {

    @Autowired
    private CodeDatabaseFieldService codeDatabaseFieldService;



    /**
     * 列表页
     * @param sort
     * @param page
     * @param result
     * @param cond
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public PageResult<CodeDatabaseTable> list (Sort sort, Pagination page, PageResult<CodeDatabaseTable> result, @RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"dic@SCDMFG$generate_type","dic@SCDMFW$generate_scope","date@create_time$");
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    /**
     * 新增按钮选择表页面
     * @param model
     * @return
     */
    @RequestMapping("select")
    public String select(Model model){
        model.addAttribute("databaseList",baseService.findDatabaseTableList());
        return getView("select");
    }

    /**
     * 添加页面
     * @param tablename
     * @param model
     * @param entity
     * @return
     */
    @RequestMapping("add/{tablename}")
    public String add(@PathVariable("tablename")String tablename,Model model,CodeDatabaseTable entity){
        model.addAttribute("fieldList",codeDatabaseFieldService.findAllTableFieldbyTableName(tablename));
        model.addAttribute("add",true);
        entity.setTable_name(tablename);
        model.addAttribute("entity",entity);
        return getView("detail");
    }

    /**
     * 保存详情主体及子列表
     * @param entityStr
     * @param result
     * @return
     */
    @RequestMapping("savewithchild")
    @ResponseBody
    public PageResult<CodeDatabaseTable> add(@RequestParam("entity") String entityStr,PageResult<CodeDatabaseTable> result){
        CodeDatabaseTable entity = JSON.parseObject(entityStr,CodeDatabaseTable.class);
        List<CodeDatabaseField> childList = entity.getChildList();
        if (entity.getId() != null) {
            baseService.update(entity);
        } else {
            baseService.save(entity);
        }
        for (CodeDatabaseField codeDatabaseField : childList) {
            codeDatabaseField.setTable_id(entity.getId());
        }
        baseService.deleteChildList(entity.getId());
        codeDatabaseFieldService.saveBatch(childList);
        return result;
    }



    @RequestMapping("generate")
    @ResponseBody
    public PageResult<CodeDatabaseTable> generate(@RequestParam("entity") String entityStr,PageResult<CodeDatabaseTable> result){
        CodeDatabaseTable entity = JSON.parseObject(entityStr,CodeDatabaseTable.class);
        List<CodeDatabaseField> childList = entity.getChildList();
        return result;
    }


    /**
     * 删除
     * @param model
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    @Description(value = "删除")
    @ResponseBody
    public PageResult<CodeDatabaseTable> delete(Model model,@PathVariable("id")Object id,PageResult<CodeDatabaseTable> result) {
        baseService.deleteById(id);
        baseService.deleteChildList(id);
        result.setFlag(true);
        return result;
    }

    /**
     * 编辑页面
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}")
    @Description(value = "编辑页面")
    public String edit(Model model,@PathVariable("id")Object id) {
        model.addAttribute("entity",baseService.findById(id));
        Condition condition = Condition.parseCondition("table_id@int@eq$");
        condition.setValue(id);
        model.addAttribute("fieldList",codeDatabaseFieldService.find(null,condition));
        model.addAttribute("edit",true);
        return getView("detail");
    }

    /**
     * 查看页面
     * @param model
     * @return
     */
    @RequestMapping(value = "view/{id}")
    @Description(value = "查看页面")
    public String view(Model model,@PathVariable("id")Object id){
        model.addAttribute("entity",baseService.findById(id));
        Condition condition = Condition.parseCondition("table_id@int@eq$");
        condition.setValue(id);
        model.addAttribute("fieldList",codeDatabaseFieldService.find(null,condition));
        model.addAttribute("view",true);
        return getView("detail");
    }

    /**
     * 数据去重接口
     * @return
     */
    @RequestMapping(value = "isrepeattable")
    @ResponseBody
    public PageResult<CodeDatabaseTable> isRepeatTable(@RequestParam("table_name")String table_name,PageResult<CodeDatabaseTable> result){
        Condition param = Condition.parseCondition("table_name@string@eq$");
        param.setValue(table_name);
        if (CollectionUtil.isNotEmpty(baseService.find(null,param))){
            result.setFlag(false);
            result.setMsg("该表已存在模型,不能创建!");
            return result;
        }else{
            result.setFlag(true);
        }
        return result;
    }


}
