package com.example.pan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域注册
     * @param registry 注册
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //设置允许跨域的请求的域名
                .allowedMethods("*")
//                .allowedMethods("GET","POST","PUT")
                .allowCredentials(true) //是否发生cookie
                .exposedHeaders("*");  //暴露那些头部信息
    }

    /**
     * 加载本地资源文件
     * @param registry 登记处
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:static/")
                .addResourceLocations("classpath:files/")
                .addResourceLocations("file:D:\\files\\");
    }
}
