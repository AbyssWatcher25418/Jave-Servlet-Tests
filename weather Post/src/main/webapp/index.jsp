<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>简易天气查询系统</title>
    <link rel="icon" href="https://i.loli.net/2021/11/25/zl48j9tyOgmX6L3.png">
    <link rel="shortcut icon" href="https://i.loli.net/2021/11/25/zl48j9tyOgmX6L3.png">
</head>
<body style="background-image:url(https://i.loli.net/2021/11/24/HDGb3w1vNWds4uJ.jpg);
background-repeat:no-repeat;background-position:center;">
<%= "<h1 style=\"text-align:center\">一个简易天气查询系统</h1>" %><br><br>
<div style="width:100%;text-align:center">
<form name="weather_request" action=Detail method="get">
    请输入待查询城市名：<input type="text" name="city_name" placeholder="请输入正确的城市名"><br>
    是否需要每3小时的天气预报？：不需要<input type="radio" name="3hour" value="no" checked="checked">  需要<input type="radio" name="3hour" value="yes"><br>
    请选择待查询的天数：<input type="checkbox" name="days" value="day1" checked="checked">今天
    <input type="checkbox" name="days" value="day2">明天 <input type="checkbox" name="days" value="day3">后天
    <input type="checkbox" name="days" value="day4">第四天 <input type="checkbox" name="days" value="day5">第五天
    <input type="checkbox" name="days" value="day6">第六天 <input type="checkbox" name="days" value="day7">第七天
    <br><br><input type="submit" value="点击查询天气"><br><br>
</form>
</div>
<div style="width:100%;text-align:center;color:#ff0000">
    PS：若无法加载天气详情，请检查城市名是否含有除汉字外的任何字符！
    如遇BUG或天气接口请求失效，请联系QQ:2478024268
</div>
</body>