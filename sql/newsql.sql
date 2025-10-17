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
  `status` varchar(20) NOT NULL DEFAULT 'active' COMMENT '状态：active-正常，inactive-停用，expired-过期',

  -- 系统字段
   create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  is_deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  deleted_time datetime NULL DEFAULT NULL COMMENT '删除时间',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='gym用户信息表';

-- 插入示例数据
INSERT INTO `gym_user` (`user_id`, `name`, `phone`, `email`, `password`, `gender`, `birth_date`, `role`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `deleted_time`) VALUES
-- 会员数据
('M001', '张三', '13800138001', 'zhangsan@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1990-05-15', 'member', 'active', 'admin', '2024-01-15 10:30:00', 'admin', '2024-01-15 10:30:00', 'VIP会员，健身经验丰富', 0, NULL),
('M002', '李四', '13800138002', 'lisi@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1988-12-03', 'member', 'active', 'admin', '2024-01-16 14:20:00', 'admin', '2024-01-16 14:20:00', '新会员，正在适应训练', 0, NULL),
('M003', '王五', '13800138003', 'wangwu@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1995-08-22', 'member', 'active', 'admin', '2024-01-17 09:15:00', 'admin', '2024-01-17 09:15:00', '瑜伽爱好者', 0, NULL),
('M004', '赵六', '13800138004', 'zhaoliu@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1992-03-10', 'member', 'inactive', 'admin', '2024-01-18 16:45:00', 'admin', '2024-01-20 11:30:00', '暂停会员资格', 0, NULL),
('M005', '孙七', '13800138005', 'sunqi@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1987-11-28', 'member', 'expired', 'admin', '2024-01-19 13:20:00', 'admin', '2024-01-25 10:15:00', '会员卡已过期', 0, NULL),

-- 教练数据
('C001', '刘教练', '13900139001', 'liujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1985-06-18', 'coach', 'active', 'admin', '2024-01-10 08:00:00', 'admin', '2024-01-10 08:00:00', '资深健身教练，擅长力量训练', 0, NULL),
('C002', '陈教练', '13900139002', 'chenjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1990-09-25', 'coach', 'active', 'admin', '2024-01-11 09:30:00', 'admin', '2024-01-11 09:30:00', '瑜伽教练，专业认证', 0, NULL),
('C003', '周教练', '13900139003', 'zhoujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1983-04-12', 'coach', 'active', 'admin', '2024-01-12 10:15:00', 'admin', '2024-01-12 10:15:00', '游泳教练，国家二级运动员', 0, NULL),
('C004', '吴教练', '13900139004', 'wujiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1988-07-30', 'coach', 'inactive', 'admin', '2024-01-13 14:20:00', 'admin', '2024-01-20 16:30:00', '暂时离职', 0, NULL)
