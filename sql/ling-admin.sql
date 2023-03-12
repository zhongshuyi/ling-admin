-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ling-admin
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


--
-- Table structure for table `sys_admin`
--

DROP TABLE IF EXISTS `sys_admin`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_admin`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户 id',
    `username`       varchar(30)     NOT NULL COMMENT '用户账号',
    `real_name`      varchar(30)     NOT NULL COMMENT '用户昵称',
    `email`          varchar(50)              DEFAULT '' COMMENT '用户邮箱',
    `tel`            varchar(11)     NOT NULL DEFAULT '' COMMENT '手机号码',
    `sex`            tinyint unsigned         DEFAULT '0' COMMENT '用户性别（0 男 1 女 2 未知）',
    `avatar_file_id` bigint unsigned          DEFAULT NULL COMMENT '头像的文件 id',
    `password`       varchar(100)             DEFAULT '' COMMENT '密码',
    `status`         tinyint unsigned         DEFAULT '1' COMMENT '帐号状态（1 正常 0 停用）',
    `depart_ids`     varchar(50)              DEFAULT NULL COMMENT '负责部门 id',
    `user_identity`  tinyint unsigned         DEFAULT '0' COMMENT '身份（0 普通成员 1 上级）',
    `remark`         varchar(500)             DEFAULT NULL COMMENT '备注',
    `create_by`      varchar(64)              DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                 DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64)              DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                 DEFAULT NULL COMMENT '更新时间',
    `is_deleted`     tinyint unsigned         DEFAULT '0' COMMENT '删除标志（0 代表存在 1 代表删除）',
    PRIMARY KEY (`id`),
    KEY `ums_admin_username_index` (`username`),
    KEY `avatar_file_id` (`avatar_file_id`),
    CONSTRAINT `sys_admin_ibfk_1` FOREIGN KEY (`avatar_file_id`) REFERENCES `sys_file` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin`
--

LOCK TABLES `sys_admin` WRITE;
/*!40000 ALTER TABLE `sys_admin`
    DISABLE KEYS */;
INSERT INTO `sys_admin`
VALUES (1, 'zhong', '钟舒艺', '1820965203@qq.com', '17607952136', 0, 32,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, '2', 1, NULL, '', NULL, 'zhong',
        '2023-03-07 16:25:55', 0),
       (2, 'test', '测试', '2820965203@qq.com', '17607952132', 0, 33,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, '', NULL, 'zhong',
        '2023-03-07 16:26:22', 0),
       (7, 'niejun', '聂俊', '1398231193@qq.com', '17607952136', 0, 34,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, 'zhong',
        '2022-03-11 09:37:49', 'zhong', '2023-03-07 16:26:44', 0),
       (8, 'aozhiyao', '敖志垚', '1820965203@qq.com', '17607952136', 0, NULL,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, NULL, NULL, '', NULL, 0),
       (9, 'huangbin', '黄斌', 'huang@gmail.com', '13907912130', 0, 35,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, NULL, NULL, 'zhong',
        '2023-03-07 16:27:02', 0),
       (10, 'xupeng', '许鹏', '965203@qq.com', '15907949796', 0, 36,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, NULL, NULL, 'zhong',
        '2023-03-07 16:27:22', 0),
       (11, 'luowei', '罗伟', 'c8d8@m4401zok.cn', '18835264721', 0, 37,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, NULL, NULL, 'zhong',
        '2023-03-07 16:27:42', 0),
       (12, 'luowei', '罗伟', 'c8d8@m4401zok.cn', '18835264721', 0, 31,
        '$2a$10$kjWksDie8.bwU4k3sf/Kxuy6V1nVSlfxYZmdSOGb4B321iMJqYMf.', 1, NULL, 0, NULL, NULL, NULL, '', NULL, 1);
/*!40000 ALTER TABLE `sys_admin`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门 id',
    `parent_id`   bigint unsigned DEFAULT '0' COMMENT '父 id',
    `dept_name`   varchar(30)     DEFAULT '' COMMENT '部门名称',
    `order_no`    int             DEFAULT '0' COMMENT '显示顺序',
    `leader`      varchar(20)     DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(11)     DEFAULT NULL COMMENT '联系电话',
    `parent_list` varchar(60)     DEFAULT '',
    `email`       varchar(50)     DEFAULT NULL COMMENT '邮箱',
    `create_by`   varchar(64)     DEFAULT '' COMMENT '创建者',
    `create_time` datetime        DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)     DEFAULT '' COMMENT '更新者',
    `update_time` datetime        DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(200)    DEFAULT NULL,
    `is_deleted`  tinyint(1)      DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept`
    DISABLE KEYS */;
INSERT INTO `sys_dept`
VALUES (1, 0, '深圳总公司', 0, '钟舒艺', NULL, '0,1,', NULL, '', NULL, '', NULL, NULL, 0),
       (2, 1, '龙岗分部', 0, '钟舒艺', NULL, '0,1,2,', NULL, '', NULL, '', NULL, NULL, 0),
       (3, 1, '龙华分部', 2, '钟舒艺', NULL, '0,1,3,', NULL, '', NULL, '', NULL, NULL, 0),
       (4, 1, '宝安分部', 1, '钟舒艺', NULL, '0,1,4,', NULL, '', NULL, '', NULL, NULL, 0),
       (5, 2, '财务部', 1, '钟舒艺', NULL, '0,1,2,5,', NULL, '', NULL, '', NULL, NULL, 0),
       (6, 2, '人事部', 0, '钟舒艺', NULL, '0,1,2,6,', NULL, '', NULL, '', NULL, NULL, 0),
       (9, 0, '长沙分公司', 2, NULL, NULL, '0,7,', NULL, '', NULL, '', NULL, NULL, 0),
       (10, 0, '南昌分公司', 1, NULL, NULL, '0,8,', NULL, '', NULL, '', NULL, NULL, 0);
/*!40000 ALTER TABLE `sys_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_permission`
--

DROP TABLE IF EXISTS `sys_dept_permission`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_permission`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dept_id`       bigint unsigned DEFAULT NULL,
    `permission_id` bigint unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `dept_id` (`dept_id`),
    KEY `permission_id` (`permission_id`),
    CONSTRAINT `sys_dept_permission_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
    CONSTRAINT `sys_dept_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_menu` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 72
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='部门权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_permission`
--

LOCK TABLES `sys_dept_permission` WRITE;
/*!40000 ALTER TABLE `sys_dept_permission`
    DISABLE KEYS */;
INSERT INTO `sys_dept_permission`
VALUES (1, 2, 1),
       (2, 2, 2),
       (3, 2, 3),
       (15, 2, 5),
       (23, 2, 21),
       (25, 2, 50),
       (26, 2, 51),
       (27, 2, 52),
       (28, 2, 53),
       (29, 2, 54),
       (30, 2, 55),
       (31, 1, 1),
       (32, 1, 2),
       (33, 1, 3),
       (34, 1, 5),
       (35, 1, 6),
       (36, 1, 12),
       (37, 1, 20),
       (38, 1, 21),
       (39, 1, 22),
       (40, 1, 23),
       (41, 1, 24),
       (42, 1, 25),
       (43, 1, 26),
       (44, 1, 28),
       (45, 1, 29),
       (46, 1, 30),
       (47, 1, 31),
       (48, 1, 32),
       (49, 1, 33),
       (50, 1, 34),
       (51, 1, 35),
       (52, 1, 36),
       (53, 1, 37),
       (54, 1, 38),
       (55, 1, 39),
       (56, 1, 40),
       (57, 1, 41),
       (58, 1, 42),
       (59, 1, 43),
       (60, 1, 44),
       (61, 1, 45),
       (62, 1, 46),
       (63, 1, 47),
       (64, 1, 48),
       (65, 1, 49),
       (66, 1, 50),
       (67, 1, 51),
       (68, 1, 52),
       (69, 1, 53),
       (70, 1, 54),
       (71, 1, 55);
/*!40000 ALTER TABLE `sys_dept_permission`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_role`
--

DROP TABLE IF EXISTS `sys_dept_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_role`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dept_id`     bigint unsigned DEFAULT NULL COMMENT '部门 id',
    `role_name`   varchar(200)    NOT NULL COMMENT '部门角色名',
    `role_key`    varchar(100)    NOT NULL COMMENT '部门角色编码',
    `remark`      varchar(255)    DEFAULT '' COMMENT '备注',
    `create_by`   varchar(30)     DEFAULT '' COMMENT '创建者',
    `create_time` datetime        DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(30)     DEFAULT '' COMMENT '修改者',
    `update_time` datetime        DEFAULT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1)      DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    KEY `dept_id` (`dept_id`),
    CONSTRAINT `sys_dept_role_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='部门角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_role`
--

LOCK TABLES `sys_dept_role` WRITE;
/*!40000 ALTER TABLE `sys_dept_role`
    DISABLE KEYS */;
INSERT INTO `sys_dept_role`
VALUES (1, 2, '会计', 'account', '部门会计角色', NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `sys_dept_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_role_permission`
--

DROP TABLE IF EXISTS `sys_dept_role_permission`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_role_permission`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `permission_id` bigint unsigned DEFAULT NULL COMMENT '权限 id',
    `dept_role_id`  bigint unsigned DEFAULT NULL COMMENT '角色 id',
    PRIMARY KEY (`id`),
    KEY `dept_role_id` (`dept_role_id`),
    KEY `permission_id` (`permission_id`),
    CONSTRAINT `sys_dept_role_permission_ibfk_3` FOREIGN KEY (`dept_role_id`) REFERENCES `sys_dept_role` (`id`),
    CONSTRAINT `sys_dept_role_permission_ibfk_4` FOREIGN KEY (`permission_id`) REFERENCES `sys_menu` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_role_permission`
--

LOCK TABLES `sys_dept_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_dept_role_permission`
    DISABLE KEYS */;
INSERT INTO `sys_dept_role_permission`
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (5, 5, 1);
/*!40000 ALTER TABLE `sys_dept_role_permission`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_role_user`
--

DROP TABLE IF EXISTS `sys_dept_role_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_role_user`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`      bigint unsigned DEFAULT NULL COMMENT '用户 id',
    `dept_role_id` bigint unsigned DEFAULT NULL COMMENT '部门角色 id',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `dept_role_id` (`dept_role_id`),
    CONSTRAINT `sys_dept_role_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_admin` (`id`),
    CONSTRAINT `sys_dept_role_user_ibfk_2` FOREIGN KEY (`dept_role_id`) REFERENCES `sys_dept_role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_role_user`
--

LOCK TABLES `sys_dept_role_user` WRITE;
/*!40000 ALTER TABLE `sys_dept_role_user`
    DISABLE KEYS */;
INSERT INTO `sys_dept_role_user`
VALUES (1, 7, 1);
/*!40000 ALTER TABLE `sys_dept_role_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data`
(
    `dict_code`   bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   int              DEFAULT '0' COMMENT '字典排序',
    `dict_label`  varchar(100)     DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100)     DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100)     DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100)     DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100)     DEFAULT NULL COMMENT '表格回显样式',
    `is_default`  tinyint unsigned DEFAULT '1' COMMENT '是否默认（0 是 1 否）',
    `status`      tinyint unsigned DEFAULT '0' COMMENT '状态（0 正常 1 停用）',
    `create_by`   varchar(64)      DEFAULT '' COMMENT '创建者',
    `create_time` datetime         DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)      DEFAULT '' COMMENT '更新者',
    `update_time` datetime         DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500)     DEFAULT NULL COMMENT '备注',
    `is_deleted`  tinyint(1)       DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`dict_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict_data`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type`
(
    `dict_id`     bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`   varchar(100)     DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100)     DEFAULT '' COMMENT '字典类型',
    `status`      tinyint unsigned DEFAULT '0' COMMENT '状态（0 正常 1 停用）',
    `create_by`   varchar(64)      DEFAULT '' COMMENT '创建者',
    `create_time` datetime         DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)      DEFAULT '' COMMENT '更新者',
    `update_time` datetime         DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500)     DEFAULT NULL COMMENT '备注',
    `is_deleted`  tinyint(1)       DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`dict_id`),
    UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict_type`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `name`          char(36)         DEFAULT '' COMMENT '储存文件名 (UUID)',
    `original_name` varchar(400)     DEFAULT '' COMMENT '原始文件名',
    `postfix`       varchar(20)      DEFAULT '' COMMENT '文件后缀名',
    `path`          varchar(500)     DEFAULT '' COMMENT '文件路径',
    `size`          bigint unsigned  DEFAULT '0' COMMENT '文件大小',
    `business_type` tinyint unsigned DEFAULT NULL COMMENT '业务类型',
    `upload_by_id`  bigint unsigned NOT NULL COMMENT '上传者 id',
    `create_by`     varchar(30)      DEFAULT '' COMMENT '创建者',
    `create_time`   datetime         DEFAULT NULL COMMENT '创建时间',
    `update_by`     varchar(30)      DEFAULT '' COMMENT '修改者',
    `update_time`   datetime         DEFAULT NULL COMMENT '修改时间',
    `remark`        varchar(500)     DEFAULT '' COMMENT '备注',
    `is_deleted`    tinyint(1)       DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 38
  DEFAULT CHARSET = utf8mb3 COMMENT ='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file`
    DISABLE KEYS */;
INSERT INTO `sys_file`
VALUES (15, 'ef89cd7b-bec7-4911-978a-48f7e5ef6e0a', 'C{Z8{[I6RP8L}U0D[9E0)KE.jpg', '.jpg', 'img/avatar', 369652, 0, 2,
        'zhong', '2022-02-22 11:51:25', 'zhong', '2023-03-07 16:26:22', '', 1),
       (23, 'cffab2a1-9587-4d7e-952d-59bfe1a48629', '79.jpeg', '.jpeg', 'img/avatar', 280129, 0, 7, 'zhong',
        '2022-05-29 00:05:01', 'zhong', '2023-03-07 16:26:44', '', 1),
       (28, '1ebaee06-de56-472b-9987-62a653aa5883', '77.jpeg', '.jpeg', 'img/avatar', 640013, 0, 1, 'zhong',
        '2022-06-05 23:26:57', 'zhong', '2023-03-07 16:25:55', '', 1),
       (29, '27ac0a43-b281-4fc4-9d47-8169f73fc91f', '194.jpeg', '.jpeg', 'img/avatar', 159264, 0, 9, NULL, NULL,
        'zhong', '2023-03-07 16:27:02', '', 1),
       (30, '323a7e48-84e9-49b2-a3c2-59c53e677e21', '1464603563.jpeg', '.jpeg', 'img/avatar', 97041, 0, 10, NULL, NULL,
        'zhong', '2023-03-07 16:27:23', '', 1),
       (31, '92755118-7e0a-4d32-a6dc-1a841400aa90', 'hdImg_3150306d8be5c4d798e4b46e1871d1241631616562390.jpg', '.jpg',
        'img/avatar', 541196, 0, 11, NULL, NULL, 'zhong', '2023-03-07 16:27:42', '', 1),
       (32, '43f395ac-20f7-4fdd-87f2-05101750ed38', '194.jpeg', '.jpeg', 'img/avatar', 160823, 0, 1, 'zhong',
        '2023-03-07 16:25:55', 'zhong', '2023-03-07 16:25:55', '', 0),
       (33, '4b78ed6f-5836-4bbe-8115-2c5241a2e68f', '1211760506.jpeg', '.jpeg', 'img/avatar', 287226, 0, 2, 'zhong',
        '2023-03-07 16:26:22', 'zhong', '2023-03-07 16:26:22', '', 0),
       (34, '1d9098e8-66fc-4b2d-8f08-fcf77d11153f', '1385876812.jpeg', '.jpeg', 'img/avatar', 541120, 0, 7, 'zhong',
        '2023-03-07 16:26:44', 'zhong', '2023-03-07 16:26:44', '', 0),
       (35, '2daedd90-82b6-490e-8a85-0626df15ac9c', '1366031343.jpeg', '.jpeg', 'img/avatar', 544559, 0, 9, 'zhong',
        '2023-03-07 16:27:02', 'zhong', '2023-03-07 16:27:02', '', 0),
       (36, '54c20485-ccb7-4b76-b92c-ddc0726de7fc', '1395599884.jpeg', '.jpeg', 'img/avatar', 1177806, 0, 10, 'zhong',
        '2023-03-07 16:27:22', 'zhong', '2023-03-07 16:27:22', '', 0),
       (37, 'cfe02c00-2a11-437e-ab8e-db8e4aef3762', '1827497988.jpeg', '.jpeg', 'img/avatar', 1398576, 0, 11, 'zhong',
        '2023-03-07 16:27:42', 'zhong', '2023-03-07 16:27:42', '', 0);
/*!40000 ALTER TABLE `sys_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_login_log`
--

DROP TABLE IF EXISTS `sys_login_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_log`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录 id',
    `user_id`        bigint unsigned  DEFAULT NULL COMMENT '用户 id',
    `username`       varchar(30)      DEFAULT '' COMMENT '登录用户名',
    `ipaddr`         varchar(45)      DEFAULT NULL COMMENT '登录 IP 地址',
    `login_location` varchar(255)     DEFAULT '' COMMENT '登录地点',
    `browser`        varchar(50)      DEFAULT '' COMMENT '浏览器类型',
    `os`             varchar(50)      DEFAULT '' COMMENT '操作系统',
    `status`         tinyint unsigned DEFAULT '0' COMMENT '登录状态（0 成功 1 失败）',
    `msg`            varchar(255)     DEFAULT '' COMMENT '提示消息',
    `login_time`     datetime         DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `sys_login_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_admin` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 198
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

LOCK TABLES `sys_login_log` WRITE;
/*!40000 ALTER TABLE `sys_login_log`
    DISABLE KEYS */;
INSERT INTO `sys_login_log`
VALUES (1, NULL, '测试新增', NULL, '', '', '', 0, '', NULL),
       (2, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-02-26 18:28:06'),
       (3, NULL, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-02-26 20:17:13'),
       (4, 1, 'zhong', '127.0.0.1', '内网 IP', 'Chrome', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-02-28 09:48:42'),
       (5, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-02-28 09:50:11'),
       (6, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-01 10:09:15'),
       (7, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-01 11:42:36'),
       (8, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-03 12:01:27'),
       (9, 1, 'zhong', '127.0.0.1', '内网 IP', 'Chrome', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-03 12:33:38'),
       (10, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-05 11:44:50'),
       (11, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-05 16:06:35'),
       (12, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-05 16:22:34'),
       (13, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-05 16:27:29'),
       (14, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-05 16:32:53'),
       (15, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-05 16:37:27'),
       (16, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-08 09:26:19'),
       (17, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-08 10:07:39'),
       (18, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-08 10:15:50'),
       (19, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-08 11:29:39'),
       (20, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-08 18:19:06'),
       (21, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-09 09:46:27'),
       (22, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-09 10:57:26'),
       (23, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-10 12:34:31'),
       (24, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-10 17:16:59'),
       (25, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-11 09:26:10'),
       (26, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-13 19:06:02'),
       (27, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-14 10:07:03'),
       (28, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-14 10:16:43'),
       (29, 1, 'zhong', '127.0.0.1', '内网 IP', 'Chrome', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-16 10:24:42'),
       (30, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-19 14:49:38'),
       (31, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-03-22 10:54:03'),
       (32, NULL, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-03-23 18:55:55'),
       (33, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-03-23 18:56:13'),
       (34, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-07 15:06:37'),
       (35, NULL, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-04-07 16:56:26'),
       (36, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-07 16:56:47'),
       (37, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-07 22:36:58'),
       (38, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-07 23:00:36'),
       (39, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-08 16:26:30'),
       (40, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-08 17:33:09'),
       (41, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-08 17:36:15'),
       (42, NULL, 'admin123', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-04-10 10:13:39'),
       (43, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-10 10:14:13'),
       (44, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-10 10:17:16'),
       (45, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-10 16:23:05'),
       (46, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-10 16:26:06'),
       (47, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-10 16:31:53'),
       (48, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-10 21:16:07'),
       (49, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-11 15:18:54'),
       (50, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-11 16:19:57'),
       (51, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-12 12:46:41'),
       (52, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-12 12:49:42'),
       (53, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-12 14:13:07'),
       (54, 1, 'zhong', '127.0.0.1', '内网 IP', 'Firefox', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-12 15:25:10'),
       (55, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-15 19:02:15'),
       (56, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-15 19:03:39'),
       (57, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-16 09:34:18'),
       (58, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Android', 0, '登录成功', '2022-04-16 14:14:17'),
       (59, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-17 15:36:03'),
       (60, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-17 20:15:37'),
       (61, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-17 20:18:15'),
       (62, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-17 20:34:57'),
       (63, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-19 18:18:33'),
       (64, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-19 22:43:24'),
       (65, NULL, '姜娟', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-04-24 15:26:22'),
       (66, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-24 16:24:47'),
       (67, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-04-25 18:38:27'),
       (68, NULL, '蔡秀兰', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '用户名或密码错误',
        '2022-04-25 18:55:43'),
       (69, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-04-25 18:56:08'),
       (70, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-01 18:49:02'),
       (71, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-01 22:44:08'),
       (72, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-02 11:39:17'),
       (73, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-02 11:44:46'),
       (74, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-02 23:40:39'),
       (75, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-03 12:34:29'),
       (76, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-07 13:40:38'),
       (77, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-09 14:27:02'),
       (78, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-09 15:14:38'),
       (79, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-11 13:25:19'),
       (80, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-11 23:57:27'),
       (81, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-12 15:29:24'),
       (82, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-13 14:45:05'),
       (83, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-13 23:01:39'),
       (84, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-15 15:03:56'),
       (85, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-15 15:04:33'),
       (86, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-15 15:11:39'),
       (87, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-15 15:29:24'),
       (88, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-15 18:42:00'),
       (89, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-15 22:05:50'),
       (90, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:32:43'),
       (91, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:43:30'),
       (92, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:50:28'),
       (93, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:52:49'),
       (94, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:53:02'),
       (95, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:53:15'),
       (96, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 17:57:55'),
       (97, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:02:15'),
       (98, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:12:09'),
       (99, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:12:37'),
       (100, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:17:05'),
       (101, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:22:16'),
       (102, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:24:09'),
       (103, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:35:17'),
       (104, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:36:36'),
       (105, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:43:07'),
       (106, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 18:56:45'),
       (107, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 19:00:58'),
       (108, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 19:19:28'),
       (109, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 19:20:57'),
       (110, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-16 19:22:35'),
       (111, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-21 21:41:10'),
       (112, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-22 17:39:31'),
       (113, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 10:39:28'),
       (114, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-24 20:00:00'),
       (115, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:36:46'),
       (116, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:38:55'),
       (117, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:38:58'),
       (118, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:39:00'),
       (119, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:39:34'),
       (120, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:39:46'),
       (121, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 22:45:49'),
       (122, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 23:33:00'),
       (123, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-24 23:47:41'),
       (124, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 00:22:39'),
       (125, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-25 16:21:11'),
       (126, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:25:03'),
       (127, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:25:38'),
       (128, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:26:08'),
       (129, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:39:04'),
       (130, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:53:29'),
       (131, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 16:58:38'),
       (132, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-25 17:12:50'),
       (133, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-26 22:35:56'),
       (134, 2, 'test', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-27 17:06:47'),
       (135, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-27 17:28:28'),
       (136, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-27 17:37:25'),
       (137, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Android', 0, '登录成功', '2022-05-27 21:38:28'),
       (138, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-27 21:50:22'),
       (139, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-27 22:34:34'),
       (140, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 13:15:01'),
       (141, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:26:18'),
       (142, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:26:41'),
       (143, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:26:46'),
       (144, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:27:59'),
       (145, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:37:03'),
       (146, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:39:25'),
       (147, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:40:47'),
       (148, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:42:38'),
       (149, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:46:39'),
       (150, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:49:25'),
       (151, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 22:59:34'),
       (152, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-28 23:10:07'),
       (153, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-29 00:00:21'),
       (154, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-29 00:04:39'),
       (155, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-29 00:30:29'),
       (156, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-30 11:28:34'),
       (157, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:02:00'),
       (158, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:34:39'),
       (159, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:34:49'),
       (160, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:36:30'),
       (161, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:36:47'),
       (162, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:36:54'),
       (163, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:36:58'),
       (164, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 0, '登录成功',
        '2022-05-31 17:37:01'),
       (165, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 0, '登录成功', '2022-05-31 21:08:59'),
       (166, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '登录成功', '2022-05-31 22:47:40'),
       (167, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-01 00:11:03'),
       (168, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-01 16:01:19'),
       (169, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '登录成功', '2022-06-01 18:12:16'),
       (170, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '登录成功', '2022-06-02 00:53:21'),
       (171, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-02 01:15:40'),
       (172, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-02 01:16:09'),
       (173, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-02 09:23:29'),
       (174, 1, 'zhong', '0:0:0:0:0:0:0:1', '内网 IP', 'Unknown', 'Unknown', 1, '登录成功', '2022-06-02 13:34:39'),
       (175, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-02 18:28:33'),
       (176, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-03 17:47:32'),
       (177, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:23:31'),
       (178, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:37:20'),
       (179, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:38:00'),
       (180, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:44:07'),
       (181, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:46:51'),
       (182, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:51:59'),
       (183, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 13:56:41'),
       (184, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 14:22:34'),
       (185, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 14:23:07'),
       (186, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 14:25:54'),
       (187, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 14:51:59'),
       (188, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 15:02:43'),
       (189, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-04 15:03:01'),
       (190, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 22:47:19'),
       (191, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 22:50:52'),
       (192, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 22:51:22'),
       (193, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 22:54:06'),
       (194, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 22:56:02'),
       (195, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 23:26:08'),
       (196, 2, 'test', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-06-05 23:29:34'),
       (197, 1, 'zhong', '127.0.0.1', '内网 IP', 'MSEdge', 'Windows 10 or Windows Server 2016', 1, '登录成功',
        '2022-09-22 08:51:54');
/*!40000 ALTER TABLE `sys_login_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu`
(
    `id`                    bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `parent_id`             bigint           NOT NULL COMMENT '父菜单 id',
    `perms`                 varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '权限标识',
    `order_no`              int                                                        DEFAULT '0' COMMENT '菜单排序',
    `path`                  varchar(500) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '路由地址',
    `title`                 varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单名字',
    `component`             varchar(300) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件路径',
    `ignore_keep_alive`     tinyint unsigned                                           DEFAULT '0' COMMENT '是否忽略缓存 (1 忽略，0 不忽略)',
    `affix`                 tinyint unsigned                                           DEFAULT '0' COMMENT '是否固定标签 (1 固定，0 不固定)',
    `icon`                  varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
    `frame_src`             varchar(500) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内嵌 iframe 的地址',
    `transition_name`       varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '该路由切换的动画名',
    `hide_breadcrumb`       tinyint unsigned                                           DEFAULT '0' COMMENT '是否在面包屑上隐藏该路由 (1 隐藏，0 不隐藏)',
    `hide_children_in_menu` tinyint unsigned                                           DEFAULT '0' COMMENT '是否隐藏所有子菜单 (1 隐藏，0 不隐藏)',
    `hide_tab`              tinyint unsigned                                           DEFAULT '0' COMMENT '当前路由不再标签页显示 (1 隐藏，0 不隐藏)',
    `hide_menu`             tinyint unsigned                                           DEFAULT '0' COMMENT '当前路由不再菜单显示 (1 隐藏，0 不隐藏)',
    `is_link`               tinyint unsigned                                           DEFAULT '0' COMMENT '是否是外链 ( 1 是，0 不是)',
    `status`                tinyint unsigned                                           DEFAULT '1' COMMENT '状态 (1 启用 0 禁用)',
    `is_frame`              tinyint unsigned                                           DEFAULT '0' COMMENT '是否是外链 1 是，0 不是',
    `create_by`             varchar(30) CHARACTER SET utf8mb3 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '创建者',
    `create_time`           datetime                                                   DEFAULT NULL COMMENT '创建时间',
    `update_by`             varchar(30) CHARACTER SET utf8mb3 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '修改者',
    `update_time`           datetime                                                   DEFAULT NULL COMMENT '修改时间',
    `menu_type`             tinyint unsigned NOT NULL COMMENT '菜单类型 ( 0 目录，1 菜单，2 按钮)',
    `remark`                varchar(500) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
    `is_deleted`            tinyint(1)                                                 DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    KEY `ums_menu_status_index` (`status`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 64
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu`
VALUES (1, 0, NULL, 0, 'dashboard', '工作面板', 'LAYOUT', 0, 0, 'bx:bx-home', NULL, NULL, 0, 0, 0, 0, 0, 1, 0, NULL,
        NULL, NULL, NULL, 0, NULL, 0),
       (2, 1, NULL, 0, 'analysis', '分析页', '/dashboard/analysis/index', 0, 0, 'bx:bx-home', NULL, NULL, 0, 0, 0, 0, 0,
        1, 0, NULL, NULL, NULL, NULL, 1, NULL, 0),
       (3, 1, NULL, 0, 'workbench', '工作台', '/dashboard/workbench/index', 0, 0, 'bx:bx-home', NULL, NULL, 0, 0, 0, 0,
        0, 1, 0, NULL, NULL, NULL, NULL, 1, NULL, 0),
       (5, 0, '', 0, 'system', '系统管理', 'LAYOUT', 0, 0, 'ion:settings-outline', NULL, NULL, 0, 0, 0, 0, 0, 1, 0,
        NULL, NULL, NULL, NULL, 0, NULL, 0),
       (6, 5, '', 0, 'account', '用户管理', '/sys/user/index', 0, 0, 'ant-design:user-outlined', NULL, NULL, 0, 0, 0, 0,
        0, 1, 0, NULL, NULL, NULL, '2022-05-15 15:05:53', 1, NULL, 0),
       (12, 5, '', 0, 'menu', '菜单/权限管理', '/sys/menu/index', 0, 0, 'ant-design:align-right-outlined', NULL, NULL,
        0, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, 1, NULL, 0),
       (20, 5, '', 2, 'dept', '部门管理', '/sys/dept/index', 0, 0, 'ant-design:deployment-unit-outlined', NULL, NULL, 0,
        0, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, 1, '部门管理', 0),
       (21, 5, '', 6, 'test', '测试页面', '/sys/test/index', 0, 0, 'ant-design:experiment-twotone', NULL, NULL, 0, 0, 0,
        0, 0, 1, 0, NULL, NULL, NULL, '2022-05-24 20:59:29', 1, NULL, 0),
       (22, 5, '', 1, 'role', '角色管理', '/sys/role/index', 0, 0, 'ant-design:user-outlined', NULL, NULL, 0, 0, 0, 0,
        0, 1, 0, NULL, NULL, NULL, NULL, 1, NULL, 0),
       (23, 6, 'sys:user:add', 0, NULL, '用户新增权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, NULL, NULL,
        NULL, '2022-05-15 21:12:01', 2, NULL, 0),
       (24, 6, 'sys:user:del', 1, NULL, '用户删除权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, NULL, NULL,
        NULL, NULL, 2, NULL, 0),
       (25, 6, 'sys:user:query', 2, NULL, '用户查询权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, NULL, NULL,
        NULL, '2022-05-15 20:50:43', 2, NULL, 0),
       (26, 6, 'sys:user:edit', 4, NULL, '用户修改权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, NULL, NULL,
        NULL, '2022-05-15 21:12:41', 2, NULL, 0),
       (28, 5, '', 5, '/sub', '下级部门管理', '/sys/sub/index', 0, 0, 'ant-design:audit-outlined', NULL, NULL, 0, 0, 0,
        0, 0, 1, 0, 'zhong', '2022-05-11 17:35:35', NULL, '2022-05-11 18:30:08', 1, '下级部门管理', 0),
       (29, 12, 'sys:menu:add', 0, NULL, '菜单新增权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 18:54:57', NULL, '2022-05-15 19:54:13', 2, NULL, 0),
       (30, 12, 'sys:menu:del', 1, NULL, '菜单删除权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 19:22:24', NULL, '2022-05-15 19:56:00', 2, NULL, 0),
       (31, 12, 'sys:menu:list', 0, NULL, '菜单查询权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 19:23:28', NULL, '2022-05-15 19:56:15', 2, NULL, 0),
       (32, 12, 'sys:menu:edit', 0, NULL, '菜单修改权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 19:25:21', NULL, '2022-05-15 19:56:30', 2, NULL, 0),
       (33, 22, 'sys:role:add', 0, NULL, '新增角色权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 19:34:39', NULL, '2022-05-15 19:42:14', 2, NULL, 0),
       (34, 22, 'sys:role:edit', 1, NULL, '修改角色权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 19:39:44', NULL, '2022-05-15 19:39:44', 2, NULL, 0),
       (35, 22, 'sys:role:list', 0, NULL, '查询角色权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:05:47', NULL, '2022-05-15 20:05:47', 2, NULL, 0),
       (36, 22, 'sys:role:del', 0, NULL, '删除角色权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:09:21', NULL, '2022-05-15 20:09:21', 2, NULL, 0),
       (37, 22, 'sys:role:perm', 0, NULL, '更改角色所拥有的权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0,
        'zhong', '2022-05-15 20:10:35', NULL, '2022-05-15 20:12:47', 2, NULL, 0),
       (38, 22, 'sys:role:detaperm', 6, NULL, '更改角色数据权限的权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1,
        0, 'zhong', '2022-05-15 20:14:34', NULL, '2022-05-15 20:14:34', 2, NULL, 0),
       (39, 20, 'sys:dept:list', 0, NULL, '部门查询权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:31:44', NULL, '2022-05-15 20:31:44', 2, NULL, 0),
       (40, 20, 'sys:dept:edit', 0, NULL, '部门修改权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:35:39', NULL, '2022-05-15 20:35:39', 2, NULL, 0),
       (41, 20, 'sys:dept:add', 0, NULL, '部门新增权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:38:38', NULL, '2022-05-15 20:38:38', 2, NULL, 0),
       (42, 20, 'sys:dept:del', 0, NULL, '部门删除权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:40:27', NULL, '2022-05-15 20:40:27', 2, NULL, 0),
       (43, 20, 'sys:dept:perm', 0, NULL, '部门权限修改', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-15 20:41:58', NULL, '2022-05-15 20:41:58', 2, NULL, 0),
       (44, 28, 'sys:deptManagement:list', 0, NULL, '下级部门信息查看', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1,
        0, 'zhong', '2022-05-15 21:04:21', NULL, '2022-05-15 21:07:55', 2, NULL, 0),
       (45, 28, 'sys:deptManagement:adduser', 0, NULL, '新增部门用户', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1,
        0, 'zhong', '2022-05-15 21:11:04', NULL, '2022-05-15 21:11:04', 2, NULL, 0),
       (46, 28, 'sys:deptManagement:addDeptRole', 0, NULL, '新增部门角色', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0,
        1, 0, 'zhong', '2022-05-15 21:14:21', NULL, '2022-05-15 21:14:21', 2, NULL, 0),
       (47, 28, 'sys:deptManagement:editUser', 0, NULL, '修改部门用户', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1,
        0, 'zhong', '2022-05-15 21:17:16', NULL, '2022-05-15 21:17:16', 2, NULL, 0),
       (48, 28, 'sys:deptManagement:userDeptrole', 0, NULL, '关联用户与部门角色', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0,
        0, 0, 1, 0, 'zhong', '2022-05-15 21:18:32', NULL, '2022-05-15 21:18:32', 2, NULL, 0),
       (49, 28, 'sys:deptManagement:deptroleperm', 0, NULL, '修改部门角色权限', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0,
        0, 0, 1, 0, 'zhong', '2022-05-15 21:19:54', NULL, '2022-05-15 21:19:54', 2, NULL, 0),
       (50, 0, '', 0, 'dome', '示例 dome', 'LAYOUT', 0, 0, 'ant-design:database-filled', NULL, NULL, 0, 0, 0, 0, 0, 1,
        0, 'zhong', '2022-05-24 21:01:54', NULL, '2022-05-24 21:01:54', 0, NULL, 0),
       (51, 50, '', 0, 'dome', 'Dome 测试', '/dome/test/index', 0, 0, 'ant-design:credit-card-outlined', NULL, NULL, 0,
        0, 0, 0, 0, 1, 0, 'zhong', '2022-05-24 21:19:35', NULL, '2022-05-27 17:35:17', 1, NULL, 0),
       (52, 51, 'dome:test:list', 0, NULL, 'dome 查询', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-24 21:21:04', NULL, '2022-05-27 17:31:39', 2, NULL, 0),
       (53, 51, 'dome:test:add', 0, NULL, 'dome 新增', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-27 17:30:54', NULL, '2022-05-27 17:30:54', 2, NULL, 0),
       (54, 51, 'dome:test:edit', 0, NULL, 'dome 修改', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-27 17:32:56', NULL, '2022-05-27 17:33:10', 2, NULL, 0),
       (55, 51, 'dome:test:del', 0, NULL, 'dome 删除', NULL, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-05-27 17:33:45', NULL, '2022-05-27 17:33:45', 2, NULL, 0),
       (58, 0, '', 4, 'personalCenter', '个人中心', '/sys/personalCenter/index', 0, 0,
        'ant-design:aliwangwang-outlined', NULL, NULL, 0, 1, 0, 0, 0, 1, 0, NULL, NULL, NULL, '2022-06-01 16:05:45', 1,
        NULL, 0),
       (60, 0, '', 6, '/doc', 'doc', NULL, 0, 0, 'ant-design:book-outlined', NULL, NULL, 0, 0, 0, 0, 0, 1, 0, 'zhong',
        '2022-06-01 16:30:06', NULL, '2022-06-01 16:30:06', 0, NULL, 0),
       (61, 60, '', 0, 'https://vvbin.cn/doc-next/guide/introduction.html', 'vben', NULL, 0, 0, NULL, NULL, NULL, 0, 0,
        0, 0, 0, 1, 0, 'zhong', '2022-06-01 17:02:04', NULL, '2022-06-01 17:02:04', 1, NULL, 0),
       (62, 60, '', 0, '/ant', 'ant', NULL, 0, 0, NULL, 'https://www.antdv.com/components/overview-cn/', NULL, 0, 0, 0,
        0, 0, 1, 1, 'zhong', '2022-06-01 17:43:31', NULL, '2022-06-01 17:43:31', 1, NULL, 0);
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录 id',
    `title`          varchar(50)      DEFAULT '' COMMENT '模块标题',
    `business_type`  int              DEFAULT '0' COMMENT '业务类型（0 其它 1 新增 2 修改 3 删除）',
    `method`         varchar(100)     DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10)      DEFAULT '' COMMENT '请求方式',
    `operator_type`  tinyint unsigned DEFAULT '0' COMMENT '操作类别（0 其它 1 后台用户 2 手机端用户）',
    `oper_name`      varchar(50)      DEFAULT '' COMMENT '操作人员',
    `dept_name`      varchar(50)      DEFAULT '' COMMENT '部门名称',
    `oper_url`       varchar(255)     DEFAULT '' COMMENT '请求 URL',
    `oper_ip`        int unsigned     DEFAULT NULL COMMENT '主机地址',
    `oper_location`  varchar(255)     DEFAULT '' COMMENT '操作地点',
    `oper_param`     varchar(2000)    DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000)    DEFAULT '' COMMENT '返回参数',
    `status`         tinyint unsigned DEFAULT '0' COMMENT '操作状态（0 正常 1 异常）',
    `error_msg`      varchar(2000)    DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime         DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role`
(
    `id`          bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '角色 id',
    `role_name`   varchar(30)      NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100)     NOT NULL COMMENT '角色权限字符串',
    `order_no`    int              NOT NULL COMMENT '显示顺序',
    `data_scope`  tinyint unsigned          DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `status`      tinyint unsigned NOT NULL DEFAULT '1' COMMENT '角色状态（1 启用 0 禁用）',
    `create_by`   varchar(64)               DEFAULT '' COMMENT '创建者',
    `create_time` datetime                  DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)               DEFAULT '' COMMENT '更新者',
    `update_time` datetime                  DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500)              DEFAULT NULL COMMENT '备注',
    `is_deleted`  tinyint(1)                DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    KEY `ums_role_status_index` (`status`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'admin', 1, 2, 1, '', NULL, '', NULL, NULL, 0),
       (2, '普通角色', 'zhong', 2, 2, 1, '', NULL, 'zhong', '2021-12-08 15:35:59', NULL, 0),
       (5, '仅本人数据权限', 'self', 3, 5, 1, 'zhong', '2022-05-24 20:18:36', '', '2022-05-24 22:33:58', NULL, 0),
       (6, '本部门权限', 'dept', 4, 3, 1, 'zhong', '2022-05-24 20:21:11', '', '2022-05-24 22:29:40', NULL, 0),
       (7, '本部门和下级部门权限', 'sub', 5, 4, 1, 'zhong', '2022-05-24 20:26:50', '', '2022-05-24 22:33:46', NULL, 0),
       (8, '自定义数据权限', 'custom', 6, 2, 1, 'zhong', '2022-05-24 20:27:25', '', '2022-05-24 22:33:33', NULL, 0),
       (9, '全部数据权限', 'all', 2, 1, 1, 'zhong', '2022-05-24 20:27:54', '', '2022-05-24 20:27:54', NULL, 0);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept`
(
    `id`      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `role_id` bigint unsigned NOT NULL COMMENT '角色 id',
    `dept_id` bigint unsigned NOT NULL COMMENT '部门 id',
    PRIMARY KEY (`id`),
    KEY `role_id` (`role_id`),
    KEY `dept_id` (`dept_id`),
    CONSTRAINT `sys_role_dept_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `sys_role_dept_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb3 COMMENT ='角色部门表 (用于做自定数据权限)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept`
    DISABLE KEYS */;
INSERT INTO `sys_role_dept`
VALUES (1, 2, 3),
       (4, 8, 1),
       (5, 8, 2),
       (6, 8, 5),
       (7, 8, 6);
/*!40000 ALTER TABLE `sys_role_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `role_id` bigint unsigned NOT NULL COMMENT '角色 id',
    `menu_id` bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `role_id` (`role_id`),
    KEY `menu_id` (`menu_id`),
    CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 37
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu`
    DISABLE KEYS */;
INSERT INTO `sys_role_menu`
VALUES (1, 2, 1),
       (2, 2, 2),
       (3, 2, 3),
       (4, 2, 5),
       (8, 2, 21),
       (9, 5, 50),
       (10, 5, 51),
       (11, 5, 52),
       (12, 6, 50),
       (13, 6, 51),
       (14, 6, 52),
       (15, 7, 50),
       (16, 7, 51),
       (17, 7, 52),
       (18, 8, 50),
       (19, 8, 51),
       (20, 8, 52),
       (21, 9, 50),
       (22, 9, 51),
       (23, 9, 52),
       (24, 2, 50),
       (25, 2, 51),
       (26, 2, 52),
       (27, 2, 33),
       (28, 2, 22),
       (29, 2, 34),
       (30, 2, 35),
       (31, 2, 36),
       (32, 2, 37),
       (33, 2, 38),
       (34, 2, 53),
       (35, 2, 54),
       (36, 2, 55);
/*!40000 ALTER TABLE `sys_role_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_dept`
--

DROP TABLE IF EXISTS `sys_user_dept`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_dept`
(
    `id`      bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `user_id` bigint unsigned NOT NULL COMMENT '用户 ID',
    `dept_id` bigint unsigned NOT NULL COMMENT '部门 id',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `dept_id` (`dept_id`),
    CONSTRAINT `sys_user_dept_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_admin` (`id`),
    CONSTRAINT `sys_user_dept_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_dept`
--

LOCK TABLES `sys_user_dept` WRITE;
/*!40000 ALTER TABLE `sys_user_dept`
    DISABLE KEYS */;
INSERT INTO `sys_user_dept`
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 7, 6),
       (4, 1, 2),
       (5, 1, 4),
       (6, 8, 1),
       (7, 9, 1),
       (8, 10, 10),
       (9, 11, 1);
/*!40000 ALTER TABLE `sys_user_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role`
(
    `id`      bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint unsigned NOT NULL COMMENT '角色 id',
    `role_id` bigint unsigned NOT NULL COMMENT '角色 id',
    PRIMARY KEY (`id`),
    KEY `role_id` (`role_id`),
    KEY `ums_user_role_user_id_index` (`user_id`),
    CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_admin` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES (1, 1, 1),
       (3, 7, 2),
       (11, 2, 2),
       (12, 8, 5),
       (13, 9, 7),
       (14, 10, 6),
       (15, 11, 9);
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_dome`
--

DROP TABLE IF EXISTS `test_dome`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_dome`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `student_no`  char(9)          DEFAULT NULL COMMENT '学生学号',
    `name`        varchar(30)      DEFAULT '' COMMENT '学生姓名',
    `sex`         tinyint unsigned DEFAULT '3' COMMENT '性别',
    `tel`         char(11)         DEFAULT '' COMMENT '电话号码',
    `class_no`    char(8)          DEFAULT '' COMMENT '班级',
    `create_by`   varchar(30)      DEFAULT '' COMMENT '创建者',
    `create_time` datetime         DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(30)      DEFAULT '' COMMENT '修改者',
    `update_time` datetime         DEFAULT NULL COMMENT '修改时间',
    `dept_id`     bigint unsigned  DEFAULT NULL COMMENT '部门 id',
    `user_id`     bigint unsigned  DEFAULT NULL COMMENT '用户 id',
    `remark`      varchar(500)     DEFAULT '' COMMENT '备注',
    `is_deleted`  tinyint(1)       DEFAULT '0' COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    KEY `dept_id` (`dept_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `test_dome_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
    CONSTRAINT `test_dome_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_admin` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb3 COMMENT ='测试表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_dome`
--

LOCK TABLES `test_dome` WRITE;
/*!40000 ALTER TABLE `test_dome`
    DISABLE KEYS */;
INSERT INTO `test_dome`
VALUES (1, '202102181', '钟舒艺', 0, '17607952136', 'Z210101', '钟舒艺', '2021-12-01 20:39:43', '钟舒艺',
        '2021-12-01 20:39:50', 1, 1, '', 0),
       (2, '202102200', '鲁俊男', 0, '15297934837', 'Z210101', '钟舒艺', '2021-12-01 20:44:04', '钟舒艺',
        '2021-12-01 20:44:44', 3, 1, '', 0),
       (3, '20210000', '龚韩', 0, '13907912130', 'Z210101', 'zhong', '2022-06-02 18:21:51', '', '2022-06-02 18:30:14',
        6, 1, '', 0);
/*!40000 ALTER TABLE `test_dome`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2023-03-11 21:49:29
