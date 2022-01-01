package com.shareEats.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ShareEatsNotificationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareEatsNotificationServerApplication.class, args);
	}
	
}
