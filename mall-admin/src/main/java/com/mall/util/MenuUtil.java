package com.mall.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.model.UmsMenu;
import com.mall.vo.RouterMeta;
import com.mall.vo.RouterVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-09-17-9:39
 **/
public class MenuUtil {


    /**
     * 获取处理好后的路由
     *
     * @param menus 从数据库中直接查出来的菜单信息
     * @return 处理好的
     */
    public static List<RouterVo> getMenus(List<UmsMenu> menus) {
        return setRedirect(buildMenus(menus, 0L));
    }

    /**
     * 构建菜单树
     *
     * @param menus 菜单
     * @return 菜单树
     */
    public static List<RouterVo> buildMenus(List<UmsMenu> menus, Long parentId) {

        LinkedList<RouterVo> routers = new LinkedList<>();

        if (menus == null || menus.isEmpty()) {
            return null;
        }

        for (UmsMenu menu : menus) {
            RouterVo router = BeanUtil.toBean(menu, RouterVo.class);
            router.setName(StringUtils.capitalize(router.getPath()));
            if (menu.getParentId().equals(0L)) {
                router.setPath("/" + router.getPath());
            }


            RouterMeta meta = BeanUtil.toBean(menu, RouterMeta.class);

            meta.setIgnoreKeepAlive(menu.getIgnoreKeepAlive().equals(0));
            meta.setAffix(menu.getAffix().equals(0));
            meta.setHideBreadcrumb(menu.getHideBreadcrumb().equals(0));
            meta.setHideChildrenInMenu(menu.getHideChildrenInMenu().equals(0));
            meta.setHideTab(menu.getHideTab().equals(0));
            meta.setHideMenu(menu.getHideMenu().equals(0));

            router.setMeta(meta);

            if (menu.getParentId().equals(parentId)) {
                routers.add(router);
            } else {
                continue;
            }
            router.setChildren(buildMenus(menus, menu.getId()));
            if (CollUtil.isEmpty(router.getChildren())){
                router.setChildren(null);
            }
        }
        return routers;
    }

    /**
     * 设置重定向路由
     *
     * @param routers 路由
     * @return 设置好后的路由
     */
    public static List<RouterVo> setRedirect(List<RouterVo> routers) {
        for (RouterVo router : routers) {
            StringBuilder redirect = new StringBuilder(router.getPath());
            RouterVo i = router;
            while (CollUtil.isNotEmpty(i.getChildren())) {
                i = i.getChildren().get(0);
                redirect.append("/").append(i.getPath());
            }
            if (CollUtil.isNotEmpty(router.getChildren())) {
                router.setRedirect(redirect.toString());
            }
        }
        return routers;
    }
}
