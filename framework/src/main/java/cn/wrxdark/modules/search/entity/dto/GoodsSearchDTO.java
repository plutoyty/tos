package cn.wrxdark.modules.search.entity.dto;


import lombok.Data;

/**
 * 搜索参数实体类
 **/
@Data
public class GoodsSearchDTO  {

    //"关键字")
    private String keyword;

    //"分类")
    private String categoryId;

    //"品牌,可以多选 品牌Id@品牌Id@品牌Id")
    private String brandId;

    //"卖家id，搜索店铺商品的时候使用")
    private String storeId;

}
