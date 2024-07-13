package it.epicode.feste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FesteApplication.class, args);
	}

}




