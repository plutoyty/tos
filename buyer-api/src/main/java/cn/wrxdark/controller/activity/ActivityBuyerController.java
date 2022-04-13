package cn.wrxdark.controller.activity;


import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.activity.entity.dos.Activity;
import cn.wrxdark.modules.activity.service.ActivityService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 买家端，活动接口
 */
@RestController
@RequestMapping("/buyer/activity")
@Api(tags = "买家端，活动接口")
@Slf4j
public class ActivityBuyerController {
    @Autowired
    private ActivityService activityService;

    /**
     * 添加活动
     * @param activity 活动对象
     * @return
     * @throws IllegalAccessException
     */
    @PostMapping
    public ResultMessage add(@RequestBody Activity activity) throws IllegalAccessException {
        activityService.add(activity);
        return ResultUtil.success();
    }

    @DeleteMapping("{activityId}")
    public ResultMessage del(@PathVariable String activityId){
        activityService.delById(activityId);
        return ResultUtil.success();
    }

    @GetMapping("{activityId}")
    public ResultMessage<Activity> get(@PathVariable String activityId){
        Activity activity = activityService.get(activityId);
        return ResultUtil.data(activity);
    }


    /**
     * @description 获得商品的分页列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/list")
    public ResultMessage<IPage<Activity>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){
        IPage<Activity> ipage=activityService.listPage(pageNum,pageSize);
        log.info("获取活动列表成功");
        return ResultUtil.data(ipage);
    }

    /**
     * @description 开启活动
     * @author 刘宇阳
     * @param activityId 活动id
     * @return
     */
    @PutMapping("/start/{activityId}")
    public ResultMessage startActivity(
            @PathVariable("activityId")  String activityId
    ){
        activityService.start(activityId);
        return ResultUtil.success();
    }

    /**
     * @description 关闭活动
     * @author 刘宇阳
     * @param activityId 活动id
     * @return
     */
    @PutMapping("/stop/{activityId}")
    public ResultMessage stopActivity(
            @PathVariable("activityId")  String activityId
    ){
        activityService.stop(activityId);
        return ResultUtil.success();
    }
}
