package com.example.weather_spring.Beans;

public class  NewTest {
    private String message;
    public void setMes(String Mes){
        this.message = Mes;
    }
    public void sendMes(){
        System.out.println(message);
    }
}
