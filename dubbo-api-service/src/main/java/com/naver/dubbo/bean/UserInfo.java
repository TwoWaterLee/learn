package com.naver.dubbo.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo  implements Serializable {
	private String name;
	private String address;
	private Date birthday;
	private Integer age;
}
