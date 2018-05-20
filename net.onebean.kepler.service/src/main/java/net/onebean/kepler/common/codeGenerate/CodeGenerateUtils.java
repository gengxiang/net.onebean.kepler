package net.onebean.kepler.common.codeGenerate;


import freemarker.template.Template;
import net.onebean.kepler.model.CodeDatabaseField;
import net.onebean.kepler.model.CodeDatabaseTable;
import net.onebean.kepler.service.SysPermissionService;
import net.onebean.util.DateUtils;
import net.onebean.util.FreeMarkerTemplateUtils;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成代码 工具类
 * @author 0neBean
 */
@Service
public class CodeGenerateUtils {
    @Autowired
    private SysPermissionService sysPermissionService;

    private final String projectPath = getProjectPath();
    private final String mapperPath = PropUtil.getConfig("apache.freemarker.model.mapper.path");
    private final String daoPath = PropUtil.getConfig("apache.freemarker.dao.path");
    private final String servicePath = PropUtil.getConfig("apache.freemarker.service.path");
    private final String serviceImplPath = PropUtil.getConfig("apache.freemarker.service.impl.path");
    private final String actionPath = PropUtil.getConfig("apache.freemarker.action.path");
    private final String modelPath = PropUtil.getConfig("apache.freemarker.model.path");
    private final String pagePath = PropUtil.getConfig("apache.freemarker.page.path");

    /**
     * 初始化方法
     * @param table 数据库模型
     */
    protected void init(CodeDatabaseTable table){
        table.setTable_name_generate(StringUtils.replaceUnderLineAndUpperCase(table.getTable_name()));
        table.setMapping(StringUtils.getMappingStr(table.getTable_name_generate()));
        decorationTableField(table);
    }

    /**
     * 生成代码方法入口
     * @param table 数据库模型
     * @throws Exception 抛出所有异常前端通知用户
     */
    public void generate(CodeDatabaseTable table) throws Exception{
        try {
            init(table);
            generateModelFile(table);//生成model文件
            generateDaoFile(table);//生成Dao文件
            generateMapperFile(table);//生成Mapper文件
            generateServiceFile(table);//生成Service文件
            generateServiceImplFile(table);//生成ServiceImpl文件
            if(table.getGenerate_scope().equals("controller") || table.getGenerate_scope().equals("page")){
                generateActionFile(table);//生成action文件
            }
            if(table.getGenerate_scope().equals("page")){
                sysPermissionService.generatePermission(table);
                generateCrudDetailFile(table);//生成CRUD-Detail-html文件
                generateCrudListFile(table);//生成CRUD-List-html文件
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{

        }
    }

    /**
     * 返回项目路径
     * @return
     */
    protected static String getProjectPath(){
        String proPath = System.getProperty("user.dir");
        proPath = proPath.replaceAll("\\\\", "\\\\\\\\");
        proPath += PropUtil.getConfig("apache.freemarker.project.name");
        return proPath;
    }

    private void generateMapperFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "Mapper.xml";
        final String path = projectPath+mapperPath+table.getTable_name_generate()+suffix;
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("dao_package_name",PropUtil.getConfig("apache.freemarker.dao.packagename"));
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }


    private void generateDaoFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "Dao.java";
        final String path = projectPath+daoPath+table.getTable_name_generate()+suffix;
        final String templateName = "Dao.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("dao_package_name",PropUtil.getConfig("apache.freemarker.dao.packagename"));
        dataMap.put("model_package_name",PropUtil.getConfig("apache.freemarker.model.packagename"));
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }


    private void generateServiceFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "Service.java";
        final String path = projectPath+servicePath+table.getTable_name_generate()+suffix;
        final String templateName = "Service.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("service_package_name",PropUtil.getConfig("apache.freemarker.service.packagename"));
        dataMap.put("model_package_name",PropUtil.getConfig("apache.freemarker.model.packagename"));
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }


    private void generateServiceImplFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "ServiceImpl.java";
        final String path = projectPath+serviceImplPath+table.getTable_name_generate()+suffix;
        final String templateName = "ServiceImpl.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("dao_package_name",PropUtil.getConfig("apache.freemarker.dao.packagename"));
        dataMap.put("service_package_name",PropUtil.getConfig("apache.freemarker.service.packagename"));
        dataMap.put("model_package_name",PropUtil.getConfig("apache.freemarker.model.packagename"));
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }


    private void generateActionFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "Controller.java";
        final String path = initPagePath(projectPath+actionPath+table.getMapping())+table.getTable_name_generate()+suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("dao_package_name",PropUtil.getConfig("apache.freemarker.dao.packagename"));
        dataMap.put("service_package_name",PropUtil.getConfig("apache.freemarker.service.packagename"));
        dataMap.put("model_package_name",PropUtil.getConfig("apache.freemarker.model.packagename"));
        dataMap.put("action_package_name",PropUtil.getConfig("apache.freemarker.action.packagename"));
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        dataMap.put("mapping",table.getMapping());
        dataMap.put("prem_name",table.getPrem_name());
        dataMap.put("generate_scope",table.getGenerate_scope());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateModelFile(CodeDatabaseTable table) throws Exception{
        final String suffix = ".java";
        final String path = projectPath+modelPath+table.getTable_name_generate()+suffix;
        final String templateName = "Model.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("tablename_original",table.getTable_name());
        dataMap.put("fieldArr",table.getChildList());
        dataMap.put("author",table.getAuthor());
        dataMap.put("description",table.getDescription());
        dataMap.put("create_time",table.getCreate_time_str());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateCrudDetailFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "detail.html";
        final String path = initPagePath(projectPath+pagePath+table.getMapping())+suffix;
        final String templateName = "CRUD-Detail.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("fieldArr",table.getChildList());
        dataMap.put("description",table.getDescription());
        dataMap.put("mapping",table.getMapping());
        dataMap.put("prem_name",table.getPrem_name());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }


    private void generateCrudListFile(CodeDatabaseTable table) throws Exception{
        final String suffix = "list.html";
        final String path = initPagePath(projectPath+pagePath+table.getMapping())+suffix;
        final String templateName = "CRUD-List.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("table_name",table.getTable_name_generate());
        dataMap.put("fieldArr",table.getChildList());
        dataMap.put("description",table.getDescription());
        dataMap.put("mapping",table.getMapping());
        dataMap.put("prem_name",table.getPrem_name());
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    /**
     * 创建页面的文件夹
     * @param path 路径
     */
    protected static String initPagePath(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path+"\\";
    }

    /**
     * 包装表和字段
     * @param table 数据库模型
     * @return CodeDatabaseTable
     */
    CodeDatabaseTable decorationTableField(CodeDatabaseTable table){
        List<CodeDatabaseField> childList = table.getChildList();
        for (CodeDatabaseField c : childList) {
            c.setMethod_name(StringUtils.toUpperCaseFirstOne(c.getColumn_name()));
            if (null != c.getPage_validate()){
                c.setValidateArr(c.getPage_validate().split(","));
            }
            if (null != c.getQuery_operator()){
                c.setIs_dic_query_param((c.getQuery_operator().startsWith("dic"))?"1":"0");
            }
        }
        table.setCreate_time_str(DateUtils.getDateStrByTimestamp(table.getCreate_time()));
        table.setChildList(childList);
        return table;
    }


    /**
     * 根据ftl生成代码方法
     * @param templateName ftl模板名
     * @param file 文件
     * @param dataMap 渲染数据
     * @throws Exception 向上抛出异常
     */
    private void generateFileByTemplate(final String templateName,File file,Map<String,Object> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);
        fos.close();
        out.close();
    }


}
