package com.findit;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinditApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinditApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<JakartaWebServlet> h2ServletRegistration() {
		ServletRegistrationBean<JakartaWebServlet> registration = 
				new ServletRegistrationBean<>(new JakartaWebServlet());
		registration.addUrlMappings("/h2-console/*");
		return registration;
	}

}
