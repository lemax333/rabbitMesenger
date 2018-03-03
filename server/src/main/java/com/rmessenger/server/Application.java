package com.rmessenger.server;

import com.rmessenger.server.config.RabbitMqConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitMqConfig.class)
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
