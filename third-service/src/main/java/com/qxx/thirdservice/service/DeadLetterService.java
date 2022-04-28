package com.qxx.thirdservice.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class DeadLetterService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String producer() throws InterruptedException {
        rabbitTemplate.convertAndSend("business_exchange","business","订单超时未支付！");
        int i = 10;
        for (int j = 1; j <= i; j++) {
            System.out.println("倒计时" + j);
            TimeUnit.SECONDS.sleep(1);
        }
        return "消息发送成功！";
    }


    @RabbitListener(queues = "dead_letter_queue")
    public void consumer(Message message, String body, Channel channel) throws InterruptedException, IOException {
        msg = body;
        System.out.println("死性队列接收到消息：" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

    }

    String msg = "";

    public String getMsg(){
        return msg;
    }

}
