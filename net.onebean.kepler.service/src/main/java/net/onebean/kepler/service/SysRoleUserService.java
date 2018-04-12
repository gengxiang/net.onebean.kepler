package net.onebean.kepler.service;
import net.onebean.core.IBaseBiz;
import net.onebean.kepler.model.SysRoleUser;

public interface SysRoleUserService extends IBaseBiz<SysRoleUser> {
    /**
     * 根据用户id删除关联数据
     * @param userId
     */
    void deleteByUserId(Long userId);
    /**
     * 根据角色id删除关联数据
     * @param roleId
     */
    void deleteByRoleId(Long roleId);
}