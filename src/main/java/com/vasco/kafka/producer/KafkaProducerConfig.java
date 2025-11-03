package com.vasco.kafka.producer;

import com.vasco.model.HandoverMessage;
import com.vasco.model.ProcessMonitorMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.host}")
    private String bootstrapServers;

    @Value("${kafka.auth}")
    private String securityProtocol;

    @Value("${kafka.username}")
    private String username;

    @Value("${kafka.password}")
    private String password;

/*    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // DNS behavior for multi-IP
        props.put("client.dns.lookup", "use_all_dns_ips");
        // SASL/PLAIN if configured
        props.put("security.protocol", securityProtocol);
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";",
                username, password));
        return props;
    }*/

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }


    // Producer/Template for ProcessMonitorMessage
    @Bean
    public ProducerFactory<String, ProcessMonitorMessage> monitorProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, ProcessMonitorMessage> kafkaTemplateMonitor() {
        return new KafkaTemplate<>(monitorProducerFactory());
    }

    // Producer/Template for HandoverMessage
    @Bean
    public ProducerFactory<String, HandoverMessage> handoverProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, HandoverMessage> kafkaTemplateHandover() {
        return new KafkaTemplate<>(handoverProducerFactory());
    }
}