package net.onebean.kepler.web.action.sys;


import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.common.dataPerm.DataPermUtils;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.security.OneBeanPasswordEncoder;
import net.onebean.kepler.security.SpringSecurityUtil;
import net.onebean.kepler.service.SysRoleUserService;
import net.onebean.kepler.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController<SysUser,SysUserService> {

    @Autowired
    private OneBeanPasswordEncoder oneBeanPasswordEncoder;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private DataPermUtils dataPermUtils;

    @RequestMapping("list")
    @ResponseBody
    public PageResult<SysUser> list (Sort sort, Pagination page, PageResult<SysUser> result,@RequestParam(value = "conditionList",required = false) String cond){
        initData(sort,page,cond);
        dicCoverList(null,"dic@SF$is_lock","dic@YHLX$user_type","date@creat_time$");
        result.setData(dataList);
        result.setPagination(page);
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
    public PageResult<SysUser> delete(Model model, @PathVariable("id")Object id, PageResult<SysUser> result) {
        baseService.deleteById(id);
        sysRoleUserService.deleteByUserId(Parse.toLong(id));
        result.setFlag(true);
        return result;
    }

    /**
     * 重置密码
     * @param id
     * @param result
     * @return
     */
    @RequestMapping(value = "resetpassword/{id}")
    @ResponseBody
    public PageResult<SysUser> resetpassword(@PathVariable("id")Object id, PageResult<SysUser> result) {
        SysUser entity =  baseService.findById(id);
        entity.setPassword(oneBeanPasswordEncoder.encode("123456"));
        baseService.update(entity);
        result.setFlag(true);
        return result;
    }

    /**
     * 账户设置
     * @param model
     * @return
     */
    @RequestMapping(value = "setting")
    public String setting(HttpServletRequest request,Model model) {
        SysUser entity = SpringSecurityUtil.getCurrentLoginUser(request);
        model.addAttribute("entity",entity);
        model.addAttribute("edit",true);
        return getView("setting");
    }

    /**
     * 设置密码
     * @param model
     * @return
     */
    @RequestMapping(value = "setpassword")
    public String setpassword(HttpServletRequest request,Model model) {
        model.addAttribute("entity",SpringSecurityUtil.getCurrentLoginUser(request));
        return getView("setpassword");
    }



    /**
     * 保存
     * @param model
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public PageResult<SysUser> save(Model model, SysUser entity, PageResult<SysUser> result) {
        if (entity.getPassword().length() != 80){//页面限制用户密码长度为30,加密过得密码长度为80 未加密就加个密吧!
            entity.setPassword(oneBeanPasswordEncoder.encode(entity.getPassword()));
        }
        if (entity.getId() != null) {
            baseService.update(entity);
        } else {
            entity.setPassword(oneBeanPasswordEncoder.encode(entity.getPassword()));
            baseService.save(entity);
        }
        result.getData().add(entity);
        return result;
    }

}
