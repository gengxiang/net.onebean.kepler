package net.onebean.kepler.service;
import java.util.List;


import com.alibaba.fastjson.JSONArray;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.VO.MenuTree;
import net.onebean.kepler.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

public interface SysPermissionService extends IBaseBiz<SysPermission> {
	
 	List<SysPermission> springSecurityFindByAdminUserId(Integer userId);
	/**
	 * 查找所有子节点
	 * @author 0neBean
	 * @param parent_id
	 * @return
	 */
	List<SysPermission> findChildSync(Long parent_id,Boolean forMenu);
	/**
	 * 异步查找子节点,每次查找一级
	 * @author 0neBean
	 * @param parent_id
	 * @return
	 */
	List<SysPermission> findChildAsync(Long parent_id);
	/**
	 * 包装方法,将机构包装成treeList
	 * @param before
	 * @param self_id
	 * @return
	 */
	List<MenuTree> permissionToMenuTree(List<SysPermission> before, Long self_id);
	/**
	 * 包装方法,将菜单包装成treeList
	 * @param before
	 * @param self_id
	 * @param roleId
	 * @return
	 */
	List<MenuTree> permissionToMenuTreeForRole(List<SysPermission> before, Long self_id,Long roleId);

	/**
	 * 匹配当前登录用户拥有的菜单权限
	 * @param list 传入匹配前的菜单列表
	 * @param currentPer 当前用户的权限列表
	 * @return
	 */
	List<SysPermission> getCurrentLoginUserHasPermission(List<SysPermission> list, JSONArray currentPer);

}