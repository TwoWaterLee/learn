package com.naver.dubbo.api;

import com.naver.dubbo.bean.UserInfo;

import java.util.List;

public interface UserInfoService {
	List<UserInfo> userInfoList(String name);
}
