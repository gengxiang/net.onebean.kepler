package net.onebean.kepler.web.action.sys;


import net.onebean.core.Condition;
import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.security.OneBeanPasswordEncoder;
import net.onebean.kepler.security.SpringSecurityUtil;
import net.onebean.kepler.service.SysRoleUserService;
import net.onebean.kepler.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 * @author 0neBean
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController<SysUser,SysUserService> {

    @Autowired
    private OneBeanPasswordEncoder oneBeanPasswordEncoder;
    @Autowired
    private SysRoleUserService sysRoleUserService;


    /**
     * 编辑页面
     * @param model modelAndView
     * @param id 主键
     * @return view
     */
    @RequestMapping("edit/{id}")
    @Description(value = "编辑页面")
    @PreAuthorize("hasPermission('$everyone','PERM_USER_EDIT')")
    public String edit(Model model,@PathVariable("id")Object id) {
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("edit",true);
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
    @PreAuthorize("hasPermission('$everyone','PERM_USER_VIEW')")
    public String view(Model model,@PathVariable("id")Object id){
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("view",true);
        return getView("detail");
    }

    /**
     * 新增页面
     * @param model modelAndView
     * @param entity 实体
     * @return view
     */
    @RequestMapping("add")
    @Description(value = "新增页面")
    @PreAuthorize("hasPermission('$everyone','PERM_USER_ADD')")
    public String add(Model model,SysUser entity) {
        model.addAttribute("add",true);
        model.addAttribute("entity",entity);
        return getView("detail");
    }

    /**
     * 保存
     * @param entity 实体
     * @param result 结果集
     * @return PageResult<SysUser>
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_USER_SAVE')")
    public PageResult<SysUser> save(SysUser entity, PageResult<SysUser> result) {
        if (null != entity.getPassword() && entity.getPassword().length() != 80){
            //页面限制用户密码长度为30,加密过得密码长度为80 未加密就加个密吧!
            entity.setPassword(oneBeanPasswordEncoder.encode(entity.getPassword()));
        }
        baseService.save(entity);
        result.getData().add(entity);
        return result;
    }

    /**
     * 预览列表页面
     * @return view
     */
    @RequestMapping("preview")
    @Description(value = "预览列表页面")
    @PreAuthorize("hasPermission('$everyone','PERM_USER_PREVIEW')")
    public String preview() {
        return getView("list");
    }


    /**
     * 列表数据
     * @param sort 排序参数
     * @param page 分页参数
     * @param result 结果集
     * @param cond 表达式
     * @return PageResult<SysUser>
     */
    @RequestMapping("list")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_USER_LIST')")
    public PageResult<SysUser> list (Sort sort, Pagination page, PageResult<SysUser> result,@RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"dic@SF$is_lock","dic@YHLX$user_type","date@creat_time$");
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }

    /**
     * 根据ID删除
     * @param id 主键
     * @param result 结果集
     * @return PageResult<SysUser>
     */
    @RequestMapping(value = "delete/{id}")
    @Description(value = "删除")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_USER_DELETE')")
    public PageResult<SysUser> delete(Model model, @PathVariable("id")Object id, PageResult<SysUser> result) {
        baseService.deleteById(id);
        sysRoleUserService.deleteByUserId(Parse.toLong(id));
        result.setFlag(true);
        return result;
    }

    /**
     * 重置密码
     * @param id 主键
     * @param result 结果集
     * @return PageResult<SysUser>
     */
    @RequestMapping(value = "resetpassword/{id}")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_USER_RESET_PASSWORD')")
    public PageResult<SysUser> resetpassword(@PathVariable("id")Object id, PageResult<SysUser> result) {
        SysUser entity =  baseService.findById(id);
        entity.setPassword(oneBeanPasswordEncoder.encode("123456"));
        baseService.update(entity);
        result.setFlag(true);
        return result;
    }

    /**
     * 账户设置
     * @param request HttpServletRequest
     * @param model modelAndView
     * @return view
     */
    @RequestMapping(value = "setting")
    @PreAuthorize("hasPermission('$everyone','PERM_USER_SETTING')")
    public String setting(HttpServletRequest request,Model model) {
        SysUser entity = SpringSecurityUtil.getCurrentLoginUser(request);
        model.addAttribute("entity",entity);
        model.addAttribute("edit",true);
        return getView("setting");
    }

    /**
     * 设置密码
     * @param request HttpServletRequest
     * @param model modelAndView
     * @return view
     */
    @RequestMapping(value = "setpassword")
    @PreAuthorize("hasPermission('$everyone','PERM_USER_SETTING_PASSWORD')")
    public String setpassword(HttpServletRequest request,Model model) {
        model.addAttribute("entity",SpringSecurityUtil.getCurrentLoginUser(request));
        return getView("setpassword");
    }

    /**
     * 根据用户'orgid'查找用户
     * @param orgId 机构ID
     * @param result 结果集
     * @return view
     */
    @RequestMapping(value = "finduserbyorgid")
    @ResponseBody
    @PreAuthorize("hasPermission('$everyone','PERM_USER_FIND_BY_ORGID')")
    public PageResult<SysUser> findUserByOrgId(@RequestParam("orgId")String orgId,PageResult<SysUser> result){
        Condition param =  Condition.parseCondition("org_id@string@eq");
        param.setValue(orgId);
        result.setData(baseService.find(null,param));
        return  result;
    }





}
