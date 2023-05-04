package com.redis.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class SessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionApplication.class, args);
	}

	@RequestMapping("/")
	public String hello(@RequestParam(value = "name", defaultValue = "admin") String name) {
		return String.format("Hello %s", name);
	}

}
