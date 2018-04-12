package net.onebean.kepler.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.onebean.core.*;
import net.onebean.core.extend.Sort;
import net.onebean.core.model.BaseIncrementIdModel;
import net.onebean.kepler.common.dictionary.DictionaryUtils;
import net.onebean.util.CollectionUtil;
import net.onebean.util.DateUtils;
import net.onebean.util.ReflectionUtils;
import net.onebean.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

public abstract  class BaseController <M extends  BaseIncrementIdModel,S extends IBaseBiz<M>> {
    /**
     *
     */
    protected List<M> dataList;
    /**
     * dao原型属性
     */
    protected S baseService;

    /**
     * 根据K泛型自动装载BaseDao
     *
     * @param baseService
     */
    @Autowired
    public final void setBaseDao(S baseService) {
        this.baseService = baseService;
    }

    /**
     * 预览列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "preview")
    @Description(value = "预览列表页面")
    public String preview(Model model) {
        return getView("list");
    }


    /**
     * 新增页面
     * @param model
     * @return
     */
    @RequestMapping(value = "add")
    @Description(value = "新增页面")
    public String add(Model model,M entity) {
        model.addAttribute("add",true);
        model.addAttribute("entity",entity);
        return getView("detail");
    }

    /**
     * 查看页面
     * @param model
     * @return
     */
    @RequestMapping(value = "view/{id}")
    @Description(value = "查看页面")
    public String view(Model model,@PathVariable("id")Object id){
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("view",true);
        return getView("detail");
    }

    /**
     * 编辑页面
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}")
    @Description(value = "编辑页面")
    public String edit(Model model,@PathVariable("id")Object id) {
        model.addAttribute("entity",baseService.findById(id));
        model.addAttribute("edit",true);
        return getView("detail");
    }

    /**
     * 保存
     * @param model
     * @return
     */
    @RequestMapping(value = "save")
    @Description(value = "保存")
    @ResponseBody
    public PageResult<M> save(Model model, M entity, PageResult<M> result) {
        if (entity.getId() != null) {
            baseService.update(entity);
        } else {
            baseService.save(entity);
        }
        result.getData().add(entity);
        return result;
    }

    /**
     * 删除
     * @param model
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    @Description(value = "删除")
    @ResponseBody
    public PageResult<M> delete(Model model,@PathVariable("id")Object id,PageResult<M> result) {
         baseService.deleteById(id);
        result.setFlag(true);
        return result;
    }


    @RequestMapping("list")
    @ResponseBody
   public PageResult<M> list (Sort sort, Pagination page,PageResult<M> result,@RequestParam(value = "conditionList",required = false) String conditionStr){
        initData(sort,page,conditionStr);
        result.setData(dataList);
        result.setPagination(page);
        return result;
    }


    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @param suffixName
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected String getView(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }

        return getViewPrefix() + suffixName;
    }

    /**
     * 获取完整
     * @return
     */
    protected String getViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            Class<M> entityClass = ReflectionUtils.findParameterizedType(getClass(), 2);
            currentViewPrefix = entityClass.getSimpleName().toLowerCase();
        }

        return currentViewPrefix;
    }


    /**
     * 给数据list包装字典
     * @param args
     * @see SF$is_lock 参数前半段为字典的code后半段是字段名用$隔开
     */
    protected void dicCoverList(List<M> dataListParam,String...args){
        try {
            if(CollectionUtil.isNotEmpty(dataListParam)){
                dataList = dataListParam;
            }
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(dataList));
            array.stream().map(o -> {
                JSONObject temp = (JSONObject)o;
                for (String s : args) {
                    String type = s.substring(0,s.indexOf("@"));
                    String filed = s.substring(s.indexOf("@")+1,s.indexOf("$"));
                    String code = s.substring(s.indexOf("$")+1,s.length());
                    switch(type)
                    {
                        case "date":
                            if (net.onebean.util.StringUtils.isNotEmpty(code)) {
                                temp.put(filed+"_str", DateUtils.format(temp.getDate(filed),code));
                            } else {
                                temp.put(filed+"_str", DateUtils.format(temp.getDate(filed),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
                            }
                            break;
                        case "dic":

                            temp.put(code, DictionaryUtils.dic(filed,temp.getString(code)));
                            break;
                        default:
                            break;
                    }
                }
                return o;
            }).collect(Collectors.toList());
            Class clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            dataList = JSON.parseArray(JSON.toJSONString(array), ReflectionUtils.findParameterizedType(clazz,0));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给数据tree包装字典
     * @param childListKey
     * @param dataListParam
     * @param args
     * @return
     */
    protected List<M> dicCoverTree(String childListKey,List<M> dataListParam,String...args){
        try {
            Class clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(dataListParam));
            JSONArray resultJsonArray = new JSONArray();
            JSONObject tempJsonObject;
            for (String s : args) {
                String type = s.substring(0,s.indexOf("@"));
                String filed = s.substring(s.indexOf("@")+1,s.indexOf("$"));
                String code = s.substring(s.indexOf("$")+1,s.length());
                for (int i = 0; i < jsonArray.size(); i++) {
                    tempJsonObject = jsonArray.getJSONObject(i);
                    switch(type)
                    {
                        case "date":
                            if (StringUtils.isNotEmpty(code)) {
                                tempJsonObject.put(filed+"_str", DateUtils.format(tempJsonObject.getDate(filed),code));
                            } else {
                                tempJsonObject.put(filed+"_str", DateUtils.format(tempJsonObject.getDate(filed),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
                            }
                            break;
                        case "dic":

                            tempJsonObject.put(code, DictionaryUtils.dic(filed,tempJsonObject.getString(code)));
                            break;
                        default:
                            break;
                    }
                    if(CollectionUtil.isNotEmpty(tempJsonObject.getJSONArray(childListKey))){
                        JSONArray childList = tempJsonObject.getJSONArray(childListKey);
                        List<M> tempList = JSON.parseArray(JSON.toJSONString(childList), ReflectionUtils.findParameterizedType(clazz,0));
                        tempJsonObject.put(childListKey,JSONArray.parseArray(JSON.toJSONString(dicCoverTree(childListKey,tempList,args))));
                    }
                    resultJsonArray.add(tempJsonObject);
                }

            }

            dataListParam = JSON.parseArray(JSON.toJSONString(resultJsonArray), ReflectionUtils.findParameterizedType(clazz,0));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataListParam;
    }


    /**
     * 装载数据
     * @param sort
     * @param page
     * @param conditionStr
     */
    protected void initData(Sort sort, Pagination page, String conditionStr){
        ListPageQuery query = new ListPageQuery();
        ConditionMap map = new ConditionMap();
        map.parseCondition(conditionStr);
        query.setConditions(map);
        query.setPagination(page);
        query.setSort(sort);
        dataList = baseService.find(query);
    }

    /**
     * 将前台的查询参数通过反射获取class 反序列化成M model
     * @param conditionStr
     * @return
     */
    protected M reflectionModelFormConditionMapStr(String conditionStr){
        try {
            ConditionMap map = new ConditionMap();
            map.parseCondition(conditionStr);
            Class clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            JSONObject jsonObject = new JSONObject();
            for (String key : map.keySet()) {
                String field = key.substring(0,key.indexOf("@"));
                String value = key.substring(key.indexOf("$")+1,key.length());
                if (StringUtils.isNotEmpty(value)){
                    jsonObject.put(field,value);
                }
            }
            return  JSON.parseObject(jsonObject.toJSONString(),ReflectionUtils.findParameterizedType(clazz,0));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
