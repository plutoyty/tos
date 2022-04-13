package cn.wrxdark.modules.goods.mapper;

import cn.wrxdark.modules.goods.entity.dos.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 产品 Mapper 接口
 * @author 刘宇阳
 * @since 2022-03-23
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * @description 商品上架
     * @author 刘宇阳
     */
    @Update("UPDATE tos_goods SET status=1 WHERE id=#{goodsId}")
     void updateGoodsOnShelf(String goodsId);

    /**
     * @description 商品下架
     * @author 刘宇阳
     */
    @Update("UPDATE tos_goods SET status=0 WHERE id=#{goodsId}")
     void updateGoodsOffShelf(String goodsId);
}
