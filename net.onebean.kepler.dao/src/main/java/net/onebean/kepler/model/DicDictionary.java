package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import java.io.Serializable;
import java.sql.Timestamp;

@TableName("dic_dictionary")
public class DicDictionary extends BaseModel{

	private String val;
	@FiledName("val")
	public String getVal(){
		return this.val;
	}
	public void setVal(String val){
		 this.val = val;
	}

	private String dic;
	@FiledName("dic")
	public String getDic(){
		return this.dic;
	}
	public void setDic(String dic){
		 this.dic = dic;
	}

	private String code;
	@FiledName("code")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		 this.code = code;
	}

	private String group_dic;
	@FiledName("group_dic")
	public String getGroup_dic(){
		return this.group_dic;
	}
	public void setGroup_dic(String group_dic){
		 this.group_dic = group_dic;
	}

	private Integer sort;
	@FiledName("sort")
	public Integer getSort(){
		return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort = sort;
	}
}