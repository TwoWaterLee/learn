package com.naver.nacosconsumer.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class TestConfig {
	@LoadBalanced
	@Autowired(required = false)
	private List<RestTemplate> restTemplatelist = Collections.emptyList();

	@Bean
	public SmartInitializingSingleton test() {
		return () -> {
			System.out.println(TestConfig.this.restTemplatelist);
			System.out.println("hello");
		};
	}

}
