package com.naver.mykafka.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cn17427
 * @date 2021/1/9
 */

@Component
@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Data
public class KafkaConsumerProperty {

	private String enableAutoCommit;

	private String groupId;

	private String autoCommitInterval;

}
