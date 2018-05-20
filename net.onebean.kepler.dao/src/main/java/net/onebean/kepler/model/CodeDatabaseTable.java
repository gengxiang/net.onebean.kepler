package net.onebean.kepler.model;

import jdk.nashorn.internal.ir.annotations.Ignore;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.IgnoreColumn;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

@TableName("code_database_table")
public class CodeDatabaseTable extends BaseModel{

	private String table_name;
	@FiledName("table_name")
	public String getTable_name(){
		return this.table_name;
	}
	public void setTable_name(String table_name){
		 this.table_name = table_name;
	}

	private String author;
	@FiledName("author")
	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(String author){
		 this.author = author;
	}

	private String description;
	@FiledName("description")
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		 this.description = description;
	}

	private Timestamp create_time;
	@FiledName("create_time")
	public Timestamp getCreate_time(){
		return this.create_time;
	}
	public void setCreate_time(Timestamp create_time){
		 this.create_time = create_time;
	}

	private String create_time_str;
	@IgnoreColumn
	public String getCreate_time_str() {
		return create_time_str;
	}

	public void setCreate_time_str(String create_time_str) {
		this.create_time_str = create_time_str;
	}

	private String generate_scope;
	@FiledName("generate_scope")
	public String getGenerate_scope() {
		return generate_scope;
	}
	public void setGenerate_scope(String generate_scope) {
		this.generate_scope = generate_scope;
	}

	private String generate_type;
	@FiledName("generate_type")
	public String getGenerate_type() {
		return generate_type;
	}
	public void setGenerate_type(String generate_type) {
		this.generate_type = generate_type;
	}

	private List<CodeDatabaseField> childList;
	@Ignore
	public List<CodeDatabaseField> getChildList() {
		return childList;
	}

	public void setChildList(List<CodeDatabaseField> childList) {
		this.childList = childList;
	}


	private Long parent_menu_id;
	private String menu_icon;
	private String prem_name;
	@FiledName("parent_menu_id")
	public Long getParent_menu_id() {
		return parent_menu_id;
	}

	public void setParent_menu_id(Long parent_menu_id) {
		this.parent_menu_id = parent_menu_id;
	}
	@FiledName("menu_icon")
	public String getMenu_icon() {
		return menu_icon;
	}

	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}

	@FiledName("prem_name")
	public String getPrem_name() {
		return prem_name;
	}

	public void setPrem_name(String prem_name) {
		this.prem_name = prem_name;
	}

	private String mapping;

	@IgnoreColumn
	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	private String table_name_generate;

	@IgnoreColumn
	public String getTable_name_generate() {
		return table_name_generate;
	}

	public void setTable_name_generate(String table_name_generate) {
		this.table_name_generate = table_name_generate;
	}
}