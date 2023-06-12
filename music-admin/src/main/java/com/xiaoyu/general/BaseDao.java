package com.xiaoyu.general;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 22:30
 */

public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}
