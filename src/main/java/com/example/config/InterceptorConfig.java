//package com.example.config;
//
//import com.example.interceptor.AuthInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.servlet.config.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// */
//@Configuration
//@CrossOrigin
//public class InterceptorConfig implements WebMvcConfigurer {
//
//
//    @Bean
//    public AuthInterceptor authInterceptor() {
//        return new AuthInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> patterns = new ArrayList();
//        patterns.add("/webjars/**");
//        patterns.add("/druid/**");
//        patterns.add("/sys/login");
//        patterns.add("/swagger/**");
//        patterns.add("/v2/api-docs");
//        patterns.add("/swagger-ui.html");
//        patterns.add("/swagger-resources/**");
//        patterns.add("/index");
//        patterns.add("/login");
//        patterns.add("**/resources/**");
//        patterns.add("/static/**");
//        patterns.add("/api");
//        patterns.add("/getJson");
//        patterns.add("/css/**");
//        patterns.add("/img/**");
//        patterns.add("/js/**");
//        patterns.add("/picture/**");
//        patterns.add("/sass/**");
//        patterns.add("/vendor/**");
//        patterns.add("/apiController/**");
//
//
//        registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
//    }
////
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/**").addResourceLocations("classpath:resources");
////
////    }
//
//
////    /**
////     *新加的
////     */
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry){
////        registry.addResourceHandler("/resource/**").addResourceLocations("file:"+System.getProperty("user.dir")+"/res/");
//////        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
////    }
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")//设置允许跨域的路径
////                .allowedOrigins("*")//设置允许跨域请求的域名
////                .allowCredentials(true)//是否允许证书 不再默认开启
////                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
////                .maxAge(3600);//跨域允许时间
////    }
//
//
//
//
//
//}
//
