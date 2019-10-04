package com.wechatapp.eticket.transaction;

import com.wechatapp.eticket.transaction.rocketmqsource.InsertEticketInfoSource;
import com.wechatapp.eticket.transaction.rocketmqsource.UpdateEticketInfoSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@EnableEurekaClient
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 900)
@EntityScan(basePackages = "com.wechatapp.eticket.core.entity")
@EnableJpaRepositories(basePackages = "com.wechatapp.eticket.core.repository")
@SpringBootApplication(scanBasePackages = "com.wechatapp.eticket")
@EnableBinding({InsertEticketInfoSource.class, UpdateEticketInfoSource.class})
public class TransactionApp {
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(TransactionApp.class, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
