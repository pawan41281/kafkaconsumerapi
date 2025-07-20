package com.example.kafkaconsumerapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;
import vo.EmployeeVo;

@Configuration
@Slf4j
public class KafkaConsumer {


	private final String kafkaTopic = "mytopic";

	@KafkaListener(topics = kafkaTopic, groupId = "group1")
	public void consumeJson(ConsumerRecord<String, EmployeeVo> record) {
		log.info("Message consume successfully. Result :: topic :: {}  | key :: {} | Message :: {}", kafkaTopic, record.key(), record.value());
	}
	
//	@KafkaListener(topics = kafkaTopic, groupId = "group1")
//	public void consumeJson(EmployeeVo EmployeeVo) {
//		log.info("Message consume successfully. Result :: topic :: {}  | Message :: {}", kafkaTopic, EmployeeVo);
//	}

}
