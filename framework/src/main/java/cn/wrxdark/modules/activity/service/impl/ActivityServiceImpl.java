package cn.wrxdark.modules.activity.service.impl;


import cn.wrxdark.cache.Cache;
import cn.wrxdark.cache.CachePrefix;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.activity.entity.dos.Activity;
import cn.wrxdark.modules.activity.entity.dto.ActivityRuleDTO;
import cn.wrxdark.modules.activity.mapper.ActivityMapper;
import cn.wrxdark.modules.activity.service.ActivityService;
import cn.wrxdark.modules.goods.entity.dos.Goods;
import cn.wrxdark.modules.goods.mapper.GoodsMapper;
import cn.wrxdark.modules.rule.mapper.RuleMapper;
import cn.wrxdark.modules.rule.mapper.entity.dos.Rule;
import cn.wrxdark.util.BeanUtil;
import cn.wrxdark.util.RedisKeyUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 活动业务
 * @author 刘宇阳
 * @since 2022-03-26
 */
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper,Activity>  implements ActivityService {
    @Autowired
    private Cache cache;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RuleMapper ruleMapper;

    /**
     * @description  添加活动，预热商品、活动、库存
     * @author 刘宇阳
     * @param activityRuleDTO 活动+规则对象
     * @throws IllegalAccessException
     */
    @Override
    @Transactional
    public void add(ActivityRuleDTO activityRuleDTO) throws IllegalAccessException {
        Activity activity=new Activity(activityRuleDTO);
        //查询参与活动的商品
        Goods goods=goodsMapper.selectById(activity.getGoodsId());
        if(goods==null){
            throw new ServiceException(ResultCode.GOODS_ERROR);
        }
        if(goods.getStock()<activity.getStock()){
            //库存不足
            throw new ServiceException(ResultCode.GOODS_SKU_QUANTITY_NOT_ENOUGH);
        }
        //活动结束时间早于开始时间
        if(activity.getEndTime().isBefore(activity.getStartTime())){
            throw new ServiceException(ResultCode.ACTIVITY_DURATION_ERROR);
        }
        //数据库插入活动
        activityMapper.insert(activity);
        log.info("成功插入活动"+activity);
        //数据库插入规则
        Rule rule=new Rule(activityRuleDTO);
        ruleMapper.insert(rule);
        //数据库插入活动与规则的关系
        ruleMapper.insertActivityRuleRelation(activity.getId(),rule.getId());
        //删除商品库存
        goods.setStock(goods.getStock()-activity.getStock());
        goodsMapper.updateById(goods);
        log.info("成功删除商品库存");
        //redis插入活动
        //TODO判断时间是否
        Duration duration= Duration.between(activity.getStartTime(),activity.getEndTime());
        Map beanMap= BeanUtil.beanToMap(activity);
        String key= RedisKeyUtil.generateActivityKey(activity.getId());
        cache.putAllHash(key,beanMap);
        cache.expire(key,duration.getSeconds(), TimeUnit.SECONDS);
        log.info("成功预热活动"+activity);
        //redis插入商品
        beanMap= BeanUtil.beanToMap(goods);
        key=RedisKeyUtil.generateGoodsKey(goods.getId(),activity.getId());
        cache.putAllHash(key,beanMap);
        cache.expire(key,duration.getSeconds(), TimeUnit.SECONDS);
        log.info("成功预热商品"+goods);
        //添加库存
        key=RedisKeyUtil.generateStockKey(goods.getId(),activity.getId());
        cache.put(key,activity.getStock(),duration.getSeconds());
        log.info("成功预热库存");
    }

    @Override
    public Activity get(String activityId) {
        Activity activity = Optional.ofNullable(
                activityMapper.selectById(activityId)).orElseThrow(()->new ServiceException(ResultCode.ACTIVITY_NOT_EXIST)
        );
        return activity;
    }

    @Override
    public void delById(String activityId) {
        if(this.removeById(activityId)) {
            throw new ServiceException(ResultCode.ACTIVITY_NOT_EXIST);
        }
    }

    /**
     * @description 开启活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    @Override
    public void start(String activityId) {
        activityMapper.updateToStart(activityId);
        log.info("将id为"+activityId+"的活动开启");
    }

    /**
     * @description 停止活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    @Override
    public void stop(String activityId) {
        activityMapper.updateToStop(activityId);
        log.info("将id为"+activityId+"的活动关闭");
    }

    /**
     * @description 获取有分页的活动列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public IPage<Activity> listPage(Integer pageNum, Integer pageSize) {
        IPage<Activity> iPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
        //经过MP分页查询将所有的分页(total/结果/页面/条数/xxx)数据封装到iPage对象
        iPage = activityMapper.selectPage(iPage,queryWrapper);
        return iPage;
    }

    @Override
    public Activity getLatestActivity() {
        Activity activity=activityMapper.selectLatestOne();
        return activity;
    }
}
