package cn.wrxdark.modules.upload.service;

import cn.wrxdark.common.entity.vo.ResultMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;

@Service
public interface UploadService {

    public ResultMessage uploadImage(MultipartFile zipFile) throws UnknownHostException;

}

