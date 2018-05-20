package net.onebean.kepler.web.action.sys;


import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.VO.OrgTree;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysOrganization;
import net.onebean.kepler.service.SysOrganizationService;
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
 * 机构管理
 * @author 0neBean
 */
@Controller
@RequestMapping("/sysorg")
public class SysOrganizationController extends BaseController<SysOrganization,SysOrganizationService> {

    /**
     * 预览列表页面
     * @return view
     */
    @RequestMapping("preview")
    @Description(value = "预览列表页面")
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_PREVIEW')")
    public String preview() {
        return getView("list");
    }

    /**
     * 新增页面
     * @param model modelAndView
     * @param entity 实体
     * @return view
     */
    @RequestMapping("add")
    @Description(value = "新增页面")
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_ADD')")
    public String add(Model model,SysOrganization entity) {
        model.addAttribute("add",true);
        model.addAttribute("entity",entity);
        return getView("detail");
    }

    /**
     * 查看页面
     * @param model modelAndView
     * @param id 主键
     * @return view
     */
    @RequestMapping("view/{id}")
    @Description(value = "查看页面")
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_VIEW')")
    public String view(Model model,@PathVariable("id")Object id){
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("view",true);
        return getView("detail");
    }

    /**
     * 编辑页面
     * @param model modelAndView
     * @param id 主键
     * @return view
     */
    @RequestMapping("edit/{id}")
    @Description(value = "编辑页面")
        @PreAuthorize("hasPermission('$everyone','PERM_ORG_EDIT')")
    public String edit(Model model,@PathVariable("id")Object id) {
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("edit",true);
        return getView("detail");
    }

    /**
     * 保存
     * @param entity 实体
     * @param result 结果集
     * @return PageResult<SysOrganization>
     */
    @RequestMapping("save")
    @Description(value = "保存")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_SAVE')")
    public PageResult<SysOrganization> save(SysOrganization entity, PageResult<SysOrganization> result) {
        baseService.save(entity);
        result.getData().add(entity);
        return result;
    }
    /**
     * 列表数据
     * @param sort 排序参数
     * @param page 分页参数
     * @param result 结果集
     * @param cond 表达式
     * @return PageResult<SysOrganization>
     */
    @RequestMapping("list")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_LIST')")
    public PageResult<SysOrganization> list (Sort sort, Pagination page, PageResult<SysOrganization> result,
                                             @RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    /**
     * 根据ID删除
     * @param id 主键
     * @param result 结果集
     * @return PageResult<SysOrganization>
     */
    @RequestMapping("delete/{id}")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_DELETE')")
    public PageResult<SysOrganization> delete(@PathVariable("id")Object id, PageResult<SysOrganization> result) {
        baseService.deleteSelfAndChildById(Parse.toLong(id));
        result.setFlag(true);
        return result;
    }

    /**
     * 查出一级节点
     * @param page 分页参数
     * @param result 结果集
     * @param parent_id 父id
     * @param self_id 自身id
     * @return PageResult<OrgTree>
     */
    @RequestMapping("orgtree")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_TREE')")
    public PageResult<OrgTree> orgTree(Pagination page,PageResult<OrgTree> result,
            @RequestParam(value = "parent_id",required = false) Long parent_id,
            @RequestParam(value = "self_id",required = false) Long self_id){
        result.setData(baseService.findChildAsync(parent_id,self_id));
        result.setPagination(page);
        return result;
    }

    /**
     * 查出所有节点
     * @param parent_id 父id
     * @param result 结果集
     * @return PageResult<OrgTree>
     */
    @RequestMapping("allorgtree")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_ALL_TREE')")
    public PageResult<OrgTree> allorgTree(@RequestParam(value = "parent_id",required = false) Long parent_id,PageResult<OrgTree> result){
        List<SysOrganization> list = baseService.findChildSync(parent_id);
        result.setData(baseService.organizationToOrgTree(list,null));
        return result;
    }

    /**
     * 添加子项
     * @param model modelAndView
     * @param parent_id 父id
     * @param entity 实体
     * @return view
     */
    @RequestMapping("addchild")
    @PreAuthorize("hasPermission('$everyone','PERM_ORG_ADD_CHILD')")
    public String addChild(Model model, @RequestParam("parent_id")Long parent_id,SysOrganization entity) {
        /*获取当前最大排序值*/
        entity.setSort(baseService.findChildOrderNextNum(parent_id));
        model.addAttribute("entity",entity);
        model.addAttribute("add",true);
        return getView("detail");
    }
}
