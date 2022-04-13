package cn.wrxdark.mq.entity;

import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 刘宇阳
 * @create 2022/3/29
 * @description 放入消息中的对象
 */
@Data
@AllArgsConstructor
@ToString
public class MQMessage implements Serializable {
    private static final long serialVersionUID = 1237728300174142127L;
    //库存流水id
    private String stockLogId;
    //存款记录
    private DepositRecord depositRecord;
}