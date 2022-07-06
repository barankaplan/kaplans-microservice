package com.kaplansmicroservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {
        "com.kaplansmicroservice.customer", "com.kaplansmicroservice.amqp"
})
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.kaplansmicroservice.clients"
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomerApplication {

    //INFO [customer,5ea041a05da6b846,5ea041a05da6b846] trace and span id
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
