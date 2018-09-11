package co.com.ceiba.parkinglotbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication
@EnableJpaRepositories("co.com.ceiba.parkinglotbackend.persistence.repositories")
@EntityScan("co.com.ceiba.parkinglotbackend.persistence.entities")
@ComponentScan("co.com.ceiba.parkinglotbackend")
@EnableJpaAuditing
@EnableTransactionManagement
public class ParkinglotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotBackendApplication.class, args);
    }
}
