package cn.wrxdark.modules.goods.service.impl;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.goods.entity.dos.Goods;
import cn.wrxdark.modules.goods.mapper.GoodsMapper;
import cn.wrxdark.modules.goods.service.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘宇阳
 * @create 2022/3/23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addOrUpdateGoods(Goods goods) {
        //检查商品信息
        this.check(goods);
        this.saveOrUpdate(goods);
    }

    private void check(Goods goods) {

        //检查活动
        //如果什么没有填  那么后端自动补充  看前端需求
    }

    @Override
    public Goods get(String goodsId) {
        return this.getById(goodsId);
    }

    @Override
    public boolean del(String goodsId) {
        if (this.get(goodsId) == null) {
            throw new ServiceException(ResultCode.GOODS_ERROR);
        }
        return this.removeById(goodsId);
    }

    /**
     * @description 获得有分页的商品列表数据
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页数据个数
     * @return
     */
    @Override
    public IPage<Goods> listPage(int pageNum, int pageSize) {
        IPage<Goods> iPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        //经过MP分页查询将所有的分页(total/结果/页面/条数/xxx)数据封装到iPage对象
        iPage = goodsMapper.selectPage(iPage,queryWrapper);
        return iPage;
    }

    /**
     * @description 商品上架
     * @author 刘宇阳
     * @param goodsId
     */
    @Override
    public void onShelf(String goodsId) {
        goodsMapper.updateGoodsOnShelf(goodsId);
    }

    /**
     * @description 商品下架
     * @author 刘宇阳
     * @param goodsId
     */
    @Override
    public void offShelf(String goodsId) {
        goodsMapper.updateGoodsOffShelf(goodsId);
    }
}
