package com.naver.mykafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cn17427
 * @date 2021/1/9
 */
@Slf4j
@RestController
public class ConsumerController {

	@Autowired
	private KafkaConsumer<String, String> kafkaConsumer;

	private static final int MIN_BATCH_SIZE = 5;

	@RequestMapping("/autoConsumerMessage")
	public String autoConsumerMessage() {
		kafkaConsumer.subscribe(Collections.singleton("kafka-boot"));
		ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
		for (ConsumerRecord<String, String> record : records) {
			log.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
		}
		return records.toString();
	}

	@RequestMapping("manualConsumerMessage")
	public String manualConsumerMessage() {
		kafkaConsumer.subscribe(Collections.singleton("kafka-boot"));
		List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
		ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
		for (ConsumerRecord record : records) {
			buffer.add(record);
		}
		if (buffer.size() >= MIN_BATCH_SIZE) {
			log.info("buffer.size() = {}", buffer.size());
			kafkaConsumer.commitSync();
			buffer.clear();
		}
		return "success";
	}

}
