package com.example.pan.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * <p>
 *  shiro配置类
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
@Configuration
public class ShiroConfig {
    private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    //不加这个注解不生效 具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAPP = new DefaultAdvisorAutoProxyCreator();
        defaultAPP.setProxyTargetClass(true);
        return defaultAPP;
    }

    //把自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm() {
        return new CustomRealm();
    }

    //权限管理 配置主要是realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager(CustomRealm myShiroRealm) {
        log.info("SecurityManager注册完成");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }

    //Filter工厂 设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        log.info("设置对应的过滤条件和跳转跳转条件");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();  //hashmap是无序的 LinkedHashMap是有序的map
        //配置不会被拦截的链接顺序判断 因为前端模板采用了thymeleaf,这里不能直接使用("/static/**","anon")来配置匿名访问,必须配置到每个静态目录
        map.put("/css/**", "anon"); //匿名访问  anon:无需认证
        map.put("/fonts/**", "anon");
        map.put("/img/**", "anon");
        map.put("/js/**", "anon");
        map.put("/html/**", "anon");
        //配置退出  过滤器shiro 其中具体的退出代码shiro已经替我们实现
        map.put("/logout", "logout");
        /*过滤链定义 从上向下顺序执行,一般将/**放在最为下边--> 这是一个坑 一不小心代码就不好使了
        * authc:所有URL都必须认证通过才可以访问 anon: 所有URL都可以匿名访问
         */
        map.put("/**", "anon");
        //如果不设置默认会自动寻找web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持
     * 使用代理方式 所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
