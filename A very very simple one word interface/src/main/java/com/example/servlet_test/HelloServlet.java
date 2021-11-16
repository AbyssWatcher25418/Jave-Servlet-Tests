package com.example.servlet_test;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.google.gson.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    JsonObject newOb = new JsonObject();
    public void init(){
        newOb.addProperty("Name","LWQ");
        newOb.addProperty("Nickname","卑微大一新生");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // print
        PrintWriter out = response.getWriter();
        out.print("<h1>"+newOb+"</h1>");

    }
    public void destroy() {
    }
}