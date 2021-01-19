package com.naver.my.simple;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;

/**
 * @author cn17427
 * @date 2021/1/14
 */
public class ProCon {
	final static NonReentrantLock lock = new NonReentrantLock();
	final static Condition notFull = lock.newCondition();
	final static Condition notEmpty = lock.newCondition();

	final static Queue<String> queue = new LinkedBlockingQueue<>();
	final static int queueSize = 10;

	public static void main(String[] args) throws Exception {
		Thread producer = new Thread(new Runnable() {
			AtomicLong count = new AtomicLong(0);
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					lock.lock();
					try {
						while (queue.size() == queueSize) {
							notEmpty.await();
						}
						queue.add("ele");
						System.out.println("pro --- " + count.incrementAndGet());

						notFull.signalAll();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		});

		Thread consumer = new Thread(new Runnable() {
			AtomicLong count = new AtomicLong(0);
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					lock.lock();
					try {
						while (0 == queue.size()) {
							notFull.await();
						}
						String ele = queue.poll();
						System.out.println("con *** " + count.getAndIncrement());
						notEmpty.signalAll();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		});

		producer.start();
		consumer.start();

		Thread.sleep(15);
		producer.interrupt();
		consumer.interrupt();
	}

}
