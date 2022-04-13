package cn.wrxdark.common.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 前后端交互VO
 *
 */
@Data
public class ResultMessage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */

    private boolean success;

    /**
     * 消息
     */
    @ApiModelProperty("消息")
    private String message;

    /**
     * 返回代码
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    @ApiModelProperty("结果")
    private T result;
}
