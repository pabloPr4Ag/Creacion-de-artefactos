package com.pragma.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.pragma.pagos.infrastructure.config.CircuitBreakerConfig;

@SpringBootApplication
@Import(CircuitBreakerConfig.class)
public class PagosApplication {
    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }
}