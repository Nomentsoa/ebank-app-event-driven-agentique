package ca.lazanomentsoa.customerservice;

import ca.lazanomentsoa.customerservice.domain.Customer;
import ca.lazanomentsoa.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(Customer.builder()
                            .name("John Doe")
                            .email("email1@me.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Jane Doe")
                    .email("email2@me.com")
                    .build());

            customerRepository.save(Customer.builder()
                    .name("Laza Nomentsoa")
                    .email("email3@me.com")
                    .build());
        };
    }


}
