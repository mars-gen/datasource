package com.example.pan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pan.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
