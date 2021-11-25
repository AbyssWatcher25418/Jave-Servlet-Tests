package com.example.servlet_test;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.alibaba.fastjson.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet", value = "/Detail")
public class Weather_Main extends HttpServlet {
    String city;String[] Days;String need3Hour = "";PrintWriter out;JSONObject objectMain;String defaultUrl;
    HttpServletRequest requestBK;
    public void init(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setHeader("content-Type", "text/html;charset=utf-8");
        out = response.getWriter();
        requestBK = request;
        try {
            defaultUrl = "https://route.showapi.com/9-2?showapi_appid=832113&showapi_sign=0fb0b0771cdf49c89e11c1222dd709ab";
            //默认获取天气信息的URL（如过期可修改）
            defaultUrl = defaultUrl.concat("&needMoreDay=1&needAlarm=1");
            if(request.getQueryString().contains("3hour")) {
                need3Hour = request.getParameter("3hour");
                if (need3Hour.equals("yes")) {
                    defaultUrl = defaultUrl.concat("&need3HourForcast=1");
                }
            }
            Days = request.getParameterValues("days");
            city = request.getParameter("city_name");
            defaultUrl = defaultUrl.concat("&area="+city);
            defaultUrl = new String(defaultUrl.getBytes(StandardCharsets.UTF_8),"GBK");//统一URL编码格式
            URL cityAddress = new URL(defaultUrl);
            URLConnection weatherConn = cityAddress.openConnection();
            BufferedReader cityRead = new BufferedReader(new InputStreamReader(weatherConn.getInputStream(),StandardCharsets.UTF_8));
            String cityMes = "[";String temp;
            while((temp = cityRead.readLine())!=null){
                cityMes += temp;
            }
            cityMes += "]";
            JSONArray cityInf = JSONArray.parseArray(cityMes);
            objectMain = cityInf.getJSONObject(0);
            objectMain = objectMain.getJSONObject("showapi_res_body");
            try {
                objectMain.getJSONObject("remark");
                Weather_Detail();
            }catch(JSONException JE) {
                out.write("<head><title>查询异常</title></head>");
                out.write("<h1 style=\"color:#ff0000;text-align:center;\">找不到该城市！</h1>");
            }
            //读取JSON数据
        }catch (MalformedURLException me){
            out.write("<head><title>查询异常</title></head>");
            out.write("<h1 style=\"color:#ff0000;text-align:center;\">天气接口已失效或过期，请联系管理员！</h1>");
        }
    }//初步获取天气接口
    public void Weather_Detail() throws IOException{
        out.write("<head><title>天气详情</title></head>");
        out.write("<body style=\"background-image:url(https://i.loli.net/2021/11/24/HDGb3w1vNWds4uJ.jpg);" +
                "background-repeat:no-repeat;background-position-x:center\">");
        out.write("<h1 style=\"text-align:center\">"+city+"天气详情表</h1><br>");
        out.write("<h4 style=\"text-align:center\">复制该链接可分享天气详情表："+requestBK.getRequestURL()+"?"+ requestBK.getQueryString()+"</h4><br><br>");
        JSONObject cityTemp =objectMain.getJSONObject("cityInfo");
        {
            out.write("<table border cellspacing=\"0\" cellpadding=\"8\" style=\"width:100%;text-align:center;border-width:0px;border-color:#cccccc;\">" +
                    "    <tr>" +
                    "        <td style=\"background:rgba(75,159,243,0.5);\">预报发布时间</td>" +
                    "        <td colspan=\"10\" style=\"text-align:left;background:rgba(255,255,255,0.5);\">20211125120000</td>" +
                    "    </tr>");
        }//表头
        {
            out.write("<tr><td width=\"10%\" style=\"background:rgba(75,159,243,0.5);\" rowspan=\"2\">查询的地区基本资料</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市所在国家中文名</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市所在省份中文名</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市所在市中文名</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市中文名</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市级别</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">邮编</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">时区</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">城市区号</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">经度</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">纬度</td>" +
                    "</tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c9")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c7")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c5")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c3")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c10")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c12")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c17")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("c11")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("longitude")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("latitude")+"</td></tr>");
        }//地区基本资料
        {
            cityTemp = objectMain.getJSONObject("now");
            out.write("    <tr><td width=\"10%\" style=\"background:rgba(75,159,243,0.5);\" rowspan=\"2\">现在实时的天气情况</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">天气</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">空气质量指数</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">空气湿度</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">风向</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">风力</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">获得气温时间</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">气温</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">天气图标</td>" +
                    "    </tr><tr><td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("weather")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("aqi")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("sd")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("wind_direction")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("wind_power")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("temperature_time")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("temperature")+"℃</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\" colspan=\"2\"><image src=\""+cityTemp.getString("weather_pic")+"\" width=\"50\"></image></td></tr>");
        }//现在的实时天气情况
        {
            cityTemp = (objectMain.getJSONObject("now")).getJSONObject("aqiDetail");
            out.write("    <tr><td width=\"10%\" style=\"background:rgba(75,159,243,0.5);\" rowspan=\"2\">AQI明细数据</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">Co(mg/m³/1h)</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">So₂(μg/m³/1h)</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">O₃(μg/m³/1h)</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">O₃(μg/m³/8h)</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">No₂(μg/m³/1h)</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">空气质量指数</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">空气质量指数类别</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">颗粒物(r<=10μm [μg/m³/h])</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">颗粒物(r<=2.5μm [μg/m³/h])</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">首要污染物</td>" +
                    "    </tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("co")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("so2")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("o3")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("o3_8h")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("no2")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("aqi")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("quality")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("pm10")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("pm2_5")+"</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("primary_pollutant")+"</td></tr>");
        }//AQI明细数据
        for(int day = 1;day<=7;day++){
            if(Arrays.asList(Days).contains("day"+day)) {
                cityTemp = objectMain.getJSONObject("f"+day);
                String week;
                switch(day){
                    case 1:week="今天";break;
                    case 2:week="明天";break;
                    case 3:week="后天";break;
                    case 4:week="第四天";break;
                    case 5:week="第五天";break;
                    case 6:week="第六天";break;
                    case 7:week="第七天";break;
                    default:week="哇，打印这个天气表可废力气了，现在我知道前端有多辛苦了......";
                }
                int rows = 4;
                if(need3Hour.equals("yes")){
                    rows = 13;
                }
                out.write("    <tr><td width=\"10%\" style=\"background:rgba(75,159,243,0.5);\" rowspan=\""+rows+"\">"+week+"的天气预报</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">当前天</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">大气压</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">降水概率</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">日出日落时间</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">紫外线强度等级</td>" +
                        "    </tr><tr><td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("day")+"</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("air_press")+"</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("jiangshui")+"</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("sun_begin_end")+"</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("ziwaixian")+"</td>" +
                        "    </tr><tr><td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">白天天气</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">白天风力编号</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">白天风向</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">白天天气温度</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">白天天气图标</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">晚上天气</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">晚上风力编号</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">晚上风向</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">晚上天气温度</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">晚上天气图标</td>" +
                        "    </tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("day_weather")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("day_wind_power")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("day_wind_direction")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("day_air_temperature")+"℃</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\""+cityTemp.getString("day_weather_pic")+"\" width=\"30\"></image></td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("night_weather")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("night_wind_power")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("night_wind_direction")+"</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("night_air_temperature")+"℃</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\""+cityTemp.getString("night_weather_pic")+"\" width=\"30\"></image></td></tr>");
                if(rows==13){
                    JSONArray hoursD = cityTemp.getJSONArray("3hourForcast");
                    Iterator nextTemp = hoursD.iterator();
                    out.write("<tr><td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">时间范围</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">天气</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">气温</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">气温最小值</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">气温最大值</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">风力</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">风向</td>" +
                            "<td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\">沉淀物</td>" +
                            "<td width=\"10%\" colspan=\"2\" style=\"background:rgba(75,209,253,0.5);\">天气图标</td></tr>");
                    while(nextTemp.hasNext()){
                        String hourTemp = nextTemp.next().toString();
                        cityTemp = JSONObject.parseObject(hourTemp);
                        out.write("<tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("hour")+"</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("weather")+"</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("temperature")+"℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("temperature_min")+"℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("temperature_max")+"℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("wind_power")+"</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("wind_direction")+"</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">"+cityTemp.getString("precip")+"</td>" +
                                "<td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\""+cityTemp.getString("weather_pic")+"\" width=\"30\"></image></td></tr>");
                    }
                }
            }
        }//N天天气预报
        out.write("</table></body>");
    }//生成天气详情表
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException
    {doGet(request,response);}
    public void destroy(){}
}