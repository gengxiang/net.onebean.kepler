package net.onebean.kepler.web.action.sys;


import net.onebean.core.Condition;
import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.form.Parse;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.VO.MenuTree;
import net.onebean.kepler.model.SysPermission;
import net.onebean.kepler.model.SysPermissionRole;
import net.onebean.kepler.security.SpringSecurityUtil;
import net.onebean.kepler.service.SysPermissionRoleService;
import net.onebean.kepler.service.SysPermissionService;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/syspremission")
public class SysPremissionController extends BaseController<SysPermission,SysPermissionService> {

    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;

    /**
     * 查出一级节点
     * @param page
     * @param result
     * @param parent_id
     * @param self_id
     * @return
     */
    @RequestMapping("menutree")
    @ResponseBody
    public PageResult<MenuTree> MenuTree(Pagination page,PageResult<MenuTree> result,
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
    @RequestMapping("allmenutree")
    @ResponseBody
    public PageResult<MenuTree> allMenuTree(@RequestParam(value = "parent_id",required = false) Long parent_id,PageResult<MenuTree> result){
        List<SysPermission> list = baseService.findChildSync(parent_id,false);//查出url和menu的数据
        list = dicCoverTree("childList",list,"dic@CDLX$menu_type");
        result.setData(baseService.permissionToMenuTree(list,null));
        return result;
    }

    /**
     * 所有有权限的菜单
     * @param result
     * @param request
     * @return
     */
    @RequestMapping("allpermissiontree")
    @ResponseBody
    public PageResult<SysPermission> allPermissionTree(PageResult<SysPermission> result,HttpServletRequest request){
        List<SysPermission> list = baseService.findChildSync(null,true);//只查出menu的数据
        list = list.get(0).getChildList();
        list = dicCoverTree("childList",list,"dic@CDLX$menu_type");
        list = baseService.getCurrentLoginUserHasPermission(list,SpringSecurityUtil.getCurrentPermissions(request));
        result.setData(list);
        return result;
    }

    /**
     * 获取对应角色的所有菜单的树
     * @param roleId
     * @param result
     * @return
     */
    @RequestMapping("getrolepremission")
    @ResponseBody
    public PageResult<MenuTree> getRolePremission(@RequestParam(value = "roleId") Long roleId,PageResult<MenuTree> result){
        List<SysPermission> list = baseService.findChildSync(null,false);//查出url和menu的数据
        result.setData(baseService.permissionToMenuTreeForRole(list,null,roleId));
        return result;
    }

    /**
     * 保存角色拥有的菜单权限
     * @param premIds
     * @param result
     * @param roleId
     * @return
     */
    @RequestMapping("savepremissionrole")
    @ResponseBody
    public PageResult<SysPermissionRole> savePremissionRole(@RequestParam("premIds")String premIds,PageResult<SysPermissionRole> result
            ,@RequestParam("roleId")String roleId){
        sysPermissionRoleService.deteleByRoleId(Parse.toLong(roleId));
        if (StringUtils.isNotEmpty(premIds)){
            sysPermissionRoleService.insertBatch(premIds,roleId);
        }
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
    public PageResult<SysPermission> delete(Model model, @PathVariable("id")Object id, PageResult<SysPermission> result) {
        baseService.deleteSelfAndChildById(Parse.toLong(id));
        sysPermissionRoleService.deteleByPermissionId(Parse.toLong(id));
        result.setFlag(true);
        return result;
    }

    /**
     *  防止菜单url重复
     * @param url
     * @param id
     * @param result
     * @return
     */
    @RequestMapping(value = "urlrepeat")
    @ResponseBody
    public PageResult<SysPermission> urlRepeat(@RequestParam("url")String url,
                                               @RequestParam("id")Long id,PageResult<SysPermission> result){
        Condition urlCondition= Condition.parseCondition("url@string@eq");
        urlCondition.setValue(url);
        List <SysPermission> list = baseService.find(new Pagination(),urlCondition);
        if(CollectionUtil.isEmpty(list)){
            result.setFlag(true);
        }else{
            if(id == null){
                result.setFlag(false);
            }else{
                result.setFlag((list.get(0).getId() == id)?true:false);
            }
        }
        return result;
    }

}
