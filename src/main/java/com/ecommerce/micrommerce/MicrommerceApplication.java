package com.ecommerce.micrommerce;

import com.ecommerce.micrommerce.model.Product;
import com.ecommerce.micrommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicrommerceApplication {

    private ProductRepository productRepository;
    public static void main(String[] args) {
        SpringApplication.run(MicrommerceApplication.class, args);
    }
    @Bean
    CommandLineRunner start( ProductRepository productRepository){
        return args -> {
            productRepository.save(new Product(1,"Hp_250",500,100));
            productRepository.save(new Product(2,"Samsung",600,200));
            productRepository.save(new Product(3,"Lenovo",700,300));

        };
    }
}
