package com.naver.mykafka.config;

import com.naver.mykafka.constant.KafkaConsumerProperty;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author cn17427
 * @date 2021/1/9
 */
@Configuration
public class KafkaConfig {

	@Autowired
	private KafkaConsumerProperty kafkaConsumerProperty;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public KafkaConsumer<String, String> kafkaConsumer() {
		Properties props = new Properties();
//		props.setProperty("auto.commit.intervals.ms", kafkaConsumerProperty.getAutoCommitInterval());
		props.setProperty("group.id", kafkaConsumerProperty.getGroupId());
		props.setProperty("enable.auto.commit", kafkaConsumerProperty.getEnableAutoCommit());
		props.setProperty("key.deserializer", StringDeserializer.class.getName());
		props.setProperty("value.deserializer", StringDeserializer.class.getName());
		props.setProperty("bootstrap.servers", bootstrapServers);

		return new KafkaConsumer(props);
	}
}
