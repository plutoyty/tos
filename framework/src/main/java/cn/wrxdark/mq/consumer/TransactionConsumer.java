package cn.wrxdark.mq.consumer;

import cn.wrxdark.common.entity.enums.StatusEnum;
import cn.wrxdark.modules.activity.mapper.ActivityMapper;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import cn.wrxdark.modules.depositRecord.mapper.DRecordMapper;
import cn.wrxdark.modules.stockLog.mapper.StockLogMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    private DRecordMapper dRecordMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private StockLogMapper stockLogMapper;

    /**
     * 接收到消息会进入这个方法，具体的消费
     * @param message 消息的字符串形式，需反序列化
     */
    @Override
    public void onMessage(String message) {
        Map<String,Object> map=(Map) JSON.parse(message);
        String stockLogId= (String) map.get("stockLogId");
        DepositRecord dr= (DepositRecord) map.get("depositRecord");
        log.info("扣减数据库中的库存");
        activityMapper.decrStock(dr.getActivityId(),dr.getGoodsId(),1);
        log.info("修改库存流水状态为成功");
        stockLogMapper.updateStatus(stockLogId,StatusEnum.STOCK_LOG_SUCCESS.statusCode());
        log.info("生成存款记录");
        dRecordMapper.insert(dr);
    }
}