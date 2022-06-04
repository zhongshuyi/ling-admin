[![码云Gitee](https://gitee.com/zhong_shu_yi/mall/badge/star.svg?theme=blue)](https://gitee.com/zhong_shu_yi/mall)
[![GitHub](https://img.shields.io/github/stars/zhongshuyi/mall.svg?style=social&label=Stars)](https://github.com/JavaLionLi/RuoYi-Vue-Plus)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus/blob/master/LICENSE)
[![使用IntelliJ IDEA开发维护](https://img.shields.io/badge/IntelliJ%20IDEA-提供支持-blue.svg)](https://www.jetbrains.com/?from=RuoYi-Vue-Plus)
<br>
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.4.5-blue.svg)]()
[![JDK-8+](https://img.shields.io/badge/JDK-8-green.svg)]()

这是我的个人学习项目,将自己所学整合,尽量规范代码

后台前端: [https://github.com/zhongshuyi/ling-admin-ui](https://github.com/zhongshuyi/ling-admin-ui)

github: [https://github.com/zhongshuyi/ling-admin](https://github.com/zhongshuyi/ling-admin)

业务参考: [jeecg-boot](http://jeecg.com)
代码参考: [RuoYi-Vue-Plus](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus)

目前使用的技术栈

| 技术栈                 | 说明               |
|:--------------------|:-----------------|
| springboot          | java框架           |
| Spring Security、Jwt | 权限,认证            |
| MySQL               | 关系型数据库           |
| p6spy               | SQL分析            |
| Redis               | 非关系型数据库          |
| Mybatis-Plus        | 增强mybatis的单表操作   |
| Jackson             | 序列化工具            |
| Validation          | 数据校验             |
| Hutool、Lombok       | 工具集,减少代码冗余 增加安全性 |
| Minio               | 本地对象存储           |
| Docker              | 容器部署             |

目前项目还在缓慢开发中,作为自己的学习项目

# 功能使用文档

## 功能思维导图

![功能思维导图](http://121.196.144.187:9000/mall/img/ling-admin.png)

## 菜单/权限

系统菜单是由用户自己配置的动态菜单,名称,图标,顺序等都可在菜单/权限管理内配置

权限/按钮 是对资源权限的分配,所有的接口都需要授权才能访问(拥有超级管理员角色的用户有所有权限,以及获取用户信息,用户菜单,登录功能不需要授权)

例如: 菜单管理要获取菜单信息要调用获取菜单的接口,如果你没有获取菜单接口的权限,则访问被拒绝

## 权限

用户权限 = 用户角色权限 + 用户部门角色权限

用户角色权限: 需要在角色管理的角色权限处给角色赋权限

部门权限: 在部门管理处给部门赋权限,部门权限与用户没有直接关系,而是要通过部门 角色进行权限操作

部门角色权限: 部门角色权限在下级部门管理->部门角色处给部门角色赋权限,所赋权限必须是该部门已有的权限,部门角色权限与用户的关联在

**下级部门管理只有用户身份是上级才能管理**

例如:

有菜单新增,菜单删除,菜单查询,菜单修改四个权限

角色A 拥有菜单新增权限
部门Z 拥有菜单删除,查询权限
部门Z下有部门角色X

则在 下级部门管理->部门角色 处给部门Z的 部门角色 赋权限时也只能选择 部门Z 已有的 菜单删除,查询权限

现假设给部门角色X赋与菜单删除,查询权限
用户S关联了角色A,部门Z,部门角色X
则用户S拥有的权限是 菜单的新增,删除,查询权限

## 数据权限

在实际开发中，需要设置用户只能查看哪些部门的数据，这种情况一般称为数据权限。
例如对于销售，财务的数据，它们是非常敏感的，因此要求对数据权限进行控制， 对于基于集团性的应用系统而言，就更多需要控制好各自公司的数据了。如设置只能看本公司、或者本部门的数据，对于特殊的领导，可能需要跨部门的数据，
因此程序不能硬编码那个领导该访问哪些数据，需要进行后台的权限和数据权限的控制。

**若用户拥有超级管理员角色,则拥有所有数据权限**

在（系统管理->角色管理->角色数据权限）设置需要数据权限的角色 目前支持以下几种权限

1. 全部数据权限
2. 自定数据权限
3. 部门数据权限
4. 部门及以下数据权限
5. 仅本人数据权限

以上数据权限均可动态配置

多个角色拥有不同数据权限则向上兼容






