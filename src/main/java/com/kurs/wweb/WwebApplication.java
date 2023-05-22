package com.kurs.wweb;

import com.kurs.wweb.sc.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Главный класс приложения.
 */
@SpringBootApplication
@Import(AppConfig.class)
public class WwebApplication {

	/**
	 * Точка входа в приложение.
	 * @param args аргументы командной строки.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WwebApplication.class, args);
	}
}