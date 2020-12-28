package com.naver.dubbo.consumer.service;

import com.naver.dubbo.bean.UserInfo;

import java.util.List;

public interface TestService {
	List<UserInfo> userInfoList(String name);
}
