package com.naver.mycloud.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("config")
public class ConfigController {

	@NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
	private boolean useLocalCache;

	@RequestMapping(value = "/get", method = GET)
	public boolean get() {
		return useLocalCache;
	}
}
