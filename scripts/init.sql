use fast_architecture;

CREATE TABLE `user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
    `name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户姓名',
    `account` varchar(64) NOT NULL DEFAULT '' COMMENT '登录账号',
    `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
    `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态  0:禁用  1:正常',
    `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
    `creator_code` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人编码',
    `updater_code` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人编码',
    `creator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人名称',
    `updater_name` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人名称',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_code` (`code`),
    UNIQUE KEY `uniq_account` (`account`)
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
      `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
      `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
      `parent_code` varchar(64) NOT NULL DEFAULT '' COMMENT '父级编码',
      `perm` varchar(255) NOT NULL DEFAULT '' COMMENT '权限,前端是路由,后端是接口路径',
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