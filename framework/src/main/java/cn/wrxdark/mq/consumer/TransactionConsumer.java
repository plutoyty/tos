package cn.wrxdark.mq.consumer;

import cn.wrxdark.modules.activity.mapper.ActivityMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 刘宇阳
 * @create 2022/3/27
 * @description mq体系中的消费者
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "seckill", consumerGroup = "tx-consumer-group")
public class TransactionConsumer implements RocketMQListener<String>{
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 接收到消息会进入这个方法，具体的消费
     * @param message 消息的字符串形式，需反序列化
     */
    @Override
    public void onMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String goodsId=jsonObject.getString("goodsId");
        String activityId=jsonObject.getString("activityId");
        log.info("扣减数据库中的库存,活动id为{}",activityId);
        activityMapper.decrStock(activityId,goodsId,1);
    }
}