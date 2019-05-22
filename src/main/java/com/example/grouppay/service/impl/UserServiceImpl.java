package com.example.grouppay.service.impl;

import com.example.grouppay.domain.User;
import com.example.grouppay.mapper.UserMapper;
import com.example.grouppay.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author gm
 * @since 2019-05-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
