package com.naver.mycloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@SentinelResource(value = "sayHello", blockHandler = "blockSayHello")
	public String sayHello(String name) {
		return "Hello, " + name;
	}

	public String blockSayHello(String name, BlockException e) {
		e.printStackTrace();
		return "blockSayHello";
	}

}
