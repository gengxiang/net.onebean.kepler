package net.onebean.kepler.service.impl;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.form.Parse;
import net.onebean.kepler.VO.OrgTree;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.SysOrganization;
import net.onebean.kepler.service.SysOrganizationService;
import net.onebean.kepler.dao.SysOrganizationDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysOrganizationServiceImpl extends BaseBiz<SysOrganization, SysOrganizationDao> implements SysOrganizationService{

    /**
     * 查找所有子节点
     * @author 0neBean
     * @param parent_id
     * @return
     */
    public List<SysOrganization> findChildSync(Long parent_id) {
        List<SysOrganization> list;
        if(null == parent_id){
            list = new ArrayList<>();
            Condition condition = Condition.parseCondition("is_root@string@eq$");
            condition.setValue("1");
            SysOrganization sysOrganization = this.find(new Pagination(),condition).get(0);
            sysOrganization.setChildList(baseDao.findChildSync(sysOrganization.getId()));
            list.add(sysOrganization);
        }else{
            list = baseDao.findChildSync(parent_id);
        }
        return list;
    }

    /**
     * 异步查找子节点,每次查找一级
     * @author 0neBean
     * @param parent_id
     * @return
     */
    @Override
    public List<OrgTree> findChildAsync(Long parent_id,Long self_id) {
        List<OrgTree> res = new ArrayList<>();
        List<OrgTree> list = baseDao.findChildAsync(parent_id);
        for (OrgTree o : list) {//某些业务场景 节点不能选择自己作为父级节点,故过滤掉所有自己及以下节点
            if (null == self_id || (null != self_id && Parse.toInt(o.getId()) != self_id) || self_id == 1) {
                res.add(o);
            }
        }
        return res;
    }


    @Override
    public List<OrgTree> organizationToOrgTree(List<SysOrganization> before,Long self_id){
        List<OrgTree> treeList = new ArrayList<>();
        OrgTree temp;
        if(CollectionUtil.isNotEmpty(before)){
            for (SysOrganization sysOrganization: before) {
                if (null == self_id || (null != self_id && Parse.toInt(sysOrganization.getId()) != self_id) || self_id == 1) {
                    temp = new OrgTree();
                    temp.setTitle(sysOrganization.getOrg_name());
                    temp.setId(sysOrganization.getId());
                    temp.setSort(sysOrganization.getSort());
                    if (CollectionUtil.isNotEmpty(sysOrganization.getChildList())) {
                        temp.setType(OrgTree.TYPE_FOLDER);
                        temp.setChildList(organizationToOrgTree(sysOrganization.getChildList(),self_id));
                    } else {
                        temp.setType(OrgTree.TYPE_ITEM);
                    }
                    treeList.add(temp);
                }
            }
        }
        return  treeList;
    }

    @Override
    public void save(SysOrganization entity) {
        super.save(entity);
        entity.setParent_ids(getParentOrgIdsNotEmpty(entity.getId()));
        super.save(entity);
    }

    @Override
    public void saveBatch(List<SysOrganization> entities) {
        super.saveBatch(entities);
        for (SysOrganization entity : entities) {
            entity.setParent_ids(getParentOrgIdsNotEmpty(entity.getId()));
        }
        super.saveBatch(entities);
    }

    @Override
    public void update(SysOrganization entity) {
        entity.setParent_ids(getParentOrgIdsNotEmpty(entity.getId()));
        super.update(entity);
    }

    @Override
    public void updateBatch(SysOrganization entity, List<Long> ids) {
        entity.setParent_ids(getParentOrgIdsNotEmpty(entity.getId()));
        super.updateBatch(entity, ids);
    }

    @Override
    public void updateBatch(List<SysOrganization> entities) {
        for (SysOrganization entity : entities) {
            entity.setParent_ids(getParentOrgIdsNotEmpty(entity.getId()));
        }
        super.updateBatch(entities);
    }

    protected String getParentOrgIdsNotEmpty(Long id){
        String res = baseDao.getParentOrgIds(id);
        if (StringUtils.isEmpty(res)){
            return  null;
        }
        return  res;
    }

    @Override
    public List<SysOrganization> findByUserId(Long userId) {
        return baseDao.findByUserId(userId);
    }

    @Override
    public void deleteSelfAndChildById(Long id) {
        baseDao.deleteSelfAndChildById(id);
    }
}