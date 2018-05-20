package net.onebean.kepler.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;

/**
* @author ${author}
* @description ${description} model
* @date ${create_time}
*/
@TableName("${tablename_original}")
public class ${table_name} extends BaseModel implements InterfaceBaseModel {


<#if fieldArr?exists>
    <#list fieldArr as item>
        <#if item.column_name != 'id'>
        private String ${item.column_name};
        @FiledName("${item.column_name}")
        public String get${item.method_name}(){
            return this.${item.column_name};
        }
        public void set${item.method_name}(String ${item.column_name}){
            this.${item.column_name} = ${item.column_name};
        }
        </#if>
    </#list>
</#if>


}