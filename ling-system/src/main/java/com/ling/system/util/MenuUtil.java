package com.ling.system.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.ling.common.constant.AppConstants;
import com.ling.common.constant.Regular;
import com.ling.common.enums.MenuType;
import com.ling.system.entity.SysMenu;
import com.ling.system.vo.RouterMeta;
import com.ling.system.vo.RouterVo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 菜单构建工具类.
 *
 * @author 钟舒艺
 **/
@Slf4j
public final class MenuUtil {

    /**
     * 前端url分隔符.
     */
    private static final String PATH_DELIMITER = "/";


    /**
     * 前端layout名称.
     */
    private static final String LAYOUT_COMPONENT = "LAYOUT";

    /**
     * 菜单根id.
     */
    private static final Long MENU_ROOT_ID = 0L;

    private MenuUtil() {
    }

    /**
     * 获取处理好后的路由.
     *
     * @param menus 从数据库中直接查出来的菜单信息
     * @return 处理好的
     */
    public static List<RouterVo> getRouter(final List<SysMenu> menus) {
        return setRedirect(buildRouters(menus, MENU_ROOT_ID));
    }

    /**
     * 构建菜单树.
     *
     * @param menus 菜单列表
     * @return 处理后菜单树
     */
    public static List<Tree<Long>> getMenuList(final List<SysMenu> menus) {
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setNameKey("title");
        return TreeUtil.build(menus, MENU_ROOT_ID, treeNodeConfig, (treeNode, tree) ->
                tree.setId(treeNode.getId())
                        .setParentId(treeNode.getParentId())
                        .setWeight(treeNode.getOrderNo())
                        .setName(treeNode.getTitle())
                        .putAll(BeanUtil.beanToMap(treeNode)));
    }

    /**
     * 构建权限树结构.
     *
     * @param menus 菜单
     * @return 树结构
     */
    public static List<Tree<Long>> buildPermTree(final List<SysMenu> menus) {
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");
        return TreeUtil.build(menus, MENU_ROOT_ID, treeNodeConfig, (treeNode, tree) ->
                tree.setId(treeNode.getId())
                        .setParentId(treeNode.getParentId())
                        .setWeight(treeNode.getOrderNo())
                        .setName(treeNode.getTitle()));
    }


    /**
     * 构建路由树.
     *
     * @param menus    菜单
     * @param parentId 父id
     * @return 菜单树
     */
    private static List<RouterVo> buildRouters(
            final List<SysMenu> menus,
            final Long parentId
    ) {

        final LinkedList<RouterVo> routers = new LinkedList<>();

        if (CollUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }

        for (final SysMenu menu : menus) {
            if (!menu.getParentId().equals(parentId)) {
                continue;
            }
            final RouterVo router = BeanUtil.toBean(menu, RouterVo.class);

            // 设置路由名字
            router.setName(router.getPath().replace(PATH_DELIMITER, ""));
            router.setName(
                    router.getName()
                            .substring(0, 1)
                            .toUpperCase()
                            +
                            router.getName().substring(1));

            String component = menu.getComponent();

            // 如果是上级菜单是根路由或链接设置LayOut组件
            if (menu.getParentId().equals(MENU_ROOT_ID) || menu.getIsLink().equals(AppConstants.TRUE)) {
                router.setComponent(LAYOUT_COMPONENT);
            }


            // 设置一级路由路径(不能是链接)
            if (menu.getParentId().equals(MENU_ROOT_ID) && menu.getIsLink().equals(AppConstants.FALSE)) {
                String path = PATH_DELIMITER + router.getPath();
                String regx = PATH_DELIMITER + "+";
                path = path.replaceAll(regx, PATH_DELIMITER);
                router.setPath(path);
            } else {
                if (router.getPath().matches("/+.+")) {
                    router.setPath(router.getPath().replaceFirst(PATH_DELIMITER + "+", ""));
                }
            }

            final RouterMeta meta = BeanUtil.toBean(menu, RouterMeta.class);

            // 设置路由属性
            meta.setIgnoreKeepAlive(conversion(menu.getIgnoreKeepAlive()))
                    .setIsLink(conversion(menu.getIsLink()))
                    .setAffix(conversion(menu.getAffix()))
                    .setHideBreadcrumb(conversion(menu.getHideBreadcrumb()))
                    .setHideChildrenInMenu(conversion(menu.getHideChildrenInMenu()))
                    .setHideTab(conversion(menu.getHideTab()))
                    .setHideMenu(conversion(menu.getHideMenu()));

            // 上级是根目录且自身是菜单
            if (menu.getMenuType().equals(MenuType.MENU.getCode()) && menu.getParentId().equals(MENU_ROOT_ID)) {
                router.setChildren(List.of(
                                        new RouterVo()
                                                .setName(router.getName())
                                                .setPath("")
                                                .setComponent(component)
                                                .setMeta(meta)
                                )
                        )
                        .setMeta(meta.setHideChildrenInMenu(Boolean.TRUE))
                        .setComponent(LAYOUT_COMPONENT);
                routers.add(router);
                continue;
            }

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
     * 设置重定向路由.
     *
     * @param routers 路由
     * @return 设置好后的路由
     */
    private static List<RouterVo> setRedirect(final List<RouterVo> routers) {
        for (final RouterVo router : routers) {

            if (CollUtil.isEmpty(router.getChildren())
                    || CharSequenceUtil.isEmpty(router.getChildren().get(0).getPath())) {
                continue;
            }
            final StringBuilder redirect = new StringBuilder(router.getPath());
            RouterVo i = router;
            while (CollUtil.isNotEmpty(i.getChildren())) {
                int count = i.getChildren().size();
                for (int j = 0; j < count; j++) {
                    RouterVo temp = i.getChildren().get(j);
                    if (!temp.getPath().matches(Regular.INTERNET_URL)) {
                        i = temp;
                        break;
                    }
                }
                redirect.append("/").append(i.getPath());
            }
            if (CollUtil.isNotEmpty(router.getChildren())) {
                router.setRedirect(redirect.toString());
            }
        }
        return routers;
    }


    /**
     * 转换.
     *
     * @param is 数据库中的值.
     * @return 转换后的值
     */
    private static Boolean conversion(final Byte is) {
        return is.equals(AppConstants.TRUE);
    }
}
