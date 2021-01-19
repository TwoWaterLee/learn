package com.naver.my.simple.spi;

import java.util.ServiceLoader;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public class App2 {

	public static void main(String[] args) {
		ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
		System.out.println("Java SPI");
		serviceLoader.forEach(Robot::sayHello);
	}

}
