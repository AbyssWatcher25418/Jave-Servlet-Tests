package com.example.servlet_test;

import javax.servlet.*;
import javax.swing.*;
import java.io.PrintWriter;

public class Filter_1 implements Filter{
    public void init(FilterConfig config) throws ServletException{}
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws java.io.IOException,ServletException{
        String cityName = request.getParameter("city_name");
        if(cityName.matches("^[\\u4e00-\\u9fa5]+$")){
            chain.doFilter(request, response);
        }
        else {
            JOptionPane.showMessageDialog(null,"查询的城市名仅能包含汉字！");
        }
    }
    public void destroy(){}
}
