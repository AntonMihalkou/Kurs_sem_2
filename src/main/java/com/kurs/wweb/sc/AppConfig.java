package com.kurs.wweb.sc;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 * Класс конфигурации приложения.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.kurs.wweb.repository")
public class AppConfig {

    /**
     * Создает бин PasswordEncoder.
     * @return объект PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
