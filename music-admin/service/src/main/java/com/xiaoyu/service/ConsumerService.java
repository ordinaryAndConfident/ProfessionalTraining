package com.xiaoyu.service;

import com.xiaoyu.pojo.Consumer;

import java.util.List;

public interface ConsumerService {

    public Consumer login(Consumer consumer);

    public boolean insert(Consumer consumer);

    public boolean deleteConsumer(Integer id);

    public boolean update(Consumer consumer);

    public List<Consumer> selectAll();

    public Consumer selectConsumerByEmail(String email);

    // 根据主键查询
    public Consumer selectById(Integer id);

    /*根据账号修改密码*/
    public boolean changePassWordByAccount(Consumer consumer);

    /*根据账号查询用户*/
    public Consumer selectByAccount(String account);
}
