package com.example.crowdControl.configurations;

import org.springframework.context.annotation.Configuration;
import sun.security.ssl.Debug;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
        Debug.println("TIMEZONE", "Spring boot application running in Singapore timezone :"+new Date());
        //Male sure mySQL uses the same timezone too
        //e.g. spring.datasource.url=jdbc:mysql://localhost:3306/dbName?serverTimezone=Asia/Singapore
    }
}
