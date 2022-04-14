package cn.wrxdark.controller.depositRecord;

import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import cn.wrxdark.modules.depositRecord.service.DRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘宇阳
 * @create 2022/4/10
 * @description 存款记录controller
 */
@RestController
@RequestMapping("/d-record")
@Api(tags = "存款记录接口")
@Slf4j
public class DRecordController {
    @Autowired
    private DRecordService dRecordService;

    /**·
     * @description 获得存款记录的分页列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @Operation(summary = "获得存款记录的分页列表")
    @GetMapping("/list")
    public ResultMessage<IPage<DepositRecord>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){
        IPage<DepositRecord> depositRecordIPage=dRecordService.getList(pageNum,pageSize);
        return ResultUtil.data(depositRecordIPage);
    }
}
