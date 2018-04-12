package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

@TableName("sys_role_user")
public class SysRoleUser extends BaseModel{

	public SysRoleUser(Long sys_user_id, Long sys_role_id) {
		this.sys_user_id = sys_user_id;
		this.sys_role_id = sys_role_id;
	}

	private Long sys_user_id;
	@FiledName("sys_user_id")
	public Long getSys_user_id(){
		return this.sys_user_id;
	}
	public void setSys_user_id(Long sys_user_id){
		 this.sys_user_id = sys_user_id;
	}

	private Long sys_role_id;
	@FiledName("sys_role_id")
	public Long getSys_role_id(){
		return this.sys_role_id;
	}
	public void setSys_role_id(Long sys_role_id){
		 this.sys_role_id = sys_role_id;
	}
}