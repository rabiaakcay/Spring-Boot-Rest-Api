package com.id3academy.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestapiApplication {

	public static void main(String[] args){
		SpringApplication.run(RestapiApplication.class, args);
	}
}
