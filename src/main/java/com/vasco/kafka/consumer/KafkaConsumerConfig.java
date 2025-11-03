package com.vasco.kafka.consumer;

import com.vasco.model.HandoverMessage;
import com.vasco.model.ProcessMonitorMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${kafka.host}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.auth}")
    private String securityProtocol;

    @Value("${kafka.username}")
    private String username;

    @Value("${kafka.password}")
    private String password;

/*    private Map<String, Object> baseConsumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // DNS behavior for multi-IP
        props.put("client.dns.lookup", "use_all_dns_ips");
        // SASL/PLAIN
        props.put("security.protocol", securityProtocol);
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";",
                username, password));
        return props;
    }*/


    private Map<String, Object> baseConsumerProps() {
        JsonDeserializer<ProcessMonitorMessage> deserializer = new JsonDeserializer<>(ProcessMonitorMessage.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka_test_consumer");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return configProps;
    }

    @Bean
    public ConsumerFactory<String, ProcessMonitorMessage> consumerFactory() {
        JsonDeserializer<ProcessMonitorMessage> deserializer = new JsonDeserializer<>(ProcessMonitorMessage.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> configProps = baseConsumerProps();
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcessMonitorMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProcessMonitorMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setAutoStartup(false); // avoid startup failure when broker not reachable
        factory.getContainerProperties().setMissingTopicsFatal(false);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, HandoverMessage> consumerHandoverFactory() {
        JsonDeserializer<HandoverMessage> deserializer = new JsonDeserializer<>(HandoverMessage.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> configProps = baseConsumerProps();
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, HandoverMessage> kafkaListenerContainerHandoverFactory() {
        ConcurrentKafkaListenerContainerFactory<String, HandoverMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerHandoverFactory());
        factory.setAutoStartup(false); // avoid startup failure when broker not reachable
        factory.getContainerProperties().setMissingTopicsFatal(false);
        return factory;
    }

}