package net.onebean.kepler.dao;

import net.onebean.core.BaseDao;
import net.onebean.kepler.VO.MenuTree;
import net.onebean.kepler.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionDao extends BaseDao<SysPermission> {

    /**
     * 根据用户查找菜单
     * @param userId
     * @return
     */
    List<SysPermission> findByUserId(@Param("userId")String userId);

    /**
     * 异步查找子节点,每次查找一级
     * @param parent_id
     * @return
     */
    List<MenuTree> findChildAsync(@Param("parent_id") Long parent_id);

    /**
     * 查找所有子节点
     * @param parent_id
     * @return
     */
    List<SysPermission> findChildSync(@Param("parent_id") Long parent_id);

    /**
     * 查找所有子节点
     * @param parent_id
     * @return
     */
    List<SysPermission> findChildSyncForMenu(@Param("parent_id") Long parent_id);

    /**
     * 根据id删除自身以及自项
     * @param id
     */
    void deleteSelfAndChildById(@Param("id")Long id);

    /**
     * 根据父ID查找下一个排序值
     * @param parent_id
     * @return
     */
    Integer findChildOrderNextNum(@Param("parent_id") Long parent_id);

    /**
     * 获取所有父级ID
     * @param childId
     * @return
     */
    String getParentMenuIds(@Param("childId")Long childId);


}