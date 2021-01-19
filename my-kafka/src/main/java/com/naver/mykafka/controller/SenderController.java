package com.naver.mykafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author cn17427
 * @date 2021/1/8
 */
@RestController
@Slf4j
public class SenderController {

	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final int MESSAGE_BATCH_SIZE = 10;

	@RequestMapping("syncSendMessage")
	public String syncSendMessage() {
		for (int i = 0; i < MESSAGE_BATCH_SIZE; i++) {
			try {
				kafkaTemplate.send("kafka-boot", "0", "foo" + i).get();
			} catch (InterruptedException e) {
				log.error("sync send message fail [{}]", e.getMessage());
				e.printStackTrace();
			} catch (ExecutionException e) {
				log.error("sync send message fail [{}]", e.getMessage());
				e.printStackTrace();
			}
		}
		return "success";
	}

	@RequestMapping("asyncSendMessage")
	public String asyncSendMessage() {
		for (int i = 0; i < MESSAGE_BATCH_SIZE; i++) {
			ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("kafka-boot", "0", "async" + i);
			send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
				@Override
				public void onFailure(Throwable throwable) {
					log.error("async send message fail [{}]", throwable.getMessage());
				}

				@Override
				public void onSuccess(SendResult<String, String> stringStringSendResult) {
					log.info("async send message success partition [{}]", stringStringSendResult.getRecordMetadata().partition());
					log.info("async send message success offset [{}]", stringStringSendResult.getRecordMetadata().offset());
				}
			});
		}
		return "success";
	}


}
