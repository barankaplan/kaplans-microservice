package com.kaplansmicroservice.customer;

import com.kaplansmicroservice.amqp.RabbitMQMessageProducer;
import com.kaplansmicroservice.clients.fraud.FraudCheckResponse;
import com.kaplansmicroservice.clients.fraud.FraudClient;
import com.kaplansmicroservice.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final RestTemplate restTemplate; no need !


//private final NotificationClient notificationClient; no need after rabbitmq

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if (Boolean.TRUE.equals(fraudCheckResponse.isFraudster())) {
            throw new IllegalStateException("fraudster");
        }



//        if (fraudCheckResponse.isFraudster()) {
//            throw new IllegalStateException("fraudster");
//        }

        // todo: send notification


        // todo: make it async. i.e add to queue
        //no need after rabbitmq
//        notificationClient.sendNotification(
//                new NotificationRequest(
//                        customer.getId(),
//                        customer.getEmail(),
//                        String.format("Hi %s, welcome to Kaplans microservie...",
//                                customer.getFirstName())
//                )
//        );

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to my microservice",
                        customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(notificationRequest,"internal.exchange",
                "internal.notification.routing-key");


    }
}
