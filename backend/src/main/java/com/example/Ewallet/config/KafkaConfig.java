package com.example.Ewallet.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic1(){
        return TopicBuilder.name("email-verification-topic").build();
    }

    @Bean
    public NewTopic topic2(){
        return TopicBuilder.name("email-credit-topic").build();
    }

    @Bean
    public NewTopic topic3(){
        return TopicBuilder.name("email-debit-topic").build();
    }

    @Bean
    public NewTopic topic4(){
        return TopicBuilder.name("email-recharge-topic").build();
    }

    @Bean
    public NewTopic topic5(){
        return TopicBuilder.name("email-cashback-topic").build();
    }

    @Bean
    public NewTopic topic6(){
        return TopicBuilder.name("email-withdraw-topic").build();
    }
}
