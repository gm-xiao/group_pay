package com.example.grouppay.service.impl;

import com.example.grouppay.domain.Order;
import com.example.grouppay.mapper.OrderMapper;
import com.example.grouppay.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gm
 * @since 2019-05-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
