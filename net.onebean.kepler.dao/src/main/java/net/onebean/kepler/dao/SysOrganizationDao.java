package net.onebean.kepler.dao;
import net.onebean.core.BaseDao;
import net.onebean.kepler.model.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOrganizationDao extends BaseDao<SysOrganization> {

    /**
     * 异步查找子节点,每次查找一级
     * @param parent_id
     * @return
     */
    List<SysOrganization> findChildAsync(@Param("parent_id") Long parent_id);

    /**
     * 查找所有子节点
     * @param parent_id
     * @return
     */
    List<SysOrganization> findChildSync(@Param("parent_id") Long parent_id);

    /**
     * 获取所有父ID
     * @param childId
     * @return
     */
    String getParentOrgIds(@Param("childId")Long childId);

    /**
     * 根据userid 查找所有机构
     * @param userId
     * @return
     */
    List<SysOrganization> findByUserId(@Param("userId")Long userId);

}