package net.onebean.kepler.web.action.sys;


import net.onebean.core.*;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.VO.OrgTree;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysOrganization;
import net.onebean.kepler.service.SysOrganizationService;
import net.onebean.kepler.service.SysTreeService;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/sysorg")
public class SysOrganizationController extends BaseController<SysOrganization,SysOrganizationService> {
    /**
     * 列表数据
     * @param sort
     * @param page
     * @param result
     * @param cond
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public PageResult<SysOrganization> list (Sort sort, Pagination page, PageResult<SysOrganization> result,
                                             @RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"date@creat_time$");
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    /**
     * 查出一级节点
     * @param page
     * @param result
     * @param parent_id
     * @param self_id
     * @return
     */
    @RequestMapping("orgtree")
    @ResponseBody
    public PageResult<OrgTree> orgTree(Pagination page,PageResult<OrgTree> result,
            @RequestParam(value = "parent_id",required = false) Long parent_id,
            @RequestParam(value = "self_id",required = false) Long self_id){
        result.setData(baseService.findChildAsync(parent_id,self_id));
        result.setPagination(page);
        return result;
    }

    /**
     * 查出所有节点
     * @param parent_id
     * @param result
     * @return
     */
    @RequestMapping("allorgtree")
    @ResponseBody
    public PageResult<OrgTree> allorgTree(@RequestParam(value = "parent_id",required = false) Long parent_id,PageResult<OrgTree> result){
        List<SysOrganization> list = baseService.findChildSync(parent_id);
        result.setData(baseService.organizationToOrgTree(list,null));
        return result;
    }

    /**
     * 删除
     * @param model
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public PageResult<SysOrganization> delete(Model model, @PathVariable("id")Object id, PageResult<SysOrganization> result) {
        baseService.deleteSelfAndChildById(Parse.toLong(id));
        result.setFlag(true);
        return result;
    }
}
