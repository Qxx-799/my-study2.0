package com.qxx.thirdservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter getMessageConverter(){
        return new SimpleMessageConverter();
    }


    // 声明正常叫花鸡
    @Bean
    public Exchange businessExchange(){
        return new DirectExchange("business_exchange");
    }

    // 声明正常队列
    @Bean
    public Queue businessQueue(){
        Map<String,Object> param = new HashMap<>();
        param.put("x-dead-letter-exchange","dead_letter_exchange"); // 绑定死性交换机
        param.put("x-dead-letter-routing-key","dead-letter");
        param.put("x-message-ttl",10000);  // 过期时间 10s
        return QueueBuilder.durable("business_queue").withArguments(param).build();
    }

    // 绑定正常队列和正常交换机
    @Bean
    public Binding businessBind(@Qualifier(value = "businessQueue") Queue queue,
                                @Qualifier(value = "businessExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("business").noargs();
    }


    // 声明死性交换机
    @Bean
    public Exchange deadLetterExchange(){
        return new DirectExchange("dead_letter_exchange");
    }

    // 声明死性队列
    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable("dead_letter_queue").build();
    }

    //绑定死性队列和死性交换机
    @Bean
    public Binding deadLetterBind(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("dead-letter").noargs();
    }

}
