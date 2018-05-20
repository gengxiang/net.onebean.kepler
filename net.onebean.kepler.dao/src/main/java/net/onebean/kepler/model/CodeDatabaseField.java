package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TableName("code_database_field")
public class CodeDatabaseField extends BaseModel{

	private Long table_id;
	@FiledName("table_id")
	public Long getTable_id(){
		return this.table_id;
	}
	public void setTable_id(Long table_id){
		 this.table_id = table_id;
	}

	private String column_name;
	@FiledName("column_name")
	public String getColumn_name(){
		return this.column_name;
	}
	public void setColumn_name(String column_name){
		 this.column_name = column_name;
	}

	private String database_type;
	@FiledName("database_type")
	public String getDatabase_type(){
		return this.database_type;
	}
	public void setDatabase_type(String database_type){
		 this.database_type = database_type;
	}

	private String annotation;
	@FiledName("annotation")
	public String getAnnotation(){
		return this.annotation;
	}
	public void setAnnotation(String annotation){
		 this.annotation = annotation;
	}

	private Integer sort;
	@FiledName("sort")
	public Integer getSort(){
		return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort = sort;
	}

	private String page_type;
	@FiledName("page_type")
	public String getPage_type(){
		return this.page_type;
	}
	public void setPage_type(String page_type){
		 this.page_type = page_type;
	}

	private String page_validate;
	@FiledName("page_validate")
	public String getPage_validate(){
		return this.page_validate;
	}
	public void setPage_validate(String page_validate){
		 this.page_validate = page_validate;
	}

	private Timestamp create_time;
	@FiledName("create_time")
	public Timestamp getCreate_time(){
		return this.create_time;
	}
	public void setCreate_time(Timestamp create_time){
		 this.create_time = create_time;
	}

	private String method_name;
	@IgnoreColumn
	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String[] validateArr;
	@IgnoreColumn
	public String[] getValidateArr() {
		return validateArr;
	}

	public void setValidateArr(String[] validateArr) {
		this.validateArr = validateArr;
	}


	private String is_query_param;

	private String query_operator;
	@FiledName("is_query_param")
	public String getIs_query_param() {
		return is_query_param;
	}

	public void setIs_query_param(String is_query_param) {
		this.is_query_param = is_query_param;
	}
	@FiledName("query_operator")
	public String getQuery_operator() {
		return query_operator;
	}

	public void setQuery_operator(String query_operator) {
		this.query_operator = query_operator;
	}

	private String is_dic_query_param;
	@IgnoreColumn
	public String getIs_dic_query_param() {
		return is_dic_query_param;
	}

	public void setIs_dic_query_param(String is_dic_query_param) {
		this.is_dic_query_param = is_dic_query_param;
	}

}