package com.naver.mycloud;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class MycloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudApplication.class, args);
	}

}
