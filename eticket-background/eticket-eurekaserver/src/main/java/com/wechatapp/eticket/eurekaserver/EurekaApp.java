package com.wechatapp.eticket.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {
	public static void main(String[] args) {
		try {
			SpringApplication.run(EurekaApp.class, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
