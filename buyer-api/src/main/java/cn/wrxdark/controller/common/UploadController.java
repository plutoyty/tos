package cn.wrxdark.controller.common;


import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import cn.wrxdark.modules.upload.service.UploadService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;

/**
 * 文件上传接口
 *
 */
@Slf4j
@RestController
@RequestMapping("/common/upload")
@Api(tags = "文件上传接口")
public class UploadController {


    @Autowired
    UploadService uploadService;

    /**
     * 文件上传
     * @param file
     * @param base64
     * @return
     */
    @PostMapping(value = "/file")
    public ResultMessage<Object> upload(MultipartFile file,
                                        String base64) {
        return ResultUtil.error(ResultCode.API_NOT_IMPLEMENT);
    }

    @PostMapping(value = "/image")
    public ResultMessage<Object> uploadImage(@RequestParam("file") MultipartFile file) throws UnknownHostException {
        return uploadService.uploadImage(file);
    }

}
