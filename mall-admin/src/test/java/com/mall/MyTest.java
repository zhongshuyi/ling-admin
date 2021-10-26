package com.mall;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.service.IUmsMenuService;
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
    private IUmsMenuService umsMenuService;

    @Test
    public void test() {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("title");

        List<Tree<Long>> treeNodes = TreeUtil.build(umsMenuService.selectMenuListAll(), 0L, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getOrderNo());
                    tree.setName(treeNode.getTitle());
                });

        System.out.println(JSONUtil.toJsonStr(treeNodes));
    }
}
