package com.xiaoyu.controller;

import com.xiaoyu.pojo.Admin;
import com.xiaoyu.service.AdminService;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 21:55
 */
@RestController
@RequestMapping("/user")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResultVO login(Admin admin) {
        return adminService.login(admin);
    }
}
