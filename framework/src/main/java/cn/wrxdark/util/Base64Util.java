package cn.wrxdark.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author 刘宇阳
 * @create 2022/4/12
 * @description Base64工具类
 * @description
 */
public class Base64Util {
    /**
     * @description BufferedImage 编码转换为 base64
     * @param bufferedImage
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/jpg;base64," + png_base64;
    }
}
