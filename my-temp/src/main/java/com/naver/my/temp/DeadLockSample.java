package com.naver.my.temp;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cn17427
 * @date 2021/1/19
 */
public class DeadLockSample extends Thread {
	private String first;
	private String second;

	public DeadLockSample(String name, String first, String second) {
		super(name);
		this.first = first;
		this.second = second;
	}

	@Override
	public void run() {
		synchronized (first) {
			System.out.println(this.getName() + " obtained: " + first);
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e ) {
				e.printStackTrace();
			}
			synchronized (second) {
				System.out.println(this.getName() + " obtained: " + second);
			}

		}
	}

	public static void main(String[] args) throws Exception {
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		Runnable dlCheck = new Runnable() {
			@Override
			public void run() {
				long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
				if (deadlockedThreads != null) {
					ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreads);
					System.out.println("Detected deadlock threads:");
					for (ThreadInfo threadInfo : threadInfos) {
						System.out.println(threadInfo.getThreadName());
					}

//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					for (ThreadInfo threadInfo : threadInfos) {
//						Thread deadLockThread = findThread(threadInfo.getThreadId());
//						System.out.println("interrupt -> " + deadLockThread.getName());
//						deadLockThread.destroy();
//					}
				}
			}
		};

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(dlCheck, 1L, 2L, TimeUnit.SECONDS);

		String lockA = "lockA";
		String lockB = "lockB";
		DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
		DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	public static Thread findThread(long threadId) {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		while (threadGroup != null) {
			Thread[] threads = new Thread[(int)(threadGroup.activeCount() * 1.5)];
			int count = threadGroup.enumerate(threads, true);
			for (int i = 0; i < count ; i++) {
				if (threadId == threads[i].getId()) {
					return threads[i];
				}
			}
			threadGroup = threadGroup.getParent();
		}
		return null;
	}
}