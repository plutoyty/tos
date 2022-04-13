package cn.wrxdark.controller.goods;


import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.goods.entity.dos.Goods;
import cn.wrxdark.modules.goods.service.GoodsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 买家端，商品接口
 */
@RestController
@RequestMapping("/buyer/goods")
@Api(tags = "买家端，商品接口")
@Slf4j
public class GoodsBuyerController {

    @Autowired
    private GoodsService goodsService;


    @PostMapping
    public ResultMessage add(@RequestBody Goods goods){
        goodsService.addOrUpdateGoods(goods);
        log.info("添加商品成功");
        return ResultUtil.success();
    }

//    @GetMapping("/{goodsId}")
//    public ResultMessage<Goods> get(@PathVariable String goodsId){
//        Goods goods=goodsService.get(goodsId);
//        log.info("获取商品成功");
//        return ResultUtil.data(goods);
//    }

    /**
     * @description 获得商品的分页列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/list")
    public ResultMessage<IPage<Goods>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
            ){
        IPage<Goods> ipage=goodsService.listPage(pageNum,pageSize);
        log.info("获取商品列表成功");
        return ResultUtil.data(ipage);
    }


    @DeleteMapping("/{goodsId}")
    public ResultMessage del(@PathVariable String goodsId){
        goodsService.del(goodsId);
        log.info("删除商品成功");
        return ResultUtil.success();
    }

    /**
     * @description 上架
     * @param goodsId 商品id
     * @author  刘宇阳
     * @return
     */
    @PutMapping("/on-shelf/{goodsId}")
    public ResultMessage onShelf(
            @PathVariable("goodsId") String goodsId
    ){
        goodsService.onShelf(goodsId);
        return ResultUtil.success();
    }

    /**
     * @description 下架
     * @author 刘宇阳
     * @param goodsId 商品id
     * @return
     */
    @PutMapping("/off-shelf/{goodsId}")
    public ResultMessage offShelf(
            @PathVariable("goodsId") String goodsId
    ){
        goodsService.offShelf(goodsId);
        return ResultUtil.success();
    }
}