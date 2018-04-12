package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import java.io.Serializable;
import java.sql.Timestamp;

@TableName("test_dp")
public class TestDp extends BaseModel{

	private Integer user_id;
	@FiledName("user_id")
	public Integer getUser_id(){
		return this.user_id;
	}
	public void setUser_id(Integer user_id){
		 this.user_id = user_id;
	}

	private Long org_id;
	@FiledName("org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	private String remark;
	@FiledName("remark")
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		 this.remark = remark;
	}
}