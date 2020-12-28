package com.naver.dubbo.consumer.service.impl;

import com.naver.dubbo.api.UserInfoService;
import com.naver.dubbo.bean.UserInfo;
import com.naver.dubbo.consumer.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private UserInfoService userInfoService;

	@Override
	public List<UserInfo> userInfoList(String name) {
		return userInfoService.userInfoList(name);
	}
}
