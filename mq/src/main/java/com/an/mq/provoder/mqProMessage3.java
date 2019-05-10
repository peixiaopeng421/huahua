package com.an.mq.provoder;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "qa")
//当如果队列中有消息产生的时候 则触发监听（消费者）
public class mqProMessage3 {

    /**
     * 直接模式
     * @param messsage
     */
    @RabbitHandler
    public  void showMessage(String messsage){

        System.out.println(" article.#  消费者 大乔 回复："+messsage);
    }

}