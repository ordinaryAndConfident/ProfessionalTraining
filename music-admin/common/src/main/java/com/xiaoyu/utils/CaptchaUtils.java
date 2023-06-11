package com.xiaoyu.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

/**
 * @author YuSir
 * @version 1.0
 * @description: 图形验证码
 * @date 2022/11/13 15:21
 */
public class CaptchaUtils{

    //定义图形验证码的长和宽
    private static LineCaptcha lineCaptcha;

    public static String createCode() {
        lineCaptcha = CaptchaUtil.createLineCaptcha(200, 40);
        // 验证码保存目录
        String realPath = System.getProperty("user.dir")+System.getProperty("file.separator")+ "code"
                +System.getProperty("file.separator");
        realPath += "line.png";
        try {
            // 先判断前一个验证码是否存在，如果存在则删除
            FileUtils.deleteDiskFile(realPath);
            //图形验证码写出，可以写出到文件，也可以写出到流
            lineCaptcha.write(realPath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //验证图形验证码的有效性，返回boolean值
        return realPath;
    }

    // 判断用户输入的验证码是否正确
    public static boolean verifyCode(String code){
        try {
            boolean verify = lineCaptcha.verify(code);
        }catch (Exception e){
            //throw new RuntimeException(e);
            return false;
        }
        return lineCaptcha.verify(code);
    }
}
