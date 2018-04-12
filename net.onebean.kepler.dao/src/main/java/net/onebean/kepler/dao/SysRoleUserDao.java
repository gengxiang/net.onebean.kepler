package net.onebean.kepler.dao;
import net.onebean.core.BaseDao;
import net.onebean.kepler.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

public interface SysRoleUserDao extends BaseDao<SysRoleUser> {
    /**
     * 根据用户id删除关联数据
     * @param userId
     */
    void deleteByUserId(@Param("userId")Long userId);    /**
     * 根据角色id删除关联数据
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId")Long roleId);
}