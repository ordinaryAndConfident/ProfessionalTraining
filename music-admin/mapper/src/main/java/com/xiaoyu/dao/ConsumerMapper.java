package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper extends BaseDao<Consumer> {

    public Consumer login(Consumer consumer);

    public int insert(Consumer consumer);

    public int deleteConsumer(Integer id);

    public int update(Consumer consumer);

    public List<Consumer> selectAll();

    public Consumer selectConsumerByEmail(String email);

    // 根据主键查询
    public Consumer selectById(Integer id);

    /*根据账号修改密码*/
    public int changePassWordByAccount(Consumer consumer);

    /*根据账号查询用户*/
    public Consumer selectByAccount(String account);
}
