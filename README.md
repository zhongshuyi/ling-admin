# Ling-Admin

[![码云 Gitee](https://gitee.com/zhong_shu_yi/ling-admin/badge/star.svg?theme=blue)](https://gitee.com/zhong_shu_yi/ling-admin)
[![GitHub](https://img.shields.io/github/stars/zhongshuyi/ling-admin.svg?style=social&label=Stars)](https://github.com/zhongshuyi/ling-admin)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/zhongshuyi/ling-admin/blob/master/LICENSE)
[![使用 IntelliJ IDEA 开发维护](https://img.shields.io/badge/IntelliJ%20IDEA-提供支持-blue.svg)](https://www.jetbrains.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.4-blue.svg)](https://spring.io/projects/spring-boot)
[![JDK-11](https://img.shields.io/badge/JDK-11-green.svg)](https://www.java.com/zh-CN/)

这是我的个人学习项目，将自己所学整合，尽量规范代码

后台前端：[https://github.com/zhongshuyi/ling-admin-ui](https://github.com/zhongshuyi/ling-admin-ui)

github: [https://github.com/zhongshuyi/ling-admin](https://github.com/zhongshuyi/ling-admin)

业务参考：[jeecg-boot](http://jeecg.com)
代码参考：[RuoYi-Vue-Plus](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus)

目前使用的技术栈

| 技术栈                 | 说明                  |
|:--------------------|:--------------------|
| springboot          | java 框架             |
| Sa-token            | 权限，认证               |
| MySQL               | 关系型数据库              |
| p6spy               | SQL 分析              |
| Redis               | 非关系型数据库             |
| Mybatis-Plus        | 增强 mybatis 的单表操作    |
| Jackson             | 序列化工具               |
| Validation          | 数据校验                |
| Hutool、Lombok       | 工具集，减少代码冗余 增加安全性    |
| Minio               | 本地对象存储              |
| Docker              | 容器部署                |
| CheckStyle          | 代码风格检查              |
| Mapstruct           | Bean 转化工具           |
| SpringDoc           | 接口文档生成              |

## 内容列表

## 背景

每次我看到自己之前写的代码或者别人的代码都会想，这个 功能/效果 有没有更好的实现方式，代码能不能更简洁优雅规范，流程能不能更合规，有没有好用的类库

我想开发一个项目把自己的所学知识都优雅地实现，先实现大部分项目都有的管理后台，然后再以此为基础去开发具体的业务，所以就有了这个项目

经历了无数次修改，现在还是不太满意，希望以后慢慢添加新的东西，修改不好的地方

## 安装

克隆仓库

```bash
git clone https://github.com/zhongshuyi/ling-admin
```

创建数据库 `ling-admin`，导入 [SQL 文件](/sql/ling-admin.sql)（/sql/ling-admin.sql）

修改 [application.yml](/ling-start/src/main/resources/application-dev.yml)中的数据库账户与密码

```yml
spring:
   datasource:
      username: root
      password: 12345678
```

修改 [redisson-config.yml](/ling-start/src/main/resources/redisson-config.yml)中的 Redis 账户与密码

```yml
# 单 Redis 节点模式
singleServerConfig:
   # 节点地址 (默认本地 6379)
   address: redis://localhost:6379
   # 密码
   password: 123456
```

启动类在 `/ling-start/src/main/java/com/ling/LingAdminApplication.java`

## 用法

参考 `ling-dome`

1. 新建模块 `ling-xxx`
2. 在数据库中创建表，包含基础字段

    ```sql
    create table tableName
    (
        id          bigint unsigned primary key auto_increment comment '主键 id',
        create_by   varchar(30)  default '' comment '创建者',
        create_time datetime null comment '创建时间',
        update_by   varchar(30)  default '' comment '修改者',
        update_time datetime null comment '修改时间',
        remark      varchar(500)    default '' comment '备注'
        is_deleted  tinyint unsigned default '0' null comment '删除标志（0 代表存在 1 代表删除）'
    )
        comment '表注释' charset = utf8;
    ```

3. 通过 `ling-generator/src/main/java/com/ling/LingGeneratorApplication.java` 生成代码

## 附加内容

### 功能描述

#### 菜单/权限

系统菜单是由用户自己配置的动态菜单，名称，图标，顺序等都可在菜单/权限管理内配置

权限/按钮 是对资源权限的分配，所有的接口都需要授权才能访问 (
拥有超级管理员角色的用户有所有权限，以及获取用户信息，用户菜单，登录功能不需要授权)

例如：菜单管理要获取菜单信息要调用获取菜单的接口，如果你没有获取菜单接口的权限，则访问被拒绝

#### 权限

用户权限 = 用户角色权限 + 用户部门角色权限

用户角色权限：需要在角色管理的角色权限处给角色赋权限

部门权限：在部门管理处给部门赋权限，部门权限与用户没有直接关系，而是要通过部门 角色进行权限操作

部门角色权限：部门角色权限在下级部门管理->部门角色处给部门角色赋权限，所赋权限必须是该部门已有的权限，部门角色权限与用户的关联在

**下级部门管理只有用户身份是上级才能管理**

例如：

有菜单新增，菜单删除，菜单查询，菜单修改四个权限

角色 A 拥有菜单新增权限
部门 Z 拥有菜单删除，查询权限
部门 Z 下有部门角色 X

则在 下级部门管理->部门角色 处给部门 Z 的 部门角色 赋权限时也只能选择 部门 Z 已有的 菜单删除，查询权限

现假设给部门角色 X 赋与菜单删除，查询权限
用户 S 关联了角色 A，部门 Z，部门角色 X
则用户 S 拥有的权限是 菜单的新增，删除，查询权限

#### 数据权限

在实际开发中，需要设置用户只能查看哪些部门的数据，这种情况一般称为数据权限。
例如对于销售，财务的数据，它们是非常敏感的，因此要求对数据权限进行控制，对于基于集团性的应用系统而言，就更多需要控制好各自公司的数据了。如设置只能看本公司、或者本部门的数据，对于特殊的领导，可能需要跨部门的数据，
因此程序不能硬编码那个领导该访问哪些数据，需要进行后台的权限和数据权限的控制。

**若用户拥有超级管理员角色，则拥有所有数据权限**

在（系统管理->角色管理->角色数据权限）设置需要数据权限的角色 目前支持以下几种权限

1. 全部数据权限
2. 自定数据权限
3. 部门数据权限
4. 部门及以下数据权限
5. 仅本人数据权限

以上数据权限均可动态配置

多个角色拥有不同数据权限则向上兼容

## API 文档

API 文档使用 SpringDoc 生成，启动项目后访问 <http://localhost:8080/v3/api-docs> 可得到 OpenAPI3 格式的接口文档，可将文档导入到
Postman、APIfox

## 主要维护人员

[@钟舒艺](https://github.com/zhongshuyi)

## 参与贡献方式

详细请阅读文档 [参与贡献](https://github.com/zhongshuyi/developer-knowledge-base/blob/main/%E5%85%B6%E4%BB%96/%E5%8F%82%E4%B8%8E%E8%B4%A1%E7%8C%AE.md)
与 [协作开发流程](https://github.com/zhongshuyi/developer-knowledge-base/blob/main/%E5%B7%A5%E5%85%B7/Git/Git%20%E5%8D%8F%E4%BD%9C%E5%BC%80%E5%8F%91%E6%B5%81%E7%A8%8B%E4%B8%8E%E8%A7%84%E8%8C%83.md)
并按其中的规范与流程进行贡献

提问请到 [Issues](https://github.com/zhongshuyi/ling-admin/issues)

接受
PR，具体请看 [协作开发流程](https://github.com/zhongshuyi/developer-knowledge-base/blob/main/%E5%B7%A5%E5%85%B7/Git/Git%20%E5%8D%8F%E4%BD%9C%E5%BC%80%E5%8F%91%E6%B5%81%E7%A8%8B%E4%B8%8E%E8%A7%84%E8%8C%83.md)

## 许可证

[MIT](LICENSE) © 2022 [zhongshuyi](https://github.com/zhongshuyi)
