package cn.wrxdark.modules.upload.service.impl;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Value("${server.port}")
    private String ip;

    @Override
    public ResultMessage uploadImage(MultipartFile file) throws UnknownHostException {
        if (!file.isEmpty()) {
            String originPath = file.getOriginalFilename();
            String suffix = originPath.substring(originPath.lastIndexOf("."));
            String name = UUID.randomUUID() + suffix;
            File file1 = new File("");
            String fileName = null;
            try {
                fileName = file1.getCanonicalPath() + "/data/image/" + name;
                File file2=new File(fileName);
                if (!file2.getParentFile().exists()) {
                    log.info("该文件夹不存在，正在创建中");
                    //创建上级目录
                    file2.getParentFile().mkdirs();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fileName != null;
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(fileName));
                System.out.println(fileName);
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error(ResultCode.OSS_EXCEPTION_ERROR);
            }
            InetAddress address = InetAddress.getLocalHost();
            String addr=address.toString();
            addr=addr.substring(addr.lastIndexOf("/")+1);
            addr+=":"+ip;
            // 获取本机 IP 地址
            return ResultUtil.data("http://"+addr+"/image/"+name);
        } else {
            return ResultUtil.error(ResultCode.OSS_EXCEPTION_ERROR);
        }
    }
}
