package cn.wrxdark.modules.goods.service;

import cn.wrxdark.modules.goods.entity.dos.Goods;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 刘宇阳
 * @create 2022/3/23
 */

public interface GoodsService extends IService<Goods> {

    void addOrUpdateGoods(Goods goods);

    Goods get(String goodsId);

    boolean del(String goodsId);

    /**
     * @description 获得有分页的商品列表数据
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页数据个数
     * @return
     */
    IPage<Goods> listPage(int pageNum, int pageSize);

    /**
     * @description 商品上架
     * @author 刘宇阳
     * @param goodsId
     */
    void onShelf(String goodsId);

    /**
     * @description 商品下架
     * @author 刘宇阳
     * @param goodsId
     */
    void offShelf(String goodsId);
}
