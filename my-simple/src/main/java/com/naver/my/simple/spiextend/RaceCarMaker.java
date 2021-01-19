package com.naver.my.simple.spiextend;

import com.alibaba.dubbo.common.URL;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public class RaceCarMaker implements CarMaker {
	WheelMaker wheelMaker;

	public void setWheelMaker(WheelMaker wheelMaker) {
		this.wheelMaker = wheelMaker;
	}

	@Override
	public Car makeCar(URL url) {
		Wheel wheel = wheelMaker.makeWheel(url);
		return new RaceCar(wheel);
	}
}
