package cn.wrxdark.modules.depositRecord.service;

import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author 刘宇阳
 * @create 2022/4/11
 * @description
 */
public interface DRecordService {
    /**
     * @description 获取有分页的存款记录列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    IPage<DepositRecord> getList(Integer pageNum, Integer pageSize);
}
