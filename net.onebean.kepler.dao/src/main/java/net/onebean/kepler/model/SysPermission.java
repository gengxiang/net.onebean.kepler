package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import java.util.List;

@TableName("sys_permission")
public class SysPermission extends BaseModel{

	private String name;
	@FiledName("name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		 this.name = name;
	}

	private String descritpion;
	@FiledName("descritpion")
	public String getDescritpion(){
		return this.descritpion;
	}
	public void setDescritpion(String descritpion){
		 this.descritpion = descritpion;
	}

	private String url;
	@FiledName("url")
	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		 this.url = url;
	}

	private Long parent_id;
	@FiledName("parent_id")
	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	private Integer sort;
	private String remark;
	private String menu_type;
	@FiledName("sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@FiledName("remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@FiledName("menu_type")
	public String getMenu_type() {
		return menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
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

	private Boolean hasChild;
	@IgnoreColumn
	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	private List<SysPermission> childList;
	@IgnoreColumn
	public List<SysPermission> getChildList() {
		return childList;
	}

	public void setChildList(List<SysPermission> childList) {
		this.childList = childList;
	}

	private String icon;
	@FiledName("icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}