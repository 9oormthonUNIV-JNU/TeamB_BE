package com.example.gifty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GiftyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiftyApplication.class, args);
	}

}
