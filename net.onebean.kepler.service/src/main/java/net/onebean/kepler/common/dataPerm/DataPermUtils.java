package net.onebean.kepler.common.dataPerm;

import net.onebean.kepler.model.SysOrganization;
import net.onebean.kepler.model.SysRole;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.service.SysOrganizationService;
import net.onebean.kepler.service.SysRoleService;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据权限 工具类 用于生成数据权限sql
 * 该类设计参考了 jeeplus 框架数据权限 感谢jeeplus作者
 * @author 0neBean
 */
@Service
public class DataPermUtils {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysOrganizationService sysOrganizationService;


    private static final String DATA_PERM_LEVEL_ALL = "0";
    private static final String DATA_PERM_LEVEL_CUSTOM = "1";
    private static final String DATA_PERM_LEVEL_ORG = "2";
    private static final String DATA_PERM_LEVEL_ORG_AND_CHILD = "3";
    private Map<String,Object> dataPermMap;


    /**
     * 生成数据权限sql
     * @param user 传入当前登录用户 用于获取用户所属机构及用户关联角色
     * @param orgAlias 自定义sql中 机构表的别名 可以有多个别名 用','分割
     * @param userAlias 自定义sql中 用户表的别名 可以有多个别名 用','分割
     * @return 返回的结果中 hasDatePerm 标识sql是否为空,sql字段为数据权限拼接的sql
     */
    public Map<String,Object> dataPermFilter(SysUser user, String orgAlias, String userAlias){
        StringBuffer sqlString = new StringBuffer();
        List<SysRole> list = sysRoleService.findRolesByUserId(user.getId());
        SysOrganization sysOrganization = sysOrganizationService.findByUserId(user.getId()).get(0);//目前程序设计一个用户归属一个组织
        Boolean allDataPerm = false;
        for (SysRole sysRole : list) {
            for (String org_a : orgAlias.split(",")) {
                switch(sysRole.getData_permission_level())
                {
                    case DATA_PERM_LEVEL_ALL:
                        //所有数据
                        allDataPerm = true;
                        break;
                    case DATA_PERM_LEVEL_CUSTOM:
                        //个人数据
                        if (StringUtils.isNotEmpty(userAlias)){
                            for (String user_a : userAlias.split(",")){
                                sqlString.append(" OR " + user_a + ".id = '" + user.getId() + "'");
                            }
                        }
                        break;
                    case DATA_PERM_LEVEL_ORG:
                        //组织数据
                        sqlString.append(" OR " + org_a + ".id = '" + sysOrganization.getId() + "'");
                        break;
                    case DATA_PERM_LEVEL_ORG_AND_CHILD:
                        //组织及组织下级
                        sqlString.append(" OR " + org_a + ".id = '" + sysOrganization.getId() + "'");
                        sqlString.append(" OR " + org_a + ".parent_ids LIKE '" + sysOrganization.getParent_ids()+",%'");
                        break;
                    default:
                        break;
                }
            }
        }
        dataPermMap = new HashMap<>();
        dataPermMap.put("hasDatePerm",!allDataPerm);
        if ((Boolean) dataPermMap.get("hasDatePerm")){
            dataPermMap.put("sql"," AND (" + sqlString.substring(4) + ")");
        }
        return  dataPermMap;
    }

}
