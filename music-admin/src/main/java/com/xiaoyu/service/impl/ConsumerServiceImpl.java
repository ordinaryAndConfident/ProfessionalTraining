package com.xiaoyu.service.impl;

import com.xiaoyu.dao.ConsumerMapper;
import com.xiaoyu.pojo.Consumer;
import com.xiaoyu.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: 前端用户
 * @date 2022/11/6 14:38
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public Consumer login(Consumer consumer) {
        return consumerMapper.login(consumer);
    }

    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    @Override
    public boolean deleteConsumer(Integer id) {
        return consumerMapper.deleteConsumer(id) > 0;
    }

    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer) > 0;
    }

    @Override
    public List<Consumer> selectAll() {
        return consumerMapper.selectAll();
    }

    @Override
    public Consumer selectConsumerByEmail(String email) {
        return consumerMapper.selectConsumerByEmail(email);
    }

    @Override
    public Consumer selectById(Integer id) {
        return consumerMapper.selectById(id);
    }

    @Override
    public boolean changePassWordByAccount(Consumer consumer) {
        return consumerMapper.changePassWordByAccount(consumer) > 0;
    }

    @Override
    public Consumer selectByAccount(String account) {
        return consumerMapper.selectByAccount(account);
    }


}
