package com.naver.my.simple.spi;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public class Bumblebee implements Robot {

	@Override
	public void sayHello() {
		System.out.println("Hello, I am Bumblebee.");
	}
}
