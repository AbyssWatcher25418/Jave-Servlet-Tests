package com.example.weather_spring;

import com.example.weather_spring.Beans.NewTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication()
public class MainApp {
    static private final Logger log = Logger.getLogger(MainApp.class.getName());
    public static void addInfo(String info){
        MainApp.log.info(info);
    }
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        NewTest obj = context.getBean("newTest",com.example.weather_spring.Beans.NewTest.class);
        obj.sendMes();
        SpringApplication.run(MainApp.class, args);
        MainApp.addInfo("服务器已成功启动！  启动日期："+new Date());
        MainApp.addInfo("天气查询主页地址为：http://localhost:8080/WeatherReq/");
    }
}
