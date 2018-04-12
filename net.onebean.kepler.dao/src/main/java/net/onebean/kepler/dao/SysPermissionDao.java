package net.onebean.kepler.dao;
import java.util.List;


import net.onebean.core.BaseDao;
import net.onebean.kepler.model.SysPermission;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionDao extends BaseDao<SysPermission> {
	
    public List<SysPermission> findByAdminUserId(@Param("userId")String userId);

    /**
     * 异步查找子节点,每次查找一级
     * @param parent_id
     * @return
     */
    List<SysPermission> findChildAsync(@Param("parent_id") Long parent_id);

    /**
     * 查找所有子节点
     * @param parentId
     * @param forMenu 为true时将不查出 为url的 SysPermission
     * @return
     */
    List<SysPermission> findChildSync(@Param("parent_id") Long parent_id);

    /**
     * 查找所有子节点
     * @param parentId
     * @param forMenu 为true时将不查出 为url的 SysPermission
     * @return
     */
    List<SysPermission> findChildSyncForMenu(@Param("parent_id") Long parent_id);


}