package com.qxx.thirdservice.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;



    public String sendMessage(String routingKey, Object message){
        rabbitTemplate.convertAndSend("delayed-exchange", routingKey, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDelay(10000);
                return message;
            }
        });
        return "消息发送成功!";
    }



    @RabbitListener(queues = "test_01")
    private void consumer(String msg, Channel channel, Message message) throws IOException {
        System.out.println("接收到到消息为：" + msg);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        this.msg = msg;
    }

    private String msg;
    public String getMessage(){
        return msg;
    }
}
