package com.example.kafkaconsumerapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vo.EmployeeVo;

@Configuration
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

	private final String employeeTopic = "employee-topic";

	@KafkaListener(topics = employeeTopic, groupId = "group1")
	public void consumeJson(ConsumerRecord<String, EmployeeVo> record) {
		log.info("Message consume successfully. Result :: topic :: {}  | key :: {} | Message :: {}", employeeTopic,
				record.key(), record.value());
	}

//	@KafkaListener(topics = kafkaTopic, groupId = "group1")
//	public void consumeJson(EmployeeVo EmployeeVo) {
//		log.info("Message consume successfully. Result :: topic :: {}  | Message :: {}", kafkaTopic, EmployeeVo);
//	}

}
