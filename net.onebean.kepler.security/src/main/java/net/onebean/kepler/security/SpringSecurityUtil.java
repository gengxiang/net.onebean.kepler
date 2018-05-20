package net.onebean.kepler.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.onebean.component.SpringUtil;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.kepler.model.SysUser;
import net.onebean.kepler.service.SysUserService;
import net.onebean.kepler.service.impl.SysUserServiceImpl;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 0neBean
 * spring security工具类
 */
public class SpringSecurityUtil {


    /**
     * 获取当前登录用户
     * 此方法不抛出任何异常,获取不到对应用户时返回null
     * @param request
     * @return
     */
    public static SysUser getCurrentLoginUser(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
            String username = ((UserDetails)securityContext.getAuthentication().getPrincipal()).getUsername();
            Condition condition = Condition.parseCondition("username@string@eq");
            condition.setValue(username);
            SysUserService sysUserService = SpringUtil.getBean(SysUserServiceImpl.class);
            return sysUserService.find(new Pagination(),condition).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public static JSONArray getCurrentPermissions(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(securityContext.getAuthentication().getAuthorities()));
        return  array;
    }

    /**
     * 生成指定长度秘钥
     * @param length
     * @return
     */
    public static String generateRandomSecret(Integer length){
        StringBuffer secret = new StringBuffer();
        int min =33;
        int max =126;
        char start;
        String test = "[A-Za-z0-9\\-]*";
        Matcher m;
        while (secret.length() != length){
            start = (char) getRandom(max, min);
            m = Pattern.compile(test).matcher(start + "");
            if (m.find() && (start != 34 && start != 92)) {
                secret.append(start);
            }
        }
        return  secret.toString();
    }


    /**
     * 生成随机数
     * @param max
     * @param min
     * @return
     */
    protected static int getRandom(int max,int min) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }


}
