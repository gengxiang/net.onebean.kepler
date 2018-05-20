package net.onebean.kepler.web.action.code;


import com.alibaba.fastjson.JSON;
import net.onebean.core.Condition;
import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.kepler.common.codeGenerate.CodeGenerateUtils;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.model.CodeDatabaseTable;
import net.onebean.kepler.model.SysPermission;
import net.onebean.kepler.service.CodeDatabaseFieldService;
import net.onebean.kepler.service.CodeDatabaseTableService;
import net.onebean.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private CodeGenerateUtils codeGenerateUtils;



    /**
     * 预览列表页面
     * @return view
     */
    @RequestMapping("preview")
    @Description(value = "预览列表页面")
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_PREVIEW')")
    public String preview() {
        return getView("list");
    }
    /**
     * 列表数据
     * @param sort 排序参数
     * @param page 分页参数
     * @param result 结果集
     * @param cond 表达式
     * @return PageResult<CodeDatabaseTable>
     */
    @RequestMapping("list")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_LIST')")
    public PageResult<CodeDatabaseTable> list (Sort sort, Pagination page, PageResult<CodeDatabaseTable> result, @RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"dic@SCDMFG$generate_type","dic@SCDMFW$generate_scope","date@create_time$");
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    /**
     * 新增按钮选择表页面
     * @param model modelAndView
     * @return view
     */
    @RequestMapping("select")
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_SELECT')")
    public String select(Model model){
        model.addAttribute("databaseList",baseService.findDatabaseTableList());
        return getView("select");
    }


    /**
     * 删除数据库模型及其关联字段
     * @param id 主键
     * @param result 结果集
     * @return PageResult<CodeDatabaseTable>
     */
    @RequestMapping(value = "delete/{id}")
    @Description(value = "删除")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_DELETE')")
    public PageResult<CodeDatabaseTable> delete(@PathVariable("id")Object id,PageResult<CodeDatabaseTable> result) {
        baseService.deleteById(id);
        baseService.deleteChildList(id);
        result.setFlag(true);
        return result;
    }


    /**
     * 编辑页面
     * @param model modelAndView
     * @param id 主键
     * @return view
     */
    @RequestMapping(value = "edit/{id}")
    @Description(value = "编辑页面")
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_EDIT')")
    public String edit(Model model, @PathVariable("id")Object id) {
        model.addAttribute("entity",baseService.findById(id));
        Condition condition = Condition.parseCondition("table_id@int@eq$");
        condition.setValue(id);
        model.addAttribute("fieldList",codeDatabaseFieldService.find(null,condition));
        model.addAttribute("edit",true);
        model.addAttribute("sysPermission",new SysPermission());
        return getView("detail");
    }

    /**
     * 查看页面
     * @param model modelAndView
     * @param id 主键
     * @return view
     */
    @RequestMapping(value = "view/{id}")
    @Description(value = "查看页面")
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_VIEW')")
    public String view(Model model,@PathVariable("id")Object id){
        model.addAttribute("entity",baseService.findById(id));
        Condition condition = Condition.parseCondition("table_id@int@eq$");
        condition.setValue(id);
        model.addAttribute("fieldList",codeDatabaseFieldService.find(null,condition));
        model.addAttribute("view",true);
        return getView("detail");
    }

    /**
     * 添加页面
     * @param tablename 表名
     * @param model modelAndView
     * @param entity 实体
     * @return view
     */
    @RequestMapping("add/{tablename}")
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_ADD')")
    public String add(@PathVariable("tablename")String tablename,Model model,CodeDatabaseTable entity){
        model.addAttribute("fieldList",codeDatabaseFieldService.findAllTableFieldbyTableName(tablename));
        model.addAttribute("add",true);
        entity.setTable_name(tablename);
        model.addAttribute("entity",entity);
        return getView("detail");
    }

    /**
     * 保存详情主体及子列表
     * @param entityStr 实体字符串
     * @param result 结果集
     * @return PageResult<CodeDatabaseTable>
     */
    @RequestMapping("save")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_SAVE')")
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


    /**
     * 生成代码
     * @param id 主键
     * @param result 结果集
     * @return PageResult<CodeDatabaseTable>
     * @throws Exception 抛出所有异常到页面处理
     */
    @RequestMapping("generate")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_GENERATE')")
    public PageResult<CodeDatabaseTable> generate(@RequestParam("id") Object id,PageResult<CodeDatabaseTable> result) throws Exception{
        CodeDatabaseTable entity = baseService.findById(id);
        Condition param = Condition.parseCondition("table_id@int@eq$");
        param.setValue(id);
        codeDatabaseFieldService.find(null,param);
        entity.setChildList(codeDatabaseFieldService.find(null,param));
        codeGenerateUtils.generate(entity);
        if (entity.getGenerate_scope().equals("page")){
            result.setMsg("代码已生成完毕,请添加菜单,并重启程序预览!");
        }else if (entity.getGenerate_scope().equals("controller")){
            result.setMsg("代码已生成完毕,重启程序后生效");
        }else if (entity.getGenerate_scope().equals("service")){
            result.setMsg("代码已生成完毕,请刷新目录查看");
        }
        return result;
    }


    /**
     * 数据去重接口
     * @param table_name 表名
     * @param result 结果集
     * @return PageResult<CodeDatabaseTable>
     */
    @RequestMapping(value = "isrepeattable")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_CODE_DATABASE_MODEL_IS_REPEAT')")
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
