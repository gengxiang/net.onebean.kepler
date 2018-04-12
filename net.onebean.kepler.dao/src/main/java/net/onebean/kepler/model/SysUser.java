package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;
import java.sql.Timestamp;
import java.util.List;

@TableName("sys_user")
public class SysUser extends BaseModel implements InterfaceBaseModel {

	private String username;
	@FiledName("username")
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		 this.username = username;
	}

	private String password;
	@FiledName("password")
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		 this.password = password;
	}
	
	private List<SysRole> roles;
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}


	private String icon;
	private String realname;
	private String email;
	private String number;
	private String mobile;
	private Timestamp creat_time;
	private String creat_time_str;
	private String is_lock;
	private String is_delete;
	private String user_type;

	@IgnoreColumn
	public String getCreat_time_str() {
		return creat_time_str;
	}

	public void setCreat_time_str(String creat_time_str) {
		this.creat_time_str = creat_time_str;
	}

	@FiledName("is_lock")
	public String getIs_lock() {
		is_lock = (null == is_lock)?"0":is_lock;
		return is_lock;
	}

	public void setIs_lock(String is_lock) {
		this.is_lock = is_lock;
	}
	@FiledName("is_delete")
	public String getIs_delete() {
		is_delete = (null == is_delete)?"0":is_delete;
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	@FiledName("user_type")
	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	@FiledName("icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@FiledName("realname")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	@FiledName("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@FiledName("number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	@FiledName("mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@FiledName("creat_time")
	public Timestamp getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Timestamp creat_time) {
		this.creat_time = creat_time;
	}

	private Integer org_id;
	@FiledName("org_id")
	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

}