package com.mall.system.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.vo.MenuVo;
import com.mall.system.vo.RouterMeta;
import com.mall.system.vo.RouterVo;

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
    public static List<RouterVo> getRouter(List<UmsMenu> menus) {
        return setRedirect(buildRouters(menus, 0L));
    }

    /**
     * 构建菜单树
     * @param menus 菜单列表
     * @return 处理后菜单树
     */
    public static List<MenuVo> getMenuList(List<UmsMenu> menus){
        return buildMenus(menus,0L);
    }

    /**
     * 构建路由树
     *
     * @param menus    菜单
     * @param parentId 父id
     * @return 菜单树
     */
    private static List<RouterVo> buildRouters(List<UmsMenu> menus, Long parentId) {

        LinkedList<RouterVo> routers = new LinkedList<>();

        if (menus == null || menus.isEmpty()) {
            return null;
        }

        for (UmsMenu menu : menus) {
            if (!menu.getParentId().equals(parentId)) {
                continue;
            }
            RouterVo router = BeanUtil.toBean(menu, RouterVo.class);
            router.setName(router.getPath());
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

            router.setChildren(buildRouters(menus, menu.getId()));
            if (CollUtil.isEmpty(router.getChildren())) {
                router.setChildren(null);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建菜单树
     *
     * @param menus    菜单列表
     * @param parentId 父id
     * @return 菜单树
     */
    private static List<MenuVo> buildMenus(List<UmsMenu> menus, Long parentId) {
        List<MenuVo> list = new ArrayList<>();
        if (menus == null || menus.isEmpty()) {
            return null;
        }
        for (UmsMenu menu : menus) {
            if (!menu.getParentId().equals(parentId)) {
                continue;
            }
            MenuVo vo = BeanUtil.toBean(menu,MenuVo.class);
            vo.setChildren(buildMenus(menus, menu.getId()));
            if (CollUtil.isEmpty(vo.getChildren())) {
                vo.setChildren(null);
            }
            list.add(vo);
        }
        return list;
    }


    /**
     * 设置重定向路由
     *
     * @param routers 路由
     * @return 设置好后的路由
     */
    private static List<RouterVo> setRedirect(List<RouterVo> routers) {
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
