package com.kaplansmicroservice.notification;

import com.kaplansmicroservice.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.Locale;
import java.util.function.Consumer;

@SpringBootApplication(scanBasePackages = {
        "com.kaplansmicroservice.notification", "com.kaplansmicroservice.amqp"
})
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class NotificationApplication {


    //new open feign notification client-that post mapping to notification
    //accepts the request and map into implementation of the controller which we loggeed in
    //and  sends message to the notification service for request
    //notification service builds and persist an instance
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer rabbitMQMessageProducer,
                                        NotificationConfiguration notificationConfiguration){
//        return args -> {
//            rabbitMQMessageProducer.publish("foo"
//                    ,notificationConfiguration.getInternalExchange(),
//                    notificationConfiguration.getInternalNotificationRoutingKey());
//        };

        return args -> {
            rabbitMQMessageProducer.publish(new Person("Baran",32)
                    ,notificationConfiguration.getInternalExchange(),
                    notificationConfiguration.getInternalNotificationRoutingKey());
        };

    }
     record Person(String name, int age){};


}
