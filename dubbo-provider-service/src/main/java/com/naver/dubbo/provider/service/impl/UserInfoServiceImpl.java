package com.naver.dubbo.provider.service.impl;

import com.naver.dubbo.api.UserInfoService;
import com.naver.dubbo.bean.UserInfo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserInfoServiceImpl implements UserInfoService {

	@Override
	public List<UserInfo> userInfoList(String name) {
		System.out.println("------------ userInfoList -----------" + name);
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setName(name);
		userInfo1.setAddress("beijing");
		userInfo1.setAge(18);
		userInfo1.setBirthday(new Date());

		UserInfo userInfo2 = new UserInfo();
		userInfo2.setName(name);
		userInfo2.setAddress("shanghai");
		userInfo2.setAge(19);
		userInfo2.setBirthday(new Date());
		return Arrays.asList(userInfo1, userInfo2);
	}
}
