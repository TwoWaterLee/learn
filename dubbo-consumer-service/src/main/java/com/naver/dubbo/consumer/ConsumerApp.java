package com.naver.dubbo.consumer;

import com.naver.dubbo.bean.UserInfo;
import com.naver.dubbo.consumer.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ConsumerApp {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
		TestService testService = context.getBean(TestService.class);
		List<UserInfo> userInfos = testService.userInfoList("test");
		userInfos.forEach(userInfo -> System.out.println(userInfo.getAddress()));
		context.start();
		System.in.read();
	}
}
