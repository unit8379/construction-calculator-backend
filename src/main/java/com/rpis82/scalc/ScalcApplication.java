package com.rpis82.scalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rpis82.scalc.microapps.HibernateSessionTest;

@SpringBootApplication
public class ScalcApplication {
		
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ScalcApplication.class, args);
		
		//startMicroapp(context); отключил микроприложение для тестирования Hibernate'а
	}
	
	public static void startMicroapp(ConfigurableApplicationContext context) {		
		HibernateSessionTest service = context.getBean("sessionTest", HibernateSessionTest.class);
		service.createUser();
	}
	
	/* Добавление в реестр WebMvc конфигурации допустимых путей, источников запросов (origins)
	   и методов в запросах с использованием CORS (Cross-Origin Resource Sharing).
	   При составлении ответов (Response), Spring будет автоматически добавлять
	   в них заголовок Access-Control-Allow-Origin с допустимым источником запроса. */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");;
			}
		};
	}
}
