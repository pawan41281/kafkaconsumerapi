package com.example.kafkaconsumerapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vo.EmployeeVo;

@Configuration
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

	private final String employeeTopic = "employee-topic";

	@RetryableTopic(
		attempts = "4", // total of 4 tries (1 + 3 retries)
		backoff = @Backoff(
						delay = 2000, // 2 second delay
						multiplier = 2.0, // exponential backoff: 2s, 4s, 8s
						maxDelay = 10000 // max 10s delay
					),
	    include = {RuntimeException.class},
//	    exclude = {IllegalArgumentException.class}, 
		autoCreateTopics = "true", 
		dltTopicSuffix = ".dead", 
		retryTopicSuffix = ".retry"
	)
	@KafkaListener(topics = employeeTopic, groupId = "group1")
	public void consumeJson(ConsumerRecord<String, EmployeeVo> record) {
		String key = record.key();
		EmployeeVo vo = record.value();
		if(vo==null) {
			log.error("vo==null :: Message is null");
			throw new RuntimeException("Message is null");
		}
		if(vo.getMobile()==null) {
			log.error("vo.getMobile()==null :: Mobile number is missing");
			throw new RuntimeException("Mobile number is missing");
		}
		if(vo.getMobile().isBlank()) {
			log.error("vo.getMobile().isBlank() :: Mobile number is missing");
			throw new RuntimeException("Mobile number is missing");
		}
		log.info("Message consume successfully. Result :: topic :: {}  | key :: {} | Message :: {}", employeeTopic, key, vo);
	}

//	@KafkaListener(topics = kafkaTopic, groupId = "group1")
//	public void consumeJson(EmployeeVo EmployeeVo) {
//		log.info("Message consume successfully. Result :: topic :: {}  | Message :: {}", kafkaTopic, EmployeeVo);
//	}

	@DltHandler
	public void handleMessageFailure(ConsumerRecord<String, EmployeeVo> record) {
		log.info("Message handled successfully. Result :: topic :: {}  | key :: {} | Message :: {}", employeeTopic, record.key(), record.value());
	}

}
