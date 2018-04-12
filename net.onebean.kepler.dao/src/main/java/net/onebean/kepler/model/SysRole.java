package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import java.sql.Timestamp;

@TableName("sys_role")
public class SysRole extends BaseModel{

	private String name;
	@FiledName("name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		 this.name = name;
	}


	private String ch_name;
	private String is_delete;
	private String is_lock;
	private String remark;
	private Timestamp creat_time;
	private String creat_time_str;


	@IgnoreColumn
	public String getCreat_time_str() {
		return creat_time_str;
	}

	public void setCreat_time_str(String creat_time_str) {
		this.creat_time_str = creat_time_str;
	}


	@FiledName("ch_name")
	public String getCh_name() {
		return ch_name;
	}

	public void setCh_name(String ch_name) {
		this.ch_name = ch_name;
	}
	@FiledName("is_delete")
	public String getIs_delete() {
		is_delete = (null == is_delete)?"0":is_delete;
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	@FiledName("is_lock")
	public String getIs_lock() {
		is_lock = (null == is_lock)?"0":is_lock;
		return is_lock;
	}

	public void setIs_lock(String is_lock) {
		this.is_lock = is_lock;
	}

	@FiledName("remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@FiledName("creat_time")
	public Timestamp getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Timestamp creat_time) {
		this.creat_time = creat_time;
	}

	private Long ruid;
	@IgnoreColumn
	public Long getRuid() {
		return ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}


	private String data_permission_level;

	@FiledName("data_permission_level")
	public String getData_permission_level() {
		return data_permission_level;
	}

	public void setData_permission_level(String data_permission_level) {
		this.data_permission_level = data_permission_level;
	}
}