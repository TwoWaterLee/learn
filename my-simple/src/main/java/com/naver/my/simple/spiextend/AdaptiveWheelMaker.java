package com.naver.my.simple.spiextend;


import com.alibaba.dubbo.common.extension.ExtensionLoader;

import com.alibaba.dubbo.common.URL;

/**
 * @author cn17427
 * @date 2021/1/18
 */
public class AdaptiveWheelMaker implements WheelMaker {

	@Override
	public Wheel makeWheel(URL url) {
		if (url == null) {
			throw new IllegalArgumentException("url == null");
		}
		// 1.从 URL 中获取 WheelMaker 名称
		String wheelMakerName = url.getParameter("Wheel.maker");
		if (wheelMakerName == null) {
			throw new IllegalArgumentException("wheelMakerName == null");
		}

		// 2.通过 SPI 加载具体的的 WheelMaker
		WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(wheelMakerName);

		// 3. 调用目标方法
		return wheelMaker.makeWheel(url);

	}
}
