package cn.wrxdark.modules.activity.service;

import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.activity.entity.dos.Activity;
import cn.wrxdark.modules.activity.entity.dto.ActivityRuleDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 刘宇阳
 * @create 2022/3/26
 * @description
 */
public interface ActivityService extends IService<Activity> {

    /**
     * @description  添加活动，预热商品、活动、库存
     * @author 刘宇阳
     * @param activityRuleDTO 活动+规则对象
     * @throws IllegalAccessException
     */
    void add(ActivityRuleDTO activityRuleDTO) throws IllegalAccessException;

    Activity get(String activityId);

    void delById(String activityId);

    /**
     * @description 开启活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    void start(String activityId);

    /**
     * @description 停止活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    void stop(String activityId);

    /**
     * @description 获取有分页的活动列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    IPage<Activity> listPage(Integer pageNum, Integer pageSize);

    Activity getLatestActivity();
}
