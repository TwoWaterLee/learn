package com.naver.mySentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class TestDemo {

	public static void main(String[] args) {
		initFlowRules();

		while (true) {
			try (Entry entry = SphU.entry("helloworld")) {
				System.out.println("hello world");
			} catch (BlockException ex) {
				System.out.println("blocked!");
			}
		}
	}

	private static void initFlowRules() {
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		rule.setResource("helloworld");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setCount(1);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}
}
