package net.onebean.kepler.service;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.VO.OrgTree;
import net.onebean.kepler.model.SysOrganization;

import java.util.List;

public interface SysOrganizationService extends IBaseBiz<SysOrganization> {
    /**
     * 查找所有子节点
     * @author 0neBean
     * @param parent_id
     * @return
     */
    List<SysOrganization> findChildSync(Long parent_id);

    /**a
     * 异步查找子节点,每次查找一级
     * @author 0neBean
     * @param parent_id
     * @return
     */
    List<OrgTree> findChildAsync(Long parent_id,Long self_id);

    /**
     * 包装方法,将机构包装成treeList
     * @param before
     * @param self_id
     * @return
     */
    List<OrgTree> organizationToOrgTree(List<SysOrganization> before,Long self_id);

    /**
     * 根据userid 查找所有机构
     * @param userId
     * @return
     */
    List<SysOrganization> findByUserId(Long userId);

    /**
     * 根据id删除自身以及自项
     * @param id
     */
    void deleteSelfAndChildById(Long id);
}