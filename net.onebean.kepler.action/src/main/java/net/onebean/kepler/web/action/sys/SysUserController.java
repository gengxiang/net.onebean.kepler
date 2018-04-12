package net.onebean.kepler.web.action.sys;


import net.onebean.core.PageResult;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.kepler.common.dataPerm.DataPermUtils;
import net.onebean.kepler.core.BaseController;
import net.onebean.kepler.model.SysUser;
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

@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController<SysUser,SysUserService> {

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

//    @RequestMapping("testDataPerm")
//    @ResponseBody
//    public Map<String,Object> testDataPerm(HttpServletRequest request){
//        SysUser user = SpringSecurityUtil.getCurrentLoginUser(request);
//        return dataPermUtils.dataPermFilter(user,"o","u");
//    }

}
