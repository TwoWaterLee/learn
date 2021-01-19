package com.naver.my.simple;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cn17427
 * @date 2021/1/13
 */
public class App0 {

	public static void main(String[] args) {

		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();

		lock.lock();
		try {
			System.out.println("begin wait");
			condition.await();
			System.out.println("end wait");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		lock.lock();
		try {
			System.out.println("begin signal");
			condition.signal();
			System.out.println("end signal");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}



	private static void testCopyOnWriteArrayList() {
		CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
		copyOnWriteArrayList.add("123");
		copyOnWriteArrayList.add("456");
		Iterator<String> itr = copyOnWriteArrayList.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
