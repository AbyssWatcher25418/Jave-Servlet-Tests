package com.example.weather_spring.Configs;

import com.example.weather_spring.Interceptors.Inter_CityName;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WeatherPostWebMVCConfig implements WebMvcConfigurer {
    //注册拦截器
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/**").setViewName("noPage");
        registry.addViewController("/WeatherReq").setViewName("index");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Inter_CityName()).addPathPatterns("/WeatherDetail");
    }
}
