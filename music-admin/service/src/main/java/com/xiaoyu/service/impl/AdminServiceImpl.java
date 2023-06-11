package com.xiaoyu.service.impl;

import com.xiaoyu.dao.AdminMapper;
import com.xiaoyu.pojo.Admin;
import com.xiaoyu.service.AdminService;
import com.xiaoyu.utils.MD5Utils;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 22:43
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResultVO login(Admin admin) {
        //1.根据用户查询，这个用户是否存在
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", admin.getAccount());
        criteria.andEqualTo("password", MD5Utils.md5(admin.getPassword()));
        List<Admin> admins = adminMapper.selectByExample(example);

        if (admins.size() == 0){
            return new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE,"登录失败，该邮箱不存在!",null);
        }else {
            return new ResultVO(ResStatus.OK,"登录成功",admins.get(0));
        }
    }
}
