package com.company;

import java.io.*;
import java.net.*;
import com.alibaba.fastjson.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("┌────────────────────────────────────────────┐ ");
        System.out.println("│         这是一个能够获取天气信息的小程序         │");
        System.out.println("└────────────────────────────────────────────┘\n\n ");
        String defaultUrl = "http://route.showapi.com/9-2?showapi_appid=832113&showapi_sign=0fb0b0771cdf49c89e11c1222dd709ab";
        System.out.println("默认URL:http://route.showapi.com/9-2?showapi_appid=832113&showapi_sign=0fb0b0771cdf49c89e11c1222dd709ab\n\n");
        System.out.print("请输入URL(留空则默认,若默认URL未过期则可不填)：");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        String newUrl = new String();
        newUrl = scan.readLine();
        if(!newUrl.isEmpty()){
            defaultUrl = newUrl;
        }
        try {
            {
                URL Address = new URL(defaultUrl);
                String hostname = Address.getHost();
                int defaultPort = Address.getDefaultPort();
                int port = Address.getPort();
                System.out.println("该URL的主机名为：" + hostname);
                System.out.println("该URL的默认端口为：" + defaultPort);
                System.out.println("该URL的端口为：" + port);
                System.out.println("");
            }//URL响应与基本信息回应
            String city = new String();
            System.out.print("请输入你要查询的城市名字(留空则默认武汉)：");
            city = scan.readLine();
            System.out.println("");
            if(city.isEmpty()) {
                city = "武汉";
            }
            defaultUrl = defaultUrl.concat("&area="+city);
            URL cityAddress = new URL(defaultUrl);
            URLConnection weatherConn = cityAddress.openConnection();
            BufferedReader cityRead = new BufferedReader(new InputStreamReader(weatherConn.getInputStream()));
            String cityMes = new String("[");String temp;
            while((temp = cityRead.readLine())!=null){
                cityMes = cityMes + temp;
            }
            cityMes += "]";
            JSONArray cityInf = JSONArray.parseArray(cityMes);
            {
                JSONObject objectMain = cityInf.getJSONObject(0);
                objectMain = objectMain.getJSONObject("showapi_res_body");
                try {
                    JSONObject remark = objectMain.getJSONObject("remark");
                }catch(JSONException JE){
                    System.out.println("找不到该城市！");
                    System.exit(1);}//检测是否为非法城市名
                {
                    System.out.println("预报发布时间："+objectMain.getString("time"));
                    System.out.println("");
                    JSONObject cityTemp = new JSONObject();
                    {
                        cityTemp = objectMain.getJSONObject("cityInfo");
                        System.out.println("查询地区的基本资料：");
                        System.out.println("城市所在国家：" + cityTemp.getString("c9") + "(" + cityTemp.getString("c8") + ")");
                        System.out.println("城市所在省份：" + cityTemp.getString("c7") + "(" + cityTemp.getString("c6") + ")");
                        System.out.println("所查询的城市：" + cityTemp.getString("c5") + "(" + cityTemp.getString("c4") + ")");
                        System.out.println("城市级别：" + cityTemp.getString("c10"));
                        System.out.println("城市邮编：" + cityTemp.getString("c12"));
                        System.out.println("城市时区：" + cityTemp.getString("c17"));
                        System.out.println("");
                    }//地区基本资料
                    {
                        cityTemp = objectMain.getJSONObject("now");
                        System.out.println("现在的实时天气情况：");
                        System.out.println("天气：" + cityTemp.getString("weather"));
                        System.out.println("空气质量指数：" + cityTemp.getString("aqi"));
                        System.out.println("空气湿度：" + cityTemp.getString("sd"));
                        System.out.println("风向：" + cityTemp.getString("wind_direction"));
                        System.out.println("风力：" + cityTemp.getString("wind_power"));
                        System.out.println("获取气温时间：" + cityTemp.getString("temperature_time"));
                        System.out.println("气温：" + cityTemp.getString("temperature"));
                        System.out.println("");
                    }//现在的实时天气情况
                    {
                        cityTemp = (objectMain.getJSONObject("now")).getJSONObject("aqiDetail");
                        System.out.println("AQI明细数据：");
                        System.out.println("CO mg/m³/h：" + cityTemp.getString("co"));
                        System.out.println("SO₂ μg/m³/h：" + cityTemp.getString("so2"));
                        System.out.println("颗粒物(粒径<=2.5μm /h)：" + cityTemp.getString("pm2_5"));
                        System.out.println("颗粒物(粒径<=10μm /h)：" + cityTemp.getString("pm10"));
                        System.out.println("首要污染物：" + cityTemp.getString("primary_pollutant"));
                        System.out.println("空气质量指数类别：" + cityTemp.getString("quality"));
                        System.out.println("");
                    }//AQI明细数据
                    String week = new String();
                    {
                        cityTemp = objectMain.getJSONObject("f1");
                        System.out.println("今天的天气预报：");
                        {
                            int weekday;
                            weekday = cityTemp.getIntValue("weekday");
                            switch (weekday) {
                                case 1:
                                    week = "一";
                                    break;
                                case 2:
                                    week = "二";
                                    break;
                                case 3:
                                    week = "三";
                                    break;
                                case 4:
                                    week = "四";
                                    break;
                                case 5:
                                    week = "五";
                                    break;
                                case 6:
                                    week = "六";
                                    break;
                                case 7:
                                    week = "日";
                                    break;
                            }
                        }//将星期数字中文化
                        System.out.println("当前日期：星期" + week);
                        System.out.println("大气压：" + cityTemp.getString("air_press"));
                        System.out.println("降水概率：" + cityTemp.getString("jiangshui"));
                        System.out.println("日出日落：" + cityTemp.getString("sun_begin_end"));
                        System.out.println("紫外线强度等级：" + cityTemp.getString("ziwaixian"));
                        System.out.println("白天天气：" + cityTemp.getString("day_weather"));
                        System.out.println("白天风力编号：" + cityTemp.getString("day_wind_power"));
                        System.out.println("白天风向：" + cityTemp.getString("day_wind_direction"));
                        System.out.println("白天天气温度：" + cityTemp.getString("day_air_temperature"));
                        System.out.println("晚上天气：" + cityTemp.getString("night_weather"));
                        System.out.println("晚上风力编号：" + cityTemp.getString("night_wind_power"));
                        System.out.println("晚上风向：" + cityTemp.getString("night_wind_direction"));
                        System.out.println("晚上天气温度：" + cityTemp.getString("night_air_temperature"));
                        System.out.println("");
                    }//今天天气预报
                    {
                        cityTemp = objectMain.getJSONObject("f2");
                        System.out.println("明天的天气预报：");
                        {
                            int weekday;
                            weekday = cityTemp.getIntValue("weekday");
                            switch (weekday) {
                                case 1:
                                    week = "一";
                                    break;
                                case 2:
                                    week = "二";
                                    break;
                                case 3:
                                    week = "三";
                                    break;
                                case 4:
                                    week = "四";
                                    break;
                                case 5:
                                    week = "五";
                                    break;
                                case 6:
                                    week = "六";
                                    break;
                                case 7:
                                    week = "日";
                                    break;
                            }
                        }//将星期数字中文化
                        System.out.println("当前日期：星期" + week);
                        System.out.println("大气压：" + cityTemp.getString("air_press"));
                        System.out.println("降水概率：" + cityTemp.getString("jiangshui"));
                        System.out.println("日出日落：" + cityTemp.getString("sun_begin_end"));
                        System.out.println("紫外线强度等级：" + cityTemp.getString("ziwaixian"));
                        System.out.println("白天天气：" + cityTemp.getString("day_weather"));
                        System.out.println("白天风力编号：" + cityTemp.getString("day_wind_power"));
                        System.out.println("白天风向：" + cityTemp.getString("day_wind_direction"));
                        System.out.println("白天天气温度：" + cityTemp.getString("day_air_temperature"));
                        System.out.println("晚上天气：" + cityTemp.getString("night_weather"));
                        System.out.println("晚上风力编号：" + cityTemp.getString("night_wind_power"));
                        System.out.println("晚上风向：" + cityTemp.getString("night_wind_direction"));
                        System.out.println("晚上天气温度：" + cityTemp.getString("night_air_temperature"));
                        System.out.println("");
                    }//明天天气预报
                    {
                        cityTemp = objectMain.getJSONObject("f3");
                        System.out.println("后天的天气预报：");
                        {
                            int weekday;
                            weekday = cityTemp.getIntValue("weekday");
                            switch (weekday) {
                                case 1:
                                    week = "一";
                                    break;
                                case 2:
                                    week = "二";
                                    break;
                                case 3:
                                    week = "三";
                                    break;
                                case 4:
                                    week = "四";
                                    break;
                                case 5:
                                    week = "五";
                                    break;
                                case 6:
                                    week = "六";
                                    break;
                                case 7:
                                    week = "日";
                                    break;
                            }
                        }//将星期数字中文化
                        System.out.println("当前日期：星期" + week);
                        System.out.println("大气压：" + cityTemp.getString("air_press"));
                        System.out.println("降水概率：" + cityTemp.getString("jiangshui"));
                        System.out.println("日出日落：" + cityTemp.getString("sun_begin_end"));
                        System.out.println("紫外线强度等级：" + cityTemp.getString("ziwaixian"));
                        System.out.println("白天天气：" + cityTemp.getString("day_weather"));
                        System.out.println("白天风力编号：" + cityTemp.getString("day_wind_power"));
                        System.out.println("白天风向：" + cityTemp.getString("day_wind_direction"));
                        System.out.println("白天天气温度：" + cityTemp.getString("day_air_temperature"));
                        System.out.println("晚上天气：" + cityTemp.getString("night_weather"));
                        System.out.println("晚上风力编号：" + cityTemp.getString("night_wind_power"));
                        System.out.println("晚上风向：" + cityTemp.getString("night_wind_direction"));
                        System.out.println("晚上天气温度：" + cityTemp.getString("night_air_temperature"));
                        System.out.println("");
                    }//后天天气预报
                    System.out.println("天气URL："+defaultUrl);
                    System.out.println("将上面的地址复制后可以分享给其它人！");
                    System.out.println("PS:不要分享太多次，不然接口会超过调用次数上限！(免费接口现状，卑微程序员穷啊！)");
                }//输出JSON数据表格
            }//读取JSON数据
        }catch ( MalformedURLException me){
            System.out.println("无效URL！");
            System.exit(1);
        }
    }
}