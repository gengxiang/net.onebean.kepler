package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import java.sql.Timestamp;
import java.util.List;

@TableName("sys_organization")
public class SysOrganization extends BaseModel{

	private Long parent_id;
	@FiledName("parent_id")
	public Long getParent_id(){
		return this.parent_id;
	}
	public void setParent_id(Long parent_id){
		 this.parent_id = parent_id;
	}

	private String is_delete;
	@FiledName("is_delete")
	public String getIs_delete() {
		is_delete = (null == is_delete)?"0":is_delete;
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	private String is_root;
	@FiledName("is_root")
	public String getIs_root() {
		is_root = (null == is_root)?"0":is_root;
		return is_root;
	}
	public void setIs_root(String is_root) {
		this.is_root = is_root;
	}

	private String org_name;
	@FiledName("org_name")
	public String getOrg_name(){
		return this.org_name;
	}
	public void setOrg_name(String org_name){
		 this.org_name = org_name;
	}

	private String remark;
	@FiledName("remark")
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		 this.remark = remark;
	}

	private Timestamp creat_time;
	private String creat_time_str;

	@FiledName("creat_time")
	public Timestamp getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Timestamp creat_time) {
		this.creat_time = creat_time;
	}

	private Integer sort;
	@FiledName("sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@IgnoreColumn
	public String getCreat_time_str() {
		return creat_time_str;
	}

	public void setCreat_time_str(String creat_time_str) {
		this.creat_time_str = creat_time_str;
	}


	private Boolean hasChild;
	@IgnoreColumn
	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	private List<SysOrganization> childList;
	@IgnoreColumn
	public List<SysOrganization> getChildList() {
		return childList;
	}

	public void setChildList(List<SysOrganization> childList) {
		this.childList = childList;
	}


	private String parent_ids;
	@FiledName("parent_ids")
	public String getParent_ids() {
		return parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}
}