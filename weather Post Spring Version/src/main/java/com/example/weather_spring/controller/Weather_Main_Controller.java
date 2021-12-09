package com.example.weather_spring.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.weather_spring.MainApp;
import com.example.weather_spring.Weather_Post.Weather_Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

@RestController
public class Weather_Main_Controller {
    private ExecutorService executorService;

    @Autowired
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @RequestMapping(value = "/WeatherDetail",method = RequestMethod.GET)
    public void WeatherDetail(HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute("city_name")  String   city,
                              @ModelAttribute("3hour")      String   need3Hour,
                              @RequestParam(value="days")   String[] Days             )throws IOException
    {   //////////////////////获取参数列表
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //设置天气详情网页的默认解析编码与实例化PrintWriter对象
        MainApp.addInfo("正在为用户请求天气详情数据......");
        StringBuilder defaultUrl = new StringBuilder("https://route.showapi.com/9-2?showapi_appid=832113&showapi_sign=0fb0b0771cdf49c89e11c1222dd709ab" +
                "&needMoreDay=1&need3HourForcast=1&area=");
        //默认获取天气信息的URL（如过期可修改）
        defaultUrl.append(city);
        //默认Url的拼接
        try {
            URL cityAddress = new URL(defaultUrl.toString());
            URLConnection weatherConn = cityAddress.openConnection();
            BufferedReader cityRead = new BufferedReader(new InputStreamReader(weatherConn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder cityMes = new StringBuilder();
            cityMes.append('[');//实现初始JSONArray的"["拼装头
            String temp;
            while ((temp = cityRead.readLine()) != null) {
                cityMes.append(temp);
            }//读取并拼接Json数据
            cityMes.append(']');//实现初始JSONArray的"]"拼装尾
            JSONArray cityInf = JSONArray.parseArray(cityMes.toString());
            JSONObject objectMain = cityInf.getJSONObject(0);
            objectMain = objectMain.getJSONObject("showapi_res_body");
            objectMain.getJSONObject("remark");//若查询到则说明城市不存在，转至JSONException异常处理代码段
            MainApp.addInfo("该用户请求参数如下：");
            MainApp.addInfo("---> [城市名]        ："+city);
            MainApp.addInfo("---> [请求天数列表]   ："+Arrays.asList(Days));
            MainApp.addInfo("---> [是否需求天气详情]："+need3Hour);
            MainApp.addInfo("尝试为用户打印天气详情表......");//将用户请求参数加入Log记录
            String SharedURL = request.getRequestURL() + "?" + request.getQueryString();
            executorService.execute(new Weather_Detail(out, city, SharedURL, objectMain, Days, need3Hour));
            //读取JSON数据
            try{
                Thread.sleep(1000);
            }catch (InterruptedException interrupt){
                MainApp.addInfo("主线程的待机状态被意外干扰！！！");
            }//令主线程待机1秒钟，保留PrintWriter为子线程打印预留时间
        } catch (MalformedURLException me) {
            out.write("<head><title>查询异常</title></head>");
            out.write("<h1 style=\"position: absolute;left:40%;top:20%;color:#ff0000;\">天气接口异常，请联系管理员：</h1>");
            out.write("<h1 style=\"position: absolute;left:40%;top:30%;color:#0000ff;\">QQ：2478024268</h1>");
            MainApp.addInfo("！！！！！！！！！！！！！！！！！！！！");
            MainApp.addInfo("  ！！！警告！！！天气接口的调用出现异常，请立即处理  ！！！警告！！！");
            MainApp.addInfo("！！！！！！！！！！！！！！！！！！！！");
        } catch (JSONException JE) {
            out.write("<head><title>查询异常</title></head>");
            out.write("<h1 style=\"position: absolute;left:40%;top:20%;color:#ff0000;\">哪有这个城市，请重新输入!</h1>");
            MainApp.addInfo("该用户输入的城市名不存在");
            response.setHeader("refresh", "1.5;url=/WeatherReq");
        }
    }//通过调用继承Runnable接口的函数，进行异步处理，一定程度防止线程阻塞
}
