package com.xiaoyu.controller;

import com.xiaoyu.pojo.Consumer;
import com.xiaoyu.service.ConsumerService;
import com.xiaoyu.utils.CaptchaUtils;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author YuSir
 * @version 1.0
 * @description: 前端用户
 * @date 2022/11/6 14:42
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @PostMapping("/createCode")
    public ResultVO createCode(){
        CaptchaUtils.createCode();
        String path = "/code/line.png";
        return new ResultVO(ResStatus.OK,"验证码",path);
    }

    @PostMapping("/register")
    public ResultVO register(Consumer consumer,String code){
        boolean verifyCode = CaptchaUtils.verifyCode(code);
        if (verifyCode) {
            Consumer cons = consumerService.selectConsumerByEmail(consumer.getEmail());
            if (cons != null){
                CaptchaUtils.createCode();
                return new ResultVO(ResStatus.NO,"用户已存在!",null);
            }
            consumer.setAvatar("images/avatar/default_avatar.jpg");
            boolean insert = consumerService.insert(consumer);
            if (insert){
                CaptchaUtils.createCode();
                return new ResultVO(ResStatus.OK,"注册成功",insert);
            }else {
                CaptchaUtils.createCode();
                return new ResultVO(ResStatus.NO,"注册失败",null);
            }
        }else {
            CaptchaUtils.createCode();
            return new ResultVO(ResStatus.NO,"验证码错误",null);
        }
    }

    @GetMapping("/login")
    public ResultVO consumerLogin(Consumer consumer){
        Consumer user = consumerService.login(consumer);
        if (user != null){
            user.setPassword("");
            return new ResultVO(ResStatus.OK,"欢迎回来!",user);
        }else {
            return new ResultVO(ResStatus.NO,"登录失败,账号或密码错误!",null);
        }
    }

    @PostMapping("/add")
    public ResultVO addConsumer(Consumer consumer) {
        consumer.setAvatar("images/avatar/avatar.jpg");
        boolean insert = consumerService.insert(consumer);
        if (insert) {
            return new ResultVO(ResStatus.OK, "添加成功!", insert);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/delete")
    public ResultVO delete(Integer id) {
        boolean i = consumerService.deleteConsumer(id);
        if (i) {
            return new ResultVO(ResStatus.OK, "删除成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @PutMapping("/update")
    public ResultVO update(Consumer consumer) {
        if (consumer.getPassword() == null || consumer.getPassword().equals("")){
            consumer.setPassword(null);
        }
        boolean i = consumerService.update(consumer);
        if (i) {
            return new ResultVO(ResStatus.OK, "更新成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "更新失败!", null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAll() {
        List<Consumer> consumers = consumerService.selectAll();
        if (consumers.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", consumers);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byAccount")
    public ResultVO getByName(String account) {
        Consumer consumer = consumerService.selectByAccount(account);
        if (consumer != null) {
            return new ResultVO(ResStatus.OK, "查询成功", consumer);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byId")
    public ResultVO getById(Integer id) {
        Consumer consumer = consumerService.selectById(id);
        consumer.setPassword("");
        if (consumer != null) {
            return new ResultVO(ResStatus.OK, "查询成功", consumer);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @PutMapping("/change/password")
    public ResultVO changePassWord(Consumer consumer) {
        boolean b = consumerService.changePassWordByAccount(consumer);
        if (b) {
            return new ResultVO(ResStatus.OK, "修改成功", b);
        } else {
            return new ResultVO(ResStatus.NO, "修改失败!", null);
        }
    }

    @PostMapping("/update/avatar")
    public ResultVO upload(@RequestParam("file") MultipartFile avatarFile,@RequestParam("id") Integer id) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "文件上传失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "avatar"
                + System.getProperty("file.separator");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "images/avatar/" + fileName;
        try {
            avatarFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvatar(relativePath);

            boolean i = consumerService.update(consumer);
            if (i) {
                return new ResultVO(ResStatus.OK, "文件上传成功", relativePath);
            } else {
                return new ResultVO(ResStatus.NO, "文件上传失败", null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
