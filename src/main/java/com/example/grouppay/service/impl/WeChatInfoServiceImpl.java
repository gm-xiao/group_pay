package com.example.grouppay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.grouppay.domain.WeChatInfo;
import com.example.grouppay.mapper.WeChatInfoMapper;
import com.example.grouppay.service.WeChatInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gm
 * @since 2019-05-23
 */
@Service
public class WeChatInfoServiceImpl extends ServiceImpl<WeChatInfoMapper, WeChatInfo> implements WeChatInfoService {

    public QueryWrapper<WeChatInfo> structure(WeChatInfo weChatInfo){
        QueryWrapper<WeChatInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(weChatInfo.getUid())){
            queryWrapper.eq("uid", weChatInfo.getUid());
        }
        return queryWrapper;
    }


    @Override
    public WeChatInfo findByUid(String uid) {
        QueryWrapper<WeChatInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return this.getOne(queryWrapper);
    }
}
