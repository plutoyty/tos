package cn.wrxdark.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author 刘宇阳
 * @create 2022/4/14
 * @description
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProducerArg implements Serializable {
    private static final long serialVersionUID = 1337728300174142127L;
    //库存流水id
    private String stockLogId;

    private String memberId;

    private String goodsId;

    private String activityId;
}
