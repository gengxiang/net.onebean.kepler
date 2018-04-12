package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

@TableName("sys_permission_role")
public class SysPermissionRole extends BaseModel{

	private Long role_id;
	@FiledName("role_id")
	public Long getRole_id(){
		return this.role_id;
	}
	public void setRole_id(Long role_id){
		 this.role_id = role_id;
	}

	private Long permission_id;
	@FiledName("permission_id")
	public Long getPermission_id(){
		return this.permission_id;
	}
	public void setPermission_id(Long permission_id){
		 this.permission_id = permission_id;
	}
}