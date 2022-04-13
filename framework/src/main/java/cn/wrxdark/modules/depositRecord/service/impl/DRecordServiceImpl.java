package cn.wrxdark.modules.depositRecord.service.impl;

import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import cn.wrxdark.modules.depositRecord.mapper.DRecordMapper;
import cn.wrxdark.modules.depositRecord.service.DRecordService;
import cn.wrxdark.modules.goods.entity.dos.Goods;
import cn.wrxdark.modules.member.entity.dos.Member;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 刘宇阳
 * @create 2022/4/11
 * @description 描述
 */
@Service
@Slf4j
public class DRecordServiceImpl implements DRecordService {
    @Autowired
    private DRecordMapper dRecordMapper;

    /**
     * @description 获取有分页的存款记录列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public IPage<DepositRecord> getList(Integer pageNum, Integer pageSize) {
        IPage<DepositRecord> iPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<DepositRecord> queryWrapper = new LambdaQueryWrapper<>();
        //经过MP分页查询将所有的分页(total/结果/页面/条数/xxx)数据封装到iPage对象
        iPage = dRecordMapper.selectPage(iPage,queryWrapper);
        log.info("获得了存款记录列表");
        return iPage;
    }
}