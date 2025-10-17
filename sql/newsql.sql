-- 健身房管理系统 - 会员表 (gym_user)

CREATE TABLE `gym_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID，主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户编号，唯一标识',
  `name` varchar(100) NOT NULL COMMENT '用户姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `email` varchar(150) DEFAULT NULL COMMENT '邮箱地址',
  `password` varchar(255) NOT NULL COMMENT '密码，加密存储',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0-女，1-男',
  `birth_date` date DEFAULT NULL COMMENT '出生日期',
  `role` varchar(20) NOT NULL DEFAULT 'member' COMMENT '角色：member-会员，coach-教练',
  `status` varchar(20) NOT NULL DEFAULT 'active' COMMENT '状态：active-正常，inactive-停用，expired-过期，suspended-暂停',
  
  -- 教练专用字段
  `experience` varchar(50) DEFAULT NULL COMMENT '经验年限，如：5年',
  `description` text DEFAULT NULL COMMENT '个人简介',
  `hire_date` date DEFAULT NULL COMMENT '入职时间',

  -- 系统字段
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  is_deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  delete_time datetime NULL DEFAULT NULL COMMENT '删除时间',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='gym用户信息表';

-- 插入示例数据
INSERT INTO `gym_user` (`user_id`, `name`, `phone`, `email`, `password`, `gender`, `birth_date`, `role`, `status`, `experience`, `description`, `hire_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`) VALUES
-- 会员数据
('M001', '张三', '13800138001', 'zhangsan@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1990-05-15', 'member', 'active', NULL, NULL, NULL, 'admin', '2024-01-15 10:30:00', 'admin', '2024-01-15 10:30:00', 'VIP会员，健身经验丰富', 0, NULL),
('M002', '李四', '13800138002', 'lisi@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1988-12-03', 'member', 'active', NULL, NULL, NULL, 'admin', '2024-01-16 14:20:00', 'admin', '2024-01-16 14:20:00', '新会员，正在适应训练', 0, NULL),
('M003', '王五', '13800138003', 'wangwu@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1995-08-22', 'member', 'active', NULL, NULL, NULL, 'admin', '2024-01-17 09:15:00', 'admin', '2024-01-17 09:15:00', '瑜伽爱好者', 0, NULL),
('M004', '赵六', '13800138004', 'zhaoliu@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1992-03-10', 'member', 'inactive', NULL, NULL, NULL, 'admin', '2024-01-18 16:45:00', 'admin', '2024-01-20 11:30:00', '暂停会员资格', 0, NULL),
('M005', '孙七', '13800138005', 'sunqi@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1987-11-28', 'member', 'expired', NULL, NULL, NULL, 'admin', '2024-01-19 13:20:00', 'admin', '2024-01-25 10:15:00', '会员卡已过期', 0, NULL),
('M006', '钱八', '13800138006', 'qianba@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1993-06-15', 'member', 'leave', NULL, NULL, NULL, 'admin', '2024-01-20 09:30:00', 'admin', '2024-01-22 14:20:00', '因工作出差请假', 0, NULL),

-- 教练数据
('C001', '刘教练', '13900139001', 'liujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1985-06-18', 'coach', 'active', 'admin', '2024-01-10 08:00:00', 'admin', '2024-01-10 08:00:00', '资深健身教练，擅长力量训练', 0, NULL),
('C002', '陈教练', '13900139002', 'chenjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1990-09-25', 'coach', 'active', 'admin', '2024-01-11 09:30:00', 'admin', '2024-01-11 09:30:00', '瑜伽教练，专业认证', 0, NULL),
('C003', '周教练', '13900139003', 'zhoujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1983-04-12', 'coach', 'active', 'admin', '2024-01-12 10:15:00', 'admin', '2024-01-12 10:15:00', '游泳教练，国家二级运动员', 0, NULL),
('C004', '吴教练', '13900139004', 'wujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1988-07-30', 'coach', 'inactive', 'admin', '2024-01-13 14:20:00', 'admin', '2024-01-20 16:30:00', '暂时离职', 0, NULL)





-- 会籍表 (gym_membership)
CREATE TABLE `gym_membership` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会籍ID，主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID，外键',
  `membership_type` varchar(50) NOT NULL COMMENT '会籍类型：月度会员、季度会员、半年会员、年度会员、终身会员',
  `start_date` date NOT NULL COMMENT '会籍开始日期',
  `expire_date` date NOT NULL COMMENT '会籍到期日期',
  `status` varchar(20) DEFAULT 'active' COMMENT '状态：active-正常，inactive-停用，expired-过期，leave-请假',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  

  -- 系统字段
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',

  
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_membership_type` (`membership_type`),
  KEY `idx_status` (`status`),
  KEY `idx_is_deleted` (`is_deleted`),
  FOREIGN KEY (`user_id`) REFERENCES `gym_user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会籍信息表';



-- 插入会籍示例数据
INSERT INTO `gym_membership` (`user_id`, `membership_type`, `start_date`, `expire_date`, `status`, `remark`, `create_by`, `update_by`) VALUES
-- 张三的会籍记录
(1, 'year', '2024-01-15', '2025-01-15', 'active', 'VIP年度会员，享受所有设施', 'admin', 'admin'),
-- 李四的会籍记录
(2, 'half_year', '2024-01-16', '2024-07-16', 'active', '新会员半年体验', 'admin', 'admin'),
-- 王五的会籍记录
(3, 'year', '2024-01-17', '2025-01-17', 'active', '瑜伽爱好者年度会员', 'admin', 'admin'),
-- 赵六的会籍记录（已停用）
(4, 'quarter', '2024-01-18', '2024-04-18', 'inactive', '因个人原因暂停会员资格', 'admin', 'admin'),
-- 孙七的会籍记录（已过期）
(5, 'month', '2024-01-19', '2024-02-19', 'expired', '月度会员已过期', 'admin', 'admin'),
-- 钱八的会籍记录（请假中）
(6, 'year', '2024-01-20', '2025-01-20', 'leave', '因工作出差请假，会籍暂停', 'admin', 'admin'),

-- 历史会籍记录示例
-- 张三的上一期会籍（已过期）
(1, 'half_year', '2023-07-15', '2024-01-15', 'expired', '上一期半年会员，已续费年度会员', 'admin', 'admin'),
-- 李四的上一期会籍（已过期）
(2, 'month', '2023-12-16', '2024-01-16', 'expired', '月度会员升级为半年会员', 'admin', 'admin'),
-- 王五的上一期会籍（已过期）
(3, 'quarter', '2023-10-17', '2024-01-17', 'expired', '季度会员升级为年度会员', 'admin', 'admin');




