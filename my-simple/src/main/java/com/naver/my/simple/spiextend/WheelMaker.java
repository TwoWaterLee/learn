package com.naver.my.simple.spiextend;

import com.alibaba.dubbo.common.URL;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public interface WheelMaker {
	Wheel makeWheel(URL url);
}
