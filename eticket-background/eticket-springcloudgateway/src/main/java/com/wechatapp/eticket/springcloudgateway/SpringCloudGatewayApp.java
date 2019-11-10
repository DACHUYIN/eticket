package com.wechatapp.eticket.springcloudgateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringCloudGatewayApp 
{
	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringCloudGatewayApp.class, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
