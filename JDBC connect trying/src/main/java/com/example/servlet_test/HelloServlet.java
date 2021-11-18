package com.example.servlet_test;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void init(){

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/MySQL?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String USER = "root";
        final String PASS = "abc0108...";
        // printdatebase
        Connection conn = null;Statement stat = null;
        PrintWriter out = response.getWriter();
        try {
            Class.forName(JDBC_DRIVER);
            out.write("<h3>连接数据库中...</h3><br>");
            //初始化数据库驱动，键入用户名与密码
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            out.write("<h3>实例化Statement对象中...</h3><br>");
            stat = conn.createStatement();
            String sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                out.write("ID："+id+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                out.write("网站名称："+name+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                out.write("网站地址：<a href="+url+">"+url+"</a><br>");
            }
            rs.close();stat.close();conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stat!=null) stat.close();
            }catch(SQLException se2){}
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        out.write("<h2>数据读取完毕！</h2>");
    }
    public void destroy() {
    }
}