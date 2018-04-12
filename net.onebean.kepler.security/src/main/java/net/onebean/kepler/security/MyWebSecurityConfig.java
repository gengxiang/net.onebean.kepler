package net.onebean.kepler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * spring security
 * web安全配置器,boot版本基本配置
 * @author 0neBean
 */
@EnableWebSecurity
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    UserDetailsService customUserService;
    @Autowired
    MyPermissionEvaluator permissionEvaluator;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService); //user Details Service验证

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        web.expressionHandler(handler);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        myAuthenticationFailureHandler.setDefaultFailureUrl("/login");

        String[] unSecuredUrls = { "/system_assets/**", "/assets/**"};
        http.authorizeRequests()
                .antMatchers(unSecuredUrls).permitAll()
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/center")
                .failureHandler(myAuthenticationFailureHandler)
                .permitAll() //登录页面用户任意访问
                .and()
                .headers().frameOptions().sameOrigin()//允许iframe嵌套本应用页面
                .and().rememberMe().and()
                .logout().permitAll(); //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class).csrf().disable();
    }




}

