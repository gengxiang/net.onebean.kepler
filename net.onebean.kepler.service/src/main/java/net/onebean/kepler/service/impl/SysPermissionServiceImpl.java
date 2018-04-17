package net.onebean.kepler.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.form.Parse;
import net.onebean.kepler.VO.MenuTree;
import net.onebean.kepler.model.SysPermissionRole;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.service.SysPermissionRoleService;
import net.onebean.kepler.service.SysUserService;
import net.onebean.util.CollectionUtil;
import net.onebean.kepler.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.SysPermission;
import net.onebean.kepler.dao.SysPermissionDao;


@Service
public class SysPermissionServiceImpl extends BaseBiz<SysPermission, SysPermissionDao> implements SysPermissionService {

	@Autowired
	private SysPermissionRoleService sysPermissionRoleService;
	@Autowired
	private SysUserService sysUserService;

	public List<SysPermission> springSecurityFindByAdminUserId(Integer userId) {
		String userId_str = userId.toString();
		SysUser user = sysUserService.findById(userId_str);
		if (null != user && user.getUser_type().equals("admin")){
			return this.findAll();
		}
		return baseDao.findByUserId(userId_str);
	}


	/**
	 * 查找所有子节点
	 * @author 0neBean
	 * @param parent_id
	 * @return
	 */
	public List<SysPermission> findChildSync(Long parent_id,Boolean forMenu) {
		Map<String,Object> param = new HashMap<>();
		param.put("parent_id",parent_id);
		param.put("forMenu",forMenu);
		List<SysPermission> list;
		if(null == parent_id){
			list = new ArrayList<>();
			Condition condition = Condition.parseCondition("is_root@string@eq$");
			condition.setValue("1");
			SysPermission SysPermission = this.find(new Pagination(),condition).get(0);
			if(forMenu){
				SysPermission.setChildList(baseDao.findChildSyncForMenu(SysPermission.getId()));
			}else{
				SysPermission.setChildList(baseDao.findChildSync(SysPermission.getId()));
			}
			list.add(SysPermission);
		}else{
			if(forMenu){
				list = baseDao.findChildSyncForMenu(parent_id);
			}else{
				list = baseDao.findChildSync(parent_id);
			}

		}
		return list;
	}

	/**
	 * 异步查找子节点,每次查找一级
	 * @param parent_id
	 * @param self_id
	 * @return
	 */
	public List<MenuTree> findChildAsync(Long parent_id,Long self_id){
		List<MenuTree> res = new ArrayList<>();
		List<MenuTree> list = baseDao.findChildAsync(parent_id);
		for (MenuTree o : list) {//某些业务场景 节点不能选择自己作为父级节点,故过滤掉所有自己及以下节点
			if (null == self_id || (null != self_id && Parse.toInt(o.getId()) != self_id) || self_id == 1) {
				res.add(o);
			}
		}
		return res;
	}


	/**
	 * 包装方法,将菜单包装成treeList
	 * @param before
	 * @param self_id
	 * @return
	 */
	public List<MenuTree> permissionToMenuTree(List<SysPermission> before, Long self_id){
		List<MenuTree> treeList = new ArrayList<>();
		MenuTree temp;
		if(CollectionUtil.isNotEmpty(before)){
			for (SysPermission SysPermission: before) {
				if (null == self_id || (null != self_id && Parse.toInt(SysPermission.getId()) != self_id) || self_id == 1) {
					temp = new MenuTree();
					temp.setTitle(SysPermission.getDescritpion());
					temp.setId(SysPermission.getId());
					temp.setMenu_type(SysPermission.getMenu_type());
					temp.setName(SysPermission.getName());
					temp.setSort(SysPermission.getSort());
					temp.setUrl(SysPermission.getUrl());
					if (CollectionUtil.isNotEmpty(SysPermission.getChildList())) {
						temp.setType(MenuTree.TYPE_FOLDER);
						temp.setChildList(permissionToMenuTree(SysPermission.getChildList(),self_id));
					} else {
						temp.setType(MenuTree.TYPE_ITEM);
					}
					treeList.add(temp);
				}
			}
		}
		return  treeList;
	}

	/**
	 * 包装方法,将菜单包装成treeList
	 * @param before
	 * @param self_id
	 * @param roleId
	 * @return
	 */
	public List<MenuTree> permissionToMenuTreeForRole(List<SysPermission> before, Long self_id,Long roleId){
		List<MenuTree> treeList = new ArrayList<>();
		MenuTree temp;
		Map<String,Object> attr;
		List<SysPermissionRole> prList = sysPermissionRoleService.getRolePremissionByRoleId(roleId);
		if(CollectionUtil.isNotEmpty(before)){
			for (SysPermission SysPermission: before) {
				if (null == self_id || (null != self_id && Parse.toInt(SysPermission.getId()) != self_id) || self_id == 1) {
					temp = new MenuTree();
					attr = new HashMap<>();
					for (SysPermissionRole sysPermissionRole : prList) {
						if (sysPermissionRole.getPermission_id().equals(SysPermission.getId())){
							attr.put("classNames","selected-item");
						}
					}

					temp.setAttr(attr);
					temp.setTitle(SysPermission.getDescritpion());
					temp.setId(SysPermission.getId());
					temp.setMenu_type(SysPermission.getMenu_type());
					temp.setName(SysPermission.getName());
					temp.setSort(SysPermission.getSort());
					temp.setUrl(SysPermission.getUrl());
					if (CollectionUtil.isNotEmpty(SysPermission.getChildList())) {
						temp.setType(MenuTree.TYPE_FOLDER);
						temp.setChildList(permissionToMenuTreeForRole(SysPermission.getChildList(),self_id,roleId));
					} else {
						temp.setType(MenuTree.TYPE_ITEM);
					}
					treeList.add(temp);
				}
			}
		}
		return  treeList;
	}


	@Override
	public List<SysPermission> getCurrentLoginUserHasPermission(List<SysPermission> list, JSONArray currentPer) {
		return iterChildList(list,currentPer);
	}

	private List<SysPermission> iterChildList(List<SysPermission> list,JSONArray currentPer){
		List<SysPermission> result  = new ArrayList<>();
		for (SysPermission parent : list) {
			currentPer.stream().map(o -> {
				JSONObject temp = (JSONObject)o;
				if (parent.getName().equals(temp.getString("authority"))){
					if (CollectionUtil.isNotEmpty(parent.getChildList())){
						parent.setChildList(iterChildList(parent.getChildList(),currentPer));
					}
					result.add(parent);
				}
				return o;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public void deleteSelfAndChildById(Long id) {
		baseDao.deleteSelfAndChildById(id);
	}
}