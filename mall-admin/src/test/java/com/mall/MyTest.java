package com.mall;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.common.core.util.PageUtils;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.util.MenuUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-24-14:48
 **/
@SpringBootTest
public class MyTest {


    @Autowired
    private IUmsRoleService iUmsRoleService;

    @Test
    public void test() {
        System.out.println(PageUtils.buildPageInfo(iUmsRoleService.pageVo(PageUtils.buildPagePlus())));
    }
}
