package com.ddys.gemsell.config;

import com.ddys.gemsell.common.Interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域
 */
@Configuration
public class GlobalCrosConfig {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOriginPatterns("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("*")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Header1", "Header2");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authenticationInterceptor)
                        .addPathPatterns("/**")
                        .excludePathPatterns("/user/login")
                        .excludePathPatterns("/type/getListByCondition")
                        .excludePathPatterns("/goods/getListByCondition")
                        .excludePathPatterns("/imgs/**")
                        .excludePathPatterns("/user/register");
            }

            //添加访问后端静态资源
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry){
                 registry.addResourceHandler("/imgs/**").addResourceLocations("classpath:/imgs/");
            }

        };
    }
}
