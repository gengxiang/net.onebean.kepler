package net.onebean.kepler.web.action.sys;


import net.onebean.core.*;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysRole;
import net.onebean.kepler.model.SysRoleUser;
import net.onebean.kepler.service.SysPermissionRoleService;
import net.onebean.kepler.service.SysRoleService;
import net.onebean.kepler.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sysrole")
public class SysRoleController extends BaseController<SysRole,SysRoleService> {

    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;

    @RequestMapping("list")
    @ResponseBody
    public PageResult<SysRole> list (Sort sort, Pagination page,PageResult<SysRole> result
            ,@RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"dic@SF$is_lock","dic@SJQX$data_permission_level","date@creat_time$");
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    @RequestMapping("findbyuid")
    @ResponseBody
    public PageResult<SysRole> findRolesByUserId(@RequestParam("userId")Long userId,PageResult<SysRole> result){
        result.setData(baseService.findRolesByUserId(userId));
        return result;
    }

    @RequestMapping("findbyname")
    @ResponseBody
    public PageResult<SysRole> findByName(@RequestParam("name")String name,Pagination page,PageResult<SysRole> result){
        ListPageQuery query = new ListPageQuery();
        ConditionMap map = new ConditionMap();
        Sort sort = new Sort();
        map.parseCondition(MessageFormat.format("ch_name@string@like${0}-is_lock@string@eq${1}",name,0));
        sort.setSort(Sort.DESC);
        sort.setOrderBy("id");
        query.setConditions(map);
        query.setPagination(page);
        query.setSort(sort);
        result.setPagination(page);
        result.setData(baseService.find(query));
        return result;
    }


    @RequestMapping("addroleuser")
    @ResponseBody
    public PageResult<SysRole> addRoleUser(@RequestParam("userId")Long userId,PageResult<SysRole> result,
                                           @RequestParam("roleIds")String roleIds,Pagination page){
        String[] roleIdsArry = roleIds.split(",");
        for (String s : roleIdsArry) {
            SysRoleUser temp = new SysRoleUser(userId, Parse.toLong(s));
            sysRoleUserService.save(temp);
        }
        result.setFlag(true);
        return result;
    }

    @RequestMapping("removeroleuser")
    @ResponseBody
    public PageResult<SysRole> removeRoleUser(@RequestParam("urIds")String urIds,PageResult<SysRole> result){
        String[] urIdsArry = urIds.split(",");
        List <Long> ids = new ArrayList<>();
        for (String s : urIdsArry) {
            ids.add(Parse.toLong(s));
        }
        sysRoleUserService.deleteByIds(ids);
        result.setFlag(true);
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
    public PageResult<SysRole> delete(Model model, @PathVariable("id")Object id, PageResult<SysRole> result) {
        baseService.deleteById(id);
        Long roleId = Parse.toLong(id);
        sysRoleUserService.deleteByRoleId(roleId);
        sysPermissionRoleService.deteleByRoleId(roleId);
        result.setFlag(true);
        return result;
    }

}
