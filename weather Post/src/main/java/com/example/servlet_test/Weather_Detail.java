package com.example.servlet_test;

import java.io.*;
import java.util.*;
import com.alibaba.fastjson.*;
import javax.servlet.http.HttpServletRequest;

public class Weather_Detail {
    /*参数解释：①city为从URL中获取的城市名参数；    ②requestBK继承于Http的Request请求；  ③objectMain为主函数获取的天气详情JSON对象；
              ④Days为URL中获取的天数标记参数列表（用于确定用户勾选了哪些需要显示详情的天数）；⑤need3Hour为URL中获取的，用于表示用户是否需要额外每三小时的天气详情*/
    public void Weather_Print(PrintWriter out, String city, HttpServletRequest requestBK,JSONObject objectMain,String[] Days,String need3Hour) {
        out.write("<head><title>天气详情</title></head>");
        out.write("<body style=\"background-image:url(https://i.loli.net/2021/11/24/HDGb3w1vNWds4uJ.jpg);" +
                     "background-repeat:no-repeat;background-position-x:center\">");
        out.write("<h1 style=\"text-align:center\">" + city + "天气详情表</h1><br>");
        out.write("<h4 style=\"text-align:center\">复制该链接可分享天气详情表：" + requestBK.getRequestURL() + "?" + requestBK.getQueryString() + "</h4><br><br>");
        JSONObject cityTemp = objectMain.getJSONObject("cityInfo");
        {
            out.write("   <table border cellspacing=\"0\" cellpadding=\"8\" style=\"width:100%;text-align:center;border-width:0px;border-color:#cccccc;\">" +
                    "    <tr><td style=\"background:rgba(75,159,243,0.5);\">预报发布时间</td>" +
                    "        <td colspan=\"10\" style=\"text-align:left;background:rgba(255,255,255,0.5);\">20211125120000</td></tr>");
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
                    "</tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c9") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c7") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c5") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c3") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c10") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c12") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c17") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("c11") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("longitude") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("latitude") + "</td></tr>");
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
                    "    </tr><tr><td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("weather") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("aqi") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("sd") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("wind_direction") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("wind_power") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("temperature_time") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("temperature") + "℃</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\" colspan=\"2\"><image src=\"" + cityTemp.getString("weather_pic") + "\" width=\"50\"></image></td></tr>");
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
                    "    </tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("co") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("so2") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("o3") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("o3_8h") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("no2") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("aqi") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("quality") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("pm10") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("pm2_5") + "</td>" +
                    "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("primary_pollutant") + "</td></tr>");
        }//AQI明细数据
        for (int day = 1; day <= 7; day++) {
            if (Arrays.asList(Days).contains("day" + day)) {
                cityTemp = objectMain.getJSONObject("f" + day);
                String week;
                switch (day) {
                    case 1:
                        week = "今天";
                        break;
                    case 2:
                        week = "明天";
                        break;
                    case 3:
                        week = "后天";
                        break;
                    case 4:
                        week = "第四天";
                        break;
                    case 5:
                        week = "第五天";
                        break;
                    case 6:
                        week = "第六天";
                        break;
                    case 7:
                        week = "第七天";
                        break;
                    default:
                        week = "哇，打印这个天气表可废力气了，现在我知道前端有多辛苦了......";
                }
                int rows = 4;
                if (need3Hour.equals("yes")) {
                    rows = 13;
                }
                out.write("    <tr><td width=\"10%\" style=\"background:rgba(75,159,243,0.5);\" rowspan=\"" + rows + "\">" + week + "的天气预报</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">当前天</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">大气压</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">降水概率</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">日出日落时间</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(75,209,253,0.5);\" colspan=\"2\">紫外线强度等级</td>" +
                        "    </tr><tr><td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("day") + "</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("air_press") + "</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("jiangshui") + "</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("sun_begin_end") + "</td>" +
                        "        <td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("ziwaixian") + "</td>" +
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
                        "    </tr><tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("day_weather") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("day_wind_power") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("day_wind_direction") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("day_air_temperature") + "℃</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\"" + cityTemp.getString("day_weather_pic") + "\" width=\"30\"></image></td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("night_weather") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("night_wind_power") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("night_wind_direction") + "</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("night_air_temperature") + "℃</td>" +
                        "        <td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\"" + cityTemp.getString("night_weather_pic") + "\" width=\"30\"></image></td></tr>");
                if (rows == 13) {
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
                    while (nextTemp.hasNext()) {
                        String hourTemp = nextTemp.next().toString();
                        cityTemp = JSONObject.parseObject(hourTemp);
                        out.write("<tr><td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("hour") + "</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("weather") + "</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("temperature") + "℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("temperature_min") + "℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("temperature_max") + "℃</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("wind_power") + "</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("wind_direction") + "</td>" +
                                "<td width=\"10%\" style=\"background:rgba(255,255,255,0.5);\">" + cityTemp.getString("precip") + "</td>" +
                                "<td width=\"10%\" colspan=\"2\" style=\"background:rgba(255,255,255,0.5);\"> <image src=\"" + cityTemp.getString("weather_pic") + "\" width=\"30\"></image></td></tr>");
                    }
                }
            }
        }//N天天气预报
        out.write("</table></body>");
    }
}
