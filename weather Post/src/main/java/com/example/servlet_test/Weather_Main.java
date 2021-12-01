package com.example.servlet_test;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.alibaba.fastjson.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet", value = "/Detail")
public class Weather_Main extends HttpServlet {
    public void init(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String defaultUrl = "https://route.showapi.com/9-2?showapi_appid=832113&showapi_sign=0fb0b0771cdf49c89e11c1222dd709ab";
        //默认获取天气信息的URL（如过期可修改）
        request.setCharacterEncoding("utf-8");
        response.setHeader("content-Type", "text/html;charset=utf-8");
        PrintWriter out  = response.getWriter();
        //设置天气详情网页的默认解析编码与实例化PrintWriter对象
        String     city  = request.getParameter("city_name");
        String need3Hour = request.getParameter("3hour");
        String[]   Days  = request.getParameterValues("days");
        //URL自带参数的获取
        defaultUrl = defaultUrl.concat("&needMoreDay=1&need3HourForcast=1");
        defaultUrl = defaultUrl.concat("&area="+city);
        defaultUrl = new String(defaultUrl.getBytes(StandardCharsets.UTF_8),"GBK");//统一URL编码格式
        //默认Url的拼接
        try {
            URL cityAddress = new URL(defaultUrl);
            URLConnection weatherConn = cityAddress.openConnection();
            BufferedReader cityRead = new BufferedReader(new InputStreamReader(weatherConn.getInputStream(),StandardCharsets.UTF_8));
            StringBuilder cityMes = new StringBuilder();
            cityMes.append('[');//实现初始JSONArray的"["拼装头
            String temp;
            while((temp = cityRead.readLine())!=null){
                cityMes.append(temp);
            }//读取并拼接Json数据
            cityMes.append(']');//实现初始JSONArray的"]"拼装尾
            JSONArray cityInf = JSONArray.parseArray(cityMes.toString());
            JSONObject objectMain = cityInf.getJSONObject(0);
            objectMain = objectMain.getJSONObject("showapi_res_body");
            objectMain.getJSONObject("remark");//若查询到则说明城市不存在，转至JSONException异常处理代码段
            Weather_Detail DetailPrint = new Weather_Detail();
            DetailPrint.Weather_Print(out,city,request,objectMain,Days,need3Hour);
            //读取JSON数据
        }catch (MalformedURLException me){
            out.write("<head><title>查询异常</title></head>");
            out.write("<h1 style=\"color:#ff0000;text-align:center;\">天气接口已失效或过期，请联系管理员！</h1>");
        }
        catch(JSONException JE) {
            out.write("<head><title>查询异常</title></head>");
            out.write("<h1 style=\"color:#ff0000;text-align:center;\">找不到该城市！</h1>");
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException {doGet(request,response);}
    public void destroy(){}
}