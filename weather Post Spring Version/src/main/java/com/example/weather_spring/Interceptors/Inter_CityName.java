package com.example.weather_spring.Interceptors;

import com.example.weather_spring.MainApp;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)//设置该拦截器优先级为1
@Component
public class Inter_CityName implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object Handle) throws IOException{
        MainApp.addInfo("用户发送表单请求......");
        MainApp.addInfo("用户主机名1："+request.getLocalName());
        MainApp.addInfo("用户主机名2："+request.getRemoteHost());
        MainApp.addInfo("用户IP："+request.getRemoteAddr());
        MainApp.addInfo("用户Session："+request.getRequestedSessionId());
        String cityName = request.getParameter("city_name");
        if(cityName.matches("^[\\u4e00-\\u9fa5]+$")){
            MainApp.addInfo("请求通过！");
            return true;
        }
        else{
            MainApp.addInfo("请求拦截！");
            MainApp.addInfo("");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("<h1 style=\"position: absolute;left:40%;top:20%;color:#0000ff;\">城市名仅能包括汉字~，请重新输入!</h1>");
            response.setHeader("refresh","1.5;url=/WeatherReq");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object Handle, ModelAndView model) throws Exception{
        MainApp.addInfo("该用户已完成连接");
        MainApp.addInfo("");
    }
}
