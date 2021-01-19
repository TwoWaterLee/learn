package com.naver.mykafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author cn17427
 * @date 2021/1/9
 */
@Component
@Slf4j
public class ConsumerListener {

	@KafkaListener(id = "foo", topics = "kafka-boot")
	public void listen(String foo) {
		log.info("message content [{}]", foo);
	}

}
