package com.sparta.aiverification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AiVerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiVerificationApplication.class, args);
    }

}
