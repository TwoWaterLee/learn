package com.naver.my.simple.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author cn17427
 * @date 2021/1/18
 */
@SPI
public interface Robot {
	void sayHello();
}
