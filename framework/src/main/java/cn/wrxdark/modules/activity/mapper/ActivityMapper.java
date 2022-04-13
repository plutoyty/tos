package cn.wrxdark.modules.activity.mapper;

import cn.wrxdark.modules.activity.entity.dos.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 刘宇阳
 * @since 2022-03-26
 */
@Mapper
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {
    /**
     * @description 扣减商品库存
     * @author 刘宇阳
     * @param activityId 活动id
     * @param goodsId 商品id
     * @param amount 要扣减的数量
     */
    @Update("UPDATE tos_activity SET rest_stock=rest_stock-#{amount} WHERE id=#{activityId} AND goods_id=#{goodsId}")
    void decrStock(String activityId,String goodsId,int amount);

    /**
     * @description 停止活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    @Update("UPDATE tos_activity SET status=0 WHERE id=#{activityId}")
    void updateToStop(String activityId);

    /**
     * @description 开启活动
     * @author 刘宇阳
     * @param activityId 活动id
     */
    @Update("UPDATE tos_activity SET status=1 WHERE id=#{activityId}")
    void updateToStart(String activityId);
}
