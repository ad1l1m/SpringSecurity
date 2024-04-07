package com.alibou.security;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan(basePackages="com.alibou.security")
//@EnableAutoConfiguration
@SpringBootApplication
public class SecurityApplication {


	public static void main(String[] args) {

		SpringApplication.run(SecurityApplication.class, args);

	}

}
