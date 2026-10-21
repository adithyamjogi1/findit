package com.findit;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
=======
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9

@SpringBootApplication
public class FinditApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinditApplication.class, args);
	}

<<<<<<< HEAD
=======
	@Bean
	public ServletRegistrationBean<JakartaWebServlet> h2ServletRegistration() {
		ServletRegistrationBean<JakartaWebServlet> registration = 
				new ServletRegistrationBean<>(new JakartaWebServlet());
		registration.addUrlMappings("/h2-console/*");
		return registration;
	}

>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
}
