package com.rakbank.feeservice;

import com.rakbank.feeservice.properties.StudentServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class FeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeeServiceApplication.class, args);
	}

}
