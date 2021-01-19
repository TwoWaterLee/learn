package com.naver.my.simple;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author cn17427
 * @date 2021/1/16
 */
public class App1 {

	public static void main(String[] args) throws Exception {

	}

	private static void serviceTest() throws Exception {
		NamingService namingService = NamingFactory.createNamingService("localhost:8848");
		namingService.registerInstance("nacos.test.3", "11.11.11.11", 8888, "TEST1");

		Instance instance = new Instance();
		instance.setIp("55.55.55.55");
		instance.setPort(9999);
		instance.setHealthy(false);
		instance.setWeight(2.0);
		Map<String, String> instanceMeta = new HashMap<>();
		instanceMeta.put("site", "et2");
		instance.setMetadata(instanceMeta);

		Service service = new Service("nacos.test.4");
		service.setAppName("nacos-naming");
		service.setGroupName("CNCF");
		service.setProtectThreshold(0.8F);
		Map<String, String> serviceMeta = new HashMap<>();
		serviceMeta.put("symmetriCall", "true");
		service.setMetadata(serviceMeta);
		instance.setServiceName(service.getName());

		namingService.registerInstance("nacos.test.4", instance);
	}

	private static void configTest() throws Exception {
		String serverAddr = "localhost:8848";
		String dataId = "cloudalibaba-sentinel-service";
		String group = "DEFAULT_GROUP";
		Properties properties = new Properties();
		properties.put("serverAddr", serverAddr);
		ConfigService configService = NacosFactory.createConfigService(properties);
		String content = configService.getConfig(dataId, group, 5000);
		System.out.println(content);

		configService.addListener(dataId, group, new Listener() {
			@Override
			public Executor getExecutor() {
				return null;
			}

			@Override
			public void receiveConfigInfo(String s) {
				System.out.println("recived: " + s);
			}
		});

		while (true) {
			Thread.sleep(1000);
		}
	}
}
