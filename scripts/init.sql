use fast_architecture;


CREATE TABLE `user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码,用户账号',
    `name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户姓名',
    `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
    `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态  0:禁用  1:正常',
    `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
    `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
    `updater_code` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人编码',
    `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
    `updater_name` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人名称',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


CREATE TABLE `role` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编码',
    `name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
    `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态  0:禁用  1:正常',
    `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
    `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
    `updater_code` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人编码',
    `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
    `updater_name` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人名称',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';


CREATE TABLE `permission` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
      `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码,前端是路由,后端是接口路径',
      `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
      `parent_code` varchar(64) NOT NULL DEFAULT '' COMMENT '父级编码',
      `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型 1:菜单 2:接口',
      `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
      `ext_config` json DEFAULT NULL COMMENT '额外配置,如前端相关属性',
      `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态  0:禁用  1:正常',
      `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
      `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
      `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
      `updater_code` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人编码',
      `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
      `updater_name` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人名称',
      PRIMARY KEY (`id`),
      UNIQUE KEY `uniq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';


CREATE TABLE `user_role` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
     `user_code` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
     `role_code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编码',
     `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
     `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
     `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uniq_user_role` (`user_code`, `role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

CREATE TABLE `role_permission` (
       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
       `role_code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编码',
       `permission_code` varchar(64) NOT NULL DEFAULT '' COMMENT '权限编码',
       `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
       `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
       `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
       PRIMARY KEY (`id`),
       UNIQUE KEY `uniq_role_permission` (`role_code`, `permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

CREATE TABLE `operation_log` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `module` varchar(64) NOT NULL DEFAULT '' COMMENT '操作模块',
     `type` varchar(64) NOT NULL DEFAULT '' COMMENT '操作类型',
     `target_id` varchar(64) NOT NULL DEFAULT '' COMMENT '操作对象ID',
     `ip` varchar(255) NOT NULL DEFAULT '' COMMENT 'IP',
     `params` json DEFAULT NULL COMMENT '参数',
     `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
     `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
     `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
     `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
     PRIMARY KEY (`id`),
     KEY `idx_module` (`module`),
     KEY `idx_type` (`type`),
     KEY `idx_target_id` (`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

CREATE TABLE `dict` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型',
    `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
    `parent_code` varchar(64) NOT NULL DEFAULT '' COMMENT '父级编码',
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
    `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
    `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态  0:禁用  1:正常',
    `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
    `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
    `updater_code` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人编码',
    `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
    `updater_name` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人名称',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_type_code` (`type`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- 插入用户
INSERT INTO `user` (`id`, `code`, `name`, `password`, `status`, `created_at`, `updated_at`, `creator_code`, `updater_code`, `creator_name`, `updater_name`)
VALUES
    (1, 'admin', '管理员', 'e10adc3949ba59abbe56e057f20f883e', 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统'),
    (3, 'user1', '用户1', 'e10adc3949ba59abbe56e057f20f883e', 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统');
-- 插入角色
INSERT INTO `role` (`id`, `code`, `name`, `status`, `created_at`, `updated_at`, `creator_code`, `updater_code`, `creator_name`, `updater_name`)
VALUES
    (1, 'SUPER_ROLE', '超级角色', 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统'),
    (2, 'NORMAL_ROLE', '普通角色', 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统');
-- 插入权限
INSERT INTO `permission` (`id`, `code`, `name`, `parent_code`, `type`, `weight`, `ext_config`, `status`, `created_at`, `updated_at`, `creator_code`, `updater_code`, `creator_name`, `updater_name`)
VALUES
    (1, '/admin/user/**', '用户管理所有权限', '', 2, 0, NULL, 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统'),
    (2, '/admin/user/page', '用户查询权限', '', 2, 0, NULL, 1, 1717592329000, 1717592329000, 'SYS', 'SYS', '系统', '系统');
-- 用户绑定角色
INSERT INTO `user_role` (`id`, `user_code`, `role_code`, `created_at`, `creator_code`, `creator_name`)
VALUES
    (1, 'admin', 'SUPER_ROLE', 1717592329000, 'SYS', '系统'),
    (2, 'user1', 'NORMAL_ROLE', 1717592329000, 'SYS', '系统');
-- 角色绑定权限
INSERT INTO `role_permission` (`id`, `role_code`, `permission_code`, `created_at`, `creator_code`, `creator_name`)
VALUES
    (1, 'SUPER_ROLE', '/admin/user/**', 1717592329000, 'SYS', '系统'),
    (2, 'NORMAL_ROLE', '/admin/user/page', 1717592329000, 'SYS', '系统');
