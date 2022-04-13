package cn.wrxdark.mq.localListener;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.modules.stockLog.entity.dos.StockLog;
import cn.wrxdark.modules.stockLog.mapper.StockLogMapper;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 刘宇阳
 * @create 2022/3/28
 * @description producer端执行本地事务和监听回查操作
 */
@Slf4j
@RocketMQTransactionListener()
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Autowired
    private StockLogMapper stockLogMapper;

    /**
     * @description 将消息转map
     * @author 刘宇阳
     * @param message
     * @return
     */
    private Map<String,Object> msgToMap(Message message) {
        String str = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        Map<String,Object> map=(Map) JSON.parse(str);
        return map;
    }

    /**
     * 执行本地事务(在前面已经执行完了)
     * @param msg
     * @param arg
     * @return
     */
    @SneakyThrows
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 如果出现网络问题，则broker将不会收到COMMIT类型的消息
     * 就会定时回查，会进入这个方法
     * @param msg
     * @return
     */
    @SneakyThrows
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //轮询回查
        log.info("============== 查询流水记录是否存在");
        Map<String,Object> map=msgToMap(msg);
        String stockLogId= (String) map.get("stockLogId");
        StockLog stockLogInSql = stockLogMapper.selectById(stockLogId);
        if(stockLogInSql==null){
            //依然找不到，未知状态
            return RocketMQLocalTransactionState.UNKNOWN;
        }else{
            //找到了，说明消息可以提交了
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}