package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.mapper.UmsAdminMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.vo.UserVo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@Service
@Slf4j
public class UmsAdminServiceImpl
        extends ServicePlusImpl<UmsAdminMapper, UmsAdmin, UserVo>
        implements IUmsAdminService {

    private static final long serialVersionUID = -6767396404407827925L;

    @Override
    public final List<UmsAdmin> getUserListByRoleId(final Long roleId) {
        return getBaseMapper().getUserListByRoleId(roleId);
    }

    @Override
    public final PagePlus<UmsAdmin, UserVo> getUserListPage(
            final PagePlus<UmsAdmin, UserVo> pagePlus, final UserBo bo
    ) {
        PageHelper.startPage((int) pagePlus.getCurrent(), (int) pagePlus.getSize());
        List<UmsAdmin> list = getBaseMapper().queryUserList(bo);
        PageInfo<UmsAdmin> pageInfo = new PageInfo<>(list);
        pagePlus.setRecords(pageInfo.getList());
        pagePlus.setRecordsVo(BeanUtil.copyToList(pageInfo.getList(), UserVo.class));
        pagePlus.setTotal(pageInfo.getTotal());
        return pagePlus;
    }

    @Override
    public final void validEntityBeforeSave(final UmsAdmin umsAdmin) {
        super.validEntityBeforeSave(umsAdmin);
        if (!checkUserNameUnique(umsAdmin)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
    }

    /**
     * 检查用户名是否唯一.
     *
     * @param umsAdmin 用户信息
     * @return true就是唯一的
     */
    public Boolean checkUserNameUnique(final UmsAdmin umsAdmin) {
        UmsAdmin user =
                getOne(Wrappers.<UmsAdmin>lambdaQuery()
                        .eq(UmsAdmin::getUsername, umsAdmin.getUsername())
                        .last("limit 1"));
        return user != null && !umsAdmin.getId().equals(user.getId());
    }
}
