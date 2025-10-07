package com.vasco.kafka.consumer;

import com.vasco.model.HandoverMessage;
import com.vasco.model.ProcessMonitorMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, ProcessMonitorMessage> consumerFactory() {
        JsonDeserializer<ProcessMonitorMessage> deserializer = new JsonDeserializer<>(ProcessMonitorMessage.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka_test_consumer");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcessMonitorMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProcessMonitorMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

        @Bean
        public ConsumerFactory<String, HandoverMessage> consumerHandoverFactory() {
            JsonDeserializer<HandoverMessage> deserializer = new JsonDeserializer<>(HandoverMessage.class);
            deserializer.addTrustedPackages("*");

            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka_test_consumer");
            configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
            return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, HandoverMessage> kafkaListenerContainerHandoverFactory() {
            ConcurrentKafkaListenerContainerFactory<String, HandoverMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerHandoverFactory());
            return factory;
        }

}