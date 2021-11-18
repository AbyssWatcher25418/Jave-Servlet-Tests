package com.example.servlet_test;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.google.gson.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")

public class HelloServlet extends HttpServlet {
    JsonObject newOb = new JsonObject();
    String UserName,UserNickname,UserSchool;
    public void init(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        newOb.addProperty("Name",request.getParameter("Name"));
        newOb.addProperty("Nickname",request.getParameter("Nickname"));
        newOb.addProperty("School",request.getParameter("School"));//对参数中用户输入的处理
        response.setContentType("text/html");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<h2>带参数一言接口测试：</h2>\n");
        out.print("<h1>"+newOb+"</h1>\n\n");
        out.print("<h3>PS:在地址栏末尾通过键入:<br>");
        out.print("\"?Name=XXX\"输入你的名字<br>");
        out.print("?Nickname=XXX\"输入你的昵称<br>\n");
        out.print("?School=XXX\"输入你的学校</h3>");
    }
    public void destroy() {
    }
}