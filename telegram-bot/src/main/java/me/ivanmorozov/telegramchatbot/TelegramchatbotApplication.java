package me.ivanmorozov.telegramchatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication()
public class TelegramchatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramchatbotApplication.class, args);
	}

}
