package net.onebean.kepler.service;

import com.alibaba.fastjson.JSONArray;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.VO.MenuTree;
import net.onebean.kepler.model.CodeDatabaseTable;
import net.onebean.kepler.model.SysPermission;

import java.util.List;

public interface SysPermissionService extends IBaseBiz<SysPermission> {
	/**
	 * 生成权限
	 * @param table 数据库表模型
	 */
	void generatePermission(CodeDatabaseTable table);
	/**
	 * springSecurity 用于根据ID查找用户的方法
	 * @param userId 用户ID
	 * @return List<SysPermission>
	 */
 	List<SysPermission> springSecurityFindByAdminUserId(Integer userId);
	/**
	 * 查找所有子节点
	 * @author 0neBean
	 * @param parent_id 父ID
	 * @return List<SysPermission>
	 */
	List<SysPermission> findChildSync(Long parent_id,Boolean forMenu);
	/**
	 * 异步查找子节点,每次查找一级
	 * @param parent_id 父ID
	 * @param self_id 数据自己的ID
	 * @return List<MenuTree>
	 */
	List<MenuTree> findChildAsync(Long parent_id,Long self_id);
	/**
	 * 包装方法,将机构包装成treeList
	 * @param before 加工前的 List<SysPermission>
	 * @param self_id 数据自己的ID
	 * @return List<MenuTree>
	 */
	List<MenuTree> permissionToMenuTree(List<SysPermission> before, Long self_id);
	/**
	 * 包装方法,将菜单包装成treeList
	 * @param before 加工前的 List<SysPermission>
	 * @param self_id 数据自己的ID
	 * @param roleId 角色ID
	 * @return List<MenuTree>
	 */
	List<MenuTree> permissionToMenuTreeForRole(List<SysPermission> before, Long self_id,Long roleId);

	/**
	 * 匹配当前登录用户拥有的菜单权限
	 * @param list 传入匹配前的菜单列表
	 * @param currentPer 当前用户的权限列表
	 * @return List<SysPermission>
	 */
	List<SysPermission> getCurrentLoginUserHasPermission(List<SysPermission> list, JSONArray currentPer);

	/**
	 * 根据id删除自身以及自项
	 * @param id 主键
	 */
	void deleteSelfAndChildById(Long id);

	/**
	 * 根据父ID查找下一个排序值
	 * @param parent_id 父ID
	 * @return Integer
	 */
	Integer findChildOrderNextNum(Long parent_id);

}