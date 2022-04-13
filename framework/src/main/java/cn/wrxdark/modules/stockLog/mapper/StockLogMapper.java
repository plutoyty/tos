package cn.wrxdark.modules.stockLog.mapper;

import cn.wrxdark.modules.stockLog.entity.dos.StockLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @description 库存流水的mapper
 * @author 刘宇阳
 * @since 2022-03-28
 */
@Mapper
@Repository
public interface StockLogMapper extends BaseMapper<StockLog> {
    /**
     * 修改流水状态，从0->1，即该库存已在数据库中修改
     * @param id
     * @param status
     */
    @Update("UPDATE stock_log SET status=#{status}  WHERE id=#{id}")
    void updateStatus(String id,int status);
}
