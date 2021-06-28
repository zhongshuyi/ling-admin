package com.mall.service;

import com.mall.model.UmsAdmin;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-28-16:45
 **/
public interface UsmAdminService {

    List<UmsAdmin> selectAll();

    int insert(UmsAdmin umsAdmin);
}
