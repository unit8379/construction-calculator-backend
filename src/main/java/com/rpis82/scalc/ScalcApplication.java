package com.rpis82.scalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
}
