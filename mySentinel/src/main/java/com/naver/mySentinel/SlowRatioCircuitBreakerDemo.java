package com.naver.mySentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SlowRatioCircuitBreakerDemo {
	private static final String KEY = "some_method";

	private static volatile boolean stop = false;
	private static int seconds = 120;

	private static AtomicInteger total = new AtomicInteger();
	private static AtomicInteger pass = new AtomicInteger();
	private static AtomicInteger block = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		initDegradeRule();
		registerStateChangeObserver();
		startTick();

		int concurrency = 8;
		for (int i = 0; i < concurrency; i++) {
			Thread entryThread = new Thread(() -> {
				while (true) {
					Entry entry = null;
					try {
						entry = SphU.entry(KEY);
						pass.incrementAndGet();
						sleep(ThreadLocalRandom.current().nextInt(40, 60));
					} catch (BlockException e) {
						block.incrementAndGet();
						sleep(ThreadLocalRandom.current().nextInt(5, 10));
					} finally {
						total.incrementAndGet();
						if (entry != null) {
							entry.exit();
						}
					}
				}
			});
			entryThread.setName("sentinel-simulate-traffic-task-" + i);
			entryThread.start();
		}
	}

	private static void registerStateChangeObserver() {
		EventObserverRegistry.getInstance().addStateChangeObserver("logging",
				(prevState, newState, rule, snapshotValue) -> {
			if (newState == CircuitBreaker.State.OPEN) {
				System.out.println(String.format("%s -> OPEN at %d, snapshotValue=%.2f", prevState.name(), TimeUtil.currentTimeMillis(), snapshotValue));
			} else {
				System.out.println(String.format("%s -> %s at %d", prevState.name(), newState.name(), TimeUtil.currentTimeMillis()));
			}
		});
	}

	private static void initDegradeRule() {
		List<DegradeRule> rules = new ArrayList<>();
		DegradeRule rule = new DegradeRule(KEY)
				.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType())
				.setCount(50)
				.setTimeWindow(10)
				.setSlowRatioThreshold(0.6)
				.setMinRequestAmount(100)
				.setStatIntervalMs(20000);
		rules.add(rule);

		DegradeRuleManager.loadRules(rules);
		System.out.println("Degrade rule loaded: " + rules);
	}

	private static void sleep(int timeMs) {
		try {
			TimeUnit.MILLISECONDS.sleep(timeMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void startTick() {
		Thread timer = new Thread(new TimerTask());
		timer.setName("sentinel-timer-tick-task");
		timer.start();
	}

	static class TimerTask implements Runnable {
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			System.out.println("Begin to ");
		}
	}
}
