package com.kaplansmicroservice.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {


    //new open feign notification client-that post mapping to notification
    //accepts the request and map into implementation of the controller which we loggeed in
    //and  sends message to the notification service for request
    //notification service builds and persist an instance
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
