package com.naver.my.temp;

/**
 * @author cn17427
 * @date 2021/1/20
 *
 * -Xms200M
 * -Xmx200M
 * -Xmn100M
 * -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=5
 * -XX:PretenureSizeThreshold=20M
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:CMSInitiatingOccupancyFraction=92
 * -XX:+UseCMSInitiatingOccupancyOnly
 * -XX:+UseCMSCompactAtFullCollection
 * -XX:CMSFullGCsBeforeCompaction=0
 * -XX:+PrintGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -Xloggc:./gc.log
 */
public class GcTest {
	private static final int SIZE_1M = 1024 * 1024;

	public static void main(String[] args) {
		sleep(1);

		for (int i = 0; i < 100; i++) {
			loadData(i);
		}
	}

	public static void loadData(int index) {
		System.out.println("load data: " + index);
		byte[] data1 = new byte[10 * SIZE_1M];
		byte[] data2 = new byte[10 * SIZE_1M];

		sleep(1);
	}

	public static void sleep(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
