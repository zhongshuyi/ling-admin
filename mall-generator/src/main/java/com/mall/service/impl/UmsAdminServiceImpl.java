package com.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.model.UmsAdmin;
import com.mall.mapper.UmsAdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-05
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IService<UmsAdmin> {

}
