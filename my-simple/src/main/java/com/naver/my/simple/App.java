package com.naver.my.simple;

/**
 * @author cn17427
 * @date 2021/1/13
 */
public class App {

	public static class ReadThread extends Thread {
		@Override
		public void run() {

			while (!Thread.currentThread().isInterrupted()) {
				if (ready) {
					System.out.println(num + num);
				}
				System.out.println("read thread...");
			}
		}
	}

	public static class Writethread extends Thread {
		@Override
		public void run() {
			num = 2;
			ready = true;
			System.out.println("writeThread set over...");
		}
	}

	private static int num = 0;
	private static boolean ready = false;

	public static void main(String[] args) throws InterruptedException {
		ReadThread rt = new ReadThread();
		rt.start();

		Writethread wt = new Writethread();
		wt.start();

		Thread.sleep(30);
		rt.interrupt();
		System.out.println("main exit");
	}
}
