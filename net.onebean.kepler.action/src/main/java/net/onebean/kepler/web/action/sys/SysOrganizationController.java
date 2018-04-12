package net.onebean.kepler.web.action.sys;


import net.onebean.core.*;
import net.onebean.core.extend.Sort;
import net.onebean.kepler.VO.OrgTree;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysOrganization;
import net.onebean.kepler.service.SysOrganizationService;
import net.onebean.kepler.service.SysTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/sysorg")
public class SysOrganizationController extends BaseController<SysOrganization,SysOrganizationService> {

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

    @RequestMapping("orgtree")
    @ResponseBody
    public PageResult<OrgTree> orgTree(Pagination page,PageResult<OrgTree> result,
            @RequestParam(value = "parent_id",required = false) Long parent_id,
            @RequestParam(value = "self_id",required = false) Long self_id){
        List<SysOrganization> list = baseService.findChildSync(parent_id);
        result.setData(baseService.organizationToOrgTree(list,self_id));
        result.setPagination(page);
        return result;
    }

    @RequestMapping("allorgtree")
    @ResponseBody
    public PageResult<OrgTree> allorgTree(@RequestParam(value = "parent_id",required = false) Long parent_id,PageResult<OrgTree> result){
        List<SysOrganization> list = baseService.findChildSync(parent_id);
        result.setData(baseService.organizationToOrgTree(list,null));
        return result;
    }
}
