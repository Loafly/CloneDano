package com.dano.clonedano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloneDanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloneDanoApplication.class, args);
    }

}
