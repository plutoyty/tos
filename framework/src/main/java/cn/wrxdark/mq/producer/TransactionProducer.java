package cn.wrxdark.mq.producer;

import cn.wrxdark.mq.entity.ConsumerArg;
import cn.wrxdark.mq.entity.ProducerArg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author 刘宇阳
 * @create 2022/3/27
 * @description
 */
@Slf4j
@Component
public class TransactionProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * @description 发送事务消息
     * @author 刘宇阳
     * @return
     */
    public boolean produce(Message<ConsumerArg> message, ProducerArg arg) {
        log.info("========发送消息中=========");
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("seckill", message, arg);
        log.info("========发送完成=========");
        return sendResult.getSendStatus().equals(SendStatus.SEND_OK);
    }
}