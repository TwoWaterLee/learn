package com.naver.my.simple.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public class DubboSpiTest {

	public static void main(String[] args) {
		ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
		Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
		optimusPrime.sayHello();

		Robot bumblebee = extensionLoader.getExtension("bumblebee");
		bumblebee.sayHello();
	}
}
