package com.pragma.hogar360.servicesvisits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicesVisitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicesVisitsApplication.class, args);
    }

}
