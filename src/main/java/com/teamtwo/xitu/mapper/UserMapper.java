package com.teamtwo.xitu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamtwo.xitu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.mapper
 **/
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
