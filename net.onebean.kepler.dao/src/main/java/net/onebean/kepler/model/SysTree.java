package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import java.util.List;

@TableName("sys_tree")
public class SysTree extends BaseModel{

	private String name;
	@FiledName("name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		 this.name = name;
	}

	private Integer parentid;
	@FiledName("parentid")
	public Integer getParentid(){
		return this.parentid;
	}
	public void setParentid(Integer parentid){
		 this.parentid = parentid;
	}

	private List<SysTree> childList;

	public List<SysTree> getChildList() {
		return childList;
	}

	public void setChildList(List<SysTree> childList) {
		this.childList = childList;
	}
}