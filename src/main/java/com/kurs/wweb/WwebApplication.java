package com.kurs.wweb;

import com.kurs.wweb.SC.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)

public class WwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WwebApplication.class, args);
	}

}
