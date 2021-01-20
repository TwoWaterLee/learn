package com.naver.my.jvm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cn17427
 * @date 2021/1/19
 */
@Slf4j
@RestController
public class HelloController {

	private static final int M = 1024 * 1024;

	@RequestMapping("/hello")
	public String hello(@RequestParam String name) {
//		Object oa = new Object();
//		Object ob = new Object();
		Lock locka = new ReentrantLock();
		Lock lockb = new ReentrantLock();


		Thread t1 = new Thread() {
			@Override
			public void run() {
				locka.lock();
				System.out.println(Thread.currentThread().getName() + " get oa ");
				lockb.lock();
				System.out.println(Thread.currentThread().getName() + " get ob");
			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				lockb.lock();
				System.out.println(Thread.currentThread().getName() + " get ob");
				locka.lock();
				System.out.println(Thread.currentThread().getName() + " get oa");
			}
		};


		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (Exception e) {
			System.out.println(e);
		}
		return name;
	}


	@RequestMapping("/test")
	public String test(@RequestParam Integer count) {
		log.info(" *** count = {} ***", count);

		for (int i = 0 ; i < 10; i++) {
			byte[] test = new byte[count*M];
		}
		log.info(" --- after 10 --- ");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 20; i++) {
			byte[] test = new byte[count*M];
		}
		log.info(" --- after 20 --- ");


		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 50; i++) {
			byte[] test = new byte[count*M];
		}
		log.info(" --- after 50 --- ");

		return "OK";
	}


}
