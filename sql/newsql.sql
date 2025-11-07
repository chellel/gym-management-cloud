-- 健身房管理系统 - 管理员表 (gym_admin)

CREATE TABLE `gym_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID，主键',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `name` varchar(100) NOT NULL COMMENT '真实姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `password` varchar(255) NOT NULL COMMENT '密码，加密存储',
  `role` varchar(20) NOT NULL DEFAULT 'admin' COMMENT '角色：super_admin-超级管理员，admin-普通管理员',
  `status` varchar(20) NOT NULL DEFAULT 'active' COMMENT '状态：active-正常，inactive-停用',

  -- 系统字段
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员用户表';

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




-- 会籍类型表 (gym_membership_type)
CREATE TABLE `gym_membership_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会籍类型ID，主键',
  `type_code` varchar(50) NOT NULL COMMENT '类型编码：month-月度会员、quarter-季度会员、half_year-半年会员、year-年度会员',
  `type_name` varchar(100) NOT NULL COMMENT '类型名称：月度会员、季度会员、半年会员、年度会员',
  `duration_days` int(11) NOT NULL COMMENT '有效期天数',
  `price` decimal(10,2) NOT NULL COMMENT '价格（元）',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价（元），用于显示折扣',
  `description` text DEFAULT NULL COMMENT '类型描述',
  `benefits` text DEFAULT NULL COMMENT '会员权益，JSON格式存储',
  `is_refundable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否可退款：0-不可退款，1-可退款',
  `refund_policy` text DEFAULT NULL COMMENT '退款政策说明',
  `status` varchar(20) NOT NULL DEFAULT 'active' COMMENT '状态：active-启用，inactive-停用',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',

  -- 系统字段
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',

  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_code` (`type_code`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`),
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会籍类型配置表';

-- 会籍表 (gym_membership)
CREATE TABLE `gym_membership` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会籍ID，主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID，外键',
  `membership_type_id` bigint NOT NULL COMMENT '会籍类型ID，外键',
  `start_date` date NOT NULL COMMENT '会籍开始日期',
  `expire_date` date NOT NULL COMMENT '会籍到期日期',
  `status` varchar(20) DEFAULT 'active' COMMENT '状态：active-正常，inactive-停用，expired-过期，suspended-暂停',
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
  KEY `idx_membership_type_id` (`membership_type_id`),
  KEY `idx_status` (`status`),
  KEY `idx_is_deleted` (`is_deleted`),
  FOREIGN KEY (`user_id`) REFERENCES `gym_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`membership_type_id`) REFERENCES `gym_membership_type`(`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会籍信息表';


-- 课程表 (gym_course)
CREATE TABLE `gym_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID，主键',
  `name` varchar(100) NOT NULL COMMENT '课程名称',
  `description` text DEFAULT NULL COMMENT '课程描述',
  `duration_minutes` int(11) NOT NULL DEFAULT 60 COMMENT '课程时长（分钟）',
  
  -- 系统字段
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  
  PRIMARY KEY (`id`),
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程信息表';


-- 排班表 (gym_schedule)
CREATE TABLE `gym_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '排班ID，主键',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID，外键',
  `coach_id` bigint(20) NOT NULL COMMENT '教练ID，外键',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `location` varchar(100) NOT NULL COMMENT '上课地点',
  `max_capacity` int(11) NOT NULL COMMENT '最大容量',
  `status` varchar(20) NOT NULL DEFAULT 'waiting' COMMENT '状态：waiting-待开始，cancelled-已取消，completed-已完成',
  
  -- 系统字段
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_coach_id` (`coach_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_status` (`status`),
  KEY `idx_is_deleted` (`is_deleted`),
  FOREIGN KEY (`course_id`) REFERENCES `gym_course`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`coach_id`) REFERENCES `gym_user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程排班表';



-- 课程预约表 (gym_booking)
CREATE TABLE `gym_booking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预约ID，主键',
  `schedule_id` bigint(20) NOT NULL COMMENT '排班ID，外键',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID，外键',
  `booking_time` datetime NOT NULL COMMENT '预约时间',
  `checkin_time` datetime DEFAULT NULL COMMENT '签到时间',
  `status` varchar(20) NOT NULL DEFAULT 'checked' COMMENT '状态：checked-已签到，未签到',
  -- 系统字段
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_schedule_member` (`schedule_id`, `member_id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_booking_time` (`booking_time`),
  KEY `idx_is_deleted` (`is_deleted`),
  FOREIGN KEY (`schedule_id`) REFERENCES `gym_schedule`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`member_id`) REFERENCES `gym_user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程预约表';









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





-- 插入会籍类型示例数据
INSERT INTO `gym_membership_type` (
  `type_code`, `type_name`, `duration_days`, `price`, `original_price`, 
  `description`, `benefits`, `is_refundable`, 
  `refund_policy`, `status`, `sort_order`, `remark`,
  `create_by`, `create_time`, `update_by`, `update_time`
) VALUES
-- 月度会员
('month', '月度会员', 30, 299.00, 399.00, 
 '适合短期体验的会员类型，包含基础健身设施使用权限', 
 '["免费使用所有健身器械", "免费参加团操课程", "免费使用更衣室和淋浴设施", "免费停车"]', 
 1, 
 '购买后7天内可申请退款，退款金额按使用天数扣除', 
 'active', 1, '月度会员，适合新用户体验', 
 'admin', NOW(), 'admin', NOW()),

-- 季度会员
('quarter', '季度会员', 90, 799.00, 999.00, 
 '三个月会员卡，性价比高，适合有规律健身习惯的用户', 
 '["免费使用所有健身器械", "免费参加团操课程", "免费使用更衣室和淋浴设施", "免费停车", "免费体测和健身计划制定", "优先预约热门课程"]', 
 1, 
 '购买后15天内可申请退款，退款金额按使用天数扣除', 
 'active', 2, '季度会员，性价比之选', 
 'admin', NOW(), 'admin', NOW()),

-- 半年会员
('half_year', '半年会员', 180, 1399.00, 1799.00, 
 '六个月会员卡，享受更多优惠和专属服务', 
 '["免费使用所有健身器械", "免费参加团操课程", "免费使用更衣室和淋浴设施", "免费停车", "免费体测和健身计划制定", "优先预约热门课程", "免费私教体验课1次", "生日月免费私教课1次"]', 
 1, 1, 
 '购买后30天内可申请退款，退款金额按使用天数扣除', 
 'active', 3, '半年会员，享受更多权益', 
 'admin', NOW(), 'admin', NOW()),

-- 年度会员
('year', '年度会员', 365, 2399.00, 2999.00, 
 '一年期会员卡，最优惠的价格，享受所有VIP服务', 
 '["免费使用所有健身器械", "免费参加团操课程", "免费使用更衣室和淋浴设施", "免费停车", "免费体测和健身计划制定", "优先预约热门课程", "免费私教体验课2次", "生日月免费私教课2次", "免费营养咨询", "专属储物柜", "免费毛巾服务", "生日礼品"]', 
 1, 
 '购买后60天内可申请退款，退款金额按使用天数扣除', 
 'active', 4, '年度会员，VIP尊享服务', 
 'admin', NOW(), 'admin', NOW()),

-- 次卡会员（限次使用）
('visit_card', '次卡会员', 365, 599.00, 699.00, 
 '按次计费的会员卡，适合不经常健身的用户', 
 '["单次使用所有健身器械", "单次参加团操课程", "单次使用更衣室和淋浴设施", "单次免费停车"]', 
 0, 
 '购买后30天内可申请退款，按剩余次数退款', 
 'active', 5, '次卡会员，按需消费', 
 'admin', NOW(), 'admin', NOW());

-- 插入会籍示例数据
INSERT INTO `gym_membership` (
  `user_id`, `membership_type_id`, `membership_type`, `start_date`, `expire_date`, 
  `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`
) VALUES
-- 张三的会籍记录（年度会员）
(1, 4, 'year', '2024-01-15', '2025-01-15', 'active', 'VIP年度会员，享受所有设施', 'admin', NOW(), 'admin', NOW()),
-- 李四的会籍记录（半年会员）
(2, 3, 'half_year', '2024-01-16', '2024-07-16', 'active', '新会员半年体验', 'admin', NOW(), 'admin', NOW()),
-- 王五的会籍记录（年度会员）
(3, 4, 'year', '2024-01-17', '2025-01-17', 'active', '瑜伽爱好者年度会员', 'admin', NOW(), 'admin', NOW()),
-- 赵六的会籍记录（季度会员，已停用）
(4, 2, 'quarter', '2024-01-18', '2024-04-18', 'inactive', '因个人原因暂停会员资格', 'admin', NOW(), 'admin', NOW()),
-- 孙七的会籍记录（月度会员，已过期）
(5, 1, 'month', '2024-01-19', '2024-02-19', 'expired', '月度会员已过期', 'admin', NOW(), 'admin', NOW()),
-- 钱八的会籍记录（年度会员，请假中）
(6, 4, 'year', '2024-01-20', '2025-01-20', 'leave', '因工作出差请假，会籍暂停', 'admin', NOW(), 'admin', NOW()),

-- 历史会籍记录示例
-- 张三的上一期会籍（半年会员，已过期）
(1, 3, 'half_year', '2023-07-15', '2024-01-15', 'expired', '上一期半年会员，已续费年度会员', 'admin', NOW(), 'admin', NOW()),
-- 李四的上一期会籍（月度会员，已过期）
(2, 1, 'month', '2023-12-16', '2024-01-16', 'expired', '月度会员升级为半年会员', 'admin', NOW(), 'admin', NOW()),
-- 王五的上一期会籍（季度会员，已过期）
(3, 2, 'quarter', '2023-10-17', '2024-01-17', 'expired', '季度会员升级为年度会员', 'admin', NOW(), 'admin', NOW());






INSERT INTO `gym_course` (
  `name`, `description`, `duration_minutes`, 
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`
) VALUES
-- 瑜伽类课程
('晨间瑜伽', '适合初学者的晨间瑜伽课程，帮助唤醒身体，开启美好一天。', 60, 'admin', NOW(), 'admin', NOW(), '适合初学者的瑜伽课程', 0, NULL),
('高温瑜伽', '在高温环境下进行瑜伽练习，深度排毒。', 60, 'admin', NOW(), 'admin', NOW(), '高温环境瑜伽练习', 0, NULL),
('瑜伽流', '流畅的瑜伽动作串联，提升身体柔韧性。', 60, 'admin', NOW(), 'admin', NOW(), '动态瑜伽课程', 0, NULL),
('冥想瑜伽', '结合冥想和瑜伽，净化心灵，放松身心。', 60, 'admin', NOW(), 'admin', NOW(), '冥想与瑜伽结合', 0, NULL),
('夜间瑜伽', '舒缓的夜间瑜伽，帮助放松，改善睡眠质量。', 60, 'admin', NOW(), 'admin', NOW(), '夜间放松瑜伽', 0, NULL),

-- 有氧运动课程
('动感单车', '高强度有氧运动，配合动感音乐，燃烧卡路里。', 60, 'admin', NOW(), 'admin', NOW(), '高强度有氧单车', 0, NULL),
('晨跑团', '户外晨跑，呼吸新鲜空气，提升心肺功能。', 60, 'admin', NOW(), 'admin', NOW(), '户外跑步训练', 0, NULL),
('有氧舞蹈', '跟随音乐节奏的有氧舞蹈，享受运动的乐趣。', 60, 'admin', NOW(), 'admin', NOW(), '舞蹈有氧运动', 0, NULL),
('Zumba舞蹈', '热情的拉丁舞蹈，在音乐中燃烧卡路里。', 60, 'admin', NOW(), 'admin', NOW(), '拉丁舞蹈健身', 0, NULL),

-- 力量训练课程
('力量训练基础', '专业力量训练指导，适合初学者，增强肌肉力量。', 60, 'admin', NOW(), 'admin', NOW(), '基础力量训练', 0, NULL),
('杠铃操', '使用杠铃进行全身训练，增强肌肉力量。', 60, 'admin', NOW(), 'admin', NOW(), '杠铃全身训练', 0, NULL),
('CrossFit训练', '高强度功能性训练，全面提升身体素质。', 60, 'admin', NOW(), 'admin', NOW(), '功能性训练', 0, NULL),

-- 格斗类课程
('拳击训练', '专业拳击技巧训练，提升反应速度和协调性。', 60, 'admin', NOW(), 'admin', NOW(), '拳击技巧训练', 0, NULL),
('泰拳训练', '传统泰拳技巧训练，提升格斗技能。', 60, 'admin', NOW(), 'admin', NOW(), '泰拳格斗训练', 0, NULL),

-- 核心训练课程
('普拉提核心', '专注于核心肌群训练，改善身体姿态和平衡。', 60, 'admin', NOW(), 'admin', NOW(), '核心肌群训练', 0, NULL),
('HIIT高强度训练', '高强度间歇训练，快速燃脂塑形。', 60, 'admin', NOW(), 'admin', NOW(), '高强度间歇训练', 0, NULL),

-- 游泳课程
('游泳训练', '专业游泳技巧指导，提升游泳水平。', 60, 'admin', NOW(), 'admin', NOW(), '游泳技巧指导', 0, NULL),
('水中健身', '在水中进行的有氧运动，对关节友好。', 60, 'admin', NOW(), 'admin', NOW(), '水中有氧运动', 0, NULL),

-- 传统运动课程
('太极课程', '传统太极拳练习，修身养性，强身健体。', 60, 'admin', NOW(), 'admin', NOW(), '传统太极拳', 0, NULL),

-- 拉伸放松课程
('拉伸放松', '深度拉伸课程，缓解肌肉紧张，促进恢复。', 30, 'admin', NOW(), 'admin', NOW(), '深度拉伸放松', 0, NULL);





-- 插入排班示例数据 (gym_schedule) - 2025-10-19 到 2025-10-25
INSERT INTO `gym_schedule` (
  `course_id`, `coach_id`, `start_time`, `end_time`, `location`, `max_capacity`, `status`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`
) VALUES
-- 瑜伽类课程排班
-- 晨间瑜伽 (course_id: 1) - 周一、三、五
(1, 7, '2025-10-20 07:00:00', '2025-10-20 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),
(1, 7, '2025-10-22 07:00:00', '2025-10-22 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),
(1, 7, '2025-10-24 07:00:00', '2025-10-24 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),

-- 高温瑜伽 (course_id: 2) - 周一、三、五
(2, 7, '2025-10-20 18:00:00', '2025-10-20 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),
(2, 7, '2025-10-22 18:00:00', '2025-10-22 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),
(2, 7, '2025-10-24 18:00:00', '2025-10-24 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),

-- 瑜伽流 (course_id: 3) - 周一、四
(3, 7, '2025-10-20 19:30:00', '2025-10-20 20:30:00', '瑜伽室B', 18, 'waiting', 'admin', NOW(), 'admin', NOW(), '瑜伽流课程', 0, NULL),
(3, 7, '2025-10-23 19:30:00', '2025-10-23 20:30:00', '瑜伽室B', 18, 'waiting', 'admin', NOW(), 'admin', NOW(), '瑜伽流课程', 0, NULL),

-- 冥想瑜伽 (course_id: 4) - 周二、五
(4, 7, '2025-10-21 20:00:00', '2025-10-21 21:00:00', '冥想室', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '冥想瑜伽课程', 0, NULL),
(4, 7, '2025-10-24 20:00:00', '2025-10-24 21:00:00', '冥想室', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '冥想瑜伽课程', 0, NULL),

-- 夜间瑜伽 (course_id: 5) - 周一、三
(5, 7, '2025-10-20 21:00:00', '2025-10-20 22:00:00', '瑜伽室A', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '夜间瑜伽课程', 0, NULL),
(5, 7, '2025-10-22 21:00:00', '2025-10-22 22:00:00', '瑜伽室A', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '夜间瑜伽课程', 0, NULL),

-- 有氧运动课程排班
-- 动感单车 (course_id: 6) - 周一、二、三
(6, 7, '2025-10-20 09:00:00', '2025-10-20 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),
(6, 7, '2025-10-21 09:00:00', '2025-10-21 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),
(6, 7, '2025-10-22 09:00:00', '2025-10-22 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),

-- 晨跑团 (course_id: 7) - 周一、三、五
(7, 7, '2025-10-20 06:30:00', '2025-10-20 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),
(7, 7, '2025-10-22 06:30:00', '2025-10-22 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),
(7, 7, '2025-10-24 06:30:00', '2025-10-24 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),

-- 有氧舞蹈 (course_id: 8) - 周一、四
(8, 7, '2025-10-20 19:00:00', '2025-10-20 20:00:00', '舞蹈室', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '有氧舞蹈课程', 0, NULL),
(8, 7, '2025-10-23 19:00:00', '2025-10-23 20:00:00', '舞蹈室', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '有氧舞蹈课程', 0, NULL),

-- Zumba舞蹈 (course_id: 9) - 周二、五
(9, 7, '2025-10-21 19:00:00', '2025-10-21 20:00:00', '舞蹈室', 22, 'waiting', 'admin', NOW(), 'admin', NOW(), 'Zumba舞蹈课程', 0, NULL),
(9, 7, '2025-10-24 19:00:00', '2025-10-24 20:00:00', '舞蹈室', 22, 'waiting', 'admin', NOW(), 'admin', NOW(), 'Zumba舞蹈课程', 0, NULL),

-- 力量训练课程排班
-- 力量训练基础 (course_id: 10) - 周一、三、五
(10, 7, '2025-10-20 10:30:00', '2025-10-20 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),
(10, 7, '2025-10-22 10:30:00', '2025-10-22 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),
(10, 7, '2025-10-24 10:30:00', '2025-10-24 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),

-- 杠铃操 (course_id: 11) - 周二、四
(11, 7, '2025-10-21 10:30:00', '2025-10-21 11:30:00', '杠铃区', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '杠铃操课程', 0, NULL),
(11, 7, '2025-10-23 10:30:00', '2025-10-23 11:30:00', '杠铃区', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '杠铃操课程', 0, NULL),

-- CrossFit训练 (course_id: 12) - 周一、三
(12, 7, '2025-10-20 18:30:00', '2025-10-20 19:30:00', 'CrossFit区', 10, 'waiting', 'admin', NOW(), 'admin', NOW(), 'CrossFit训练课程', 0, NULL),
(12, 7, '2025-10-22 18:30:00', '2025-10-22 19:30:00', 'CrossFit区', 10, 'waiting', 'admin', NOW(), 'admin', NOW(), 'CrossFit训练课程', 0, NULL),

-- 格斗类课程排班
-- 拳击训练 (course_id: 13) - 周一、四
(13, 7, '2025-10-20 20:00:00', '2025-10-20 21:00:00', '拳击台', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '拳击训练课程', 0, NULL),
(13, 7, '2025-10-23 20:00:00', '2025-10-23 21:00:00', '拳击台', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '拳击训练课程', 0, NULL),

-- 泰拳训练 (course_id: 14) - 周二、五
(14, 7, '2025-10-21 20:00:00', '2025-10-21 21:00:00', '格斗区', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '泰拳训练课程', 0, NULL),
(14, 7, '2025-10-24 20:00:00', '2025-10-24 21:00:00', '格斗区', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '泰拳训练课程', 0, NULL),

-- 核心训练课程排班
-- 普拉提核心 (course_id: 15) - 周一、三、五
(15, 7, '2025-10-20 11:00:00', '2025-10-20 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),
(15, 7, '2025-10-22 11:00:00', '2025-10-22 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),
(15, 7, '2025-10-24 11:00:00', '2025-10-24 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),

-- HIIT高强度训练 (course_id: 16) - 周二、四
(16, 7, '2025-10-21 11:00:00', '2025-10-21 12:00:00', 'HIIT训练区', 14, 'waiting', 'admin', NOW(), 'admin', NOW(), 'HIIT高强度训练', 0, NULL),
(16, 7, '2025-10-23 11:00:00', '2025-10-23 12:00:00', 'HIIT训练区', 14, 'waiting', 'admin', NOW(), 'admin', NOW(), 'HIIT高强度训练', 0, NULL),

-- 游泳课程排班
-- 游泳训练 (course_id: 17) - 周一、三、五
(17, 9, '2025-10-20 14:00:00', '2025-10-20 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),
(17, 9, '2025-10-22 14:00:00', '2025-10-22 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),
(17, 9, '2025-10-24 14:00:00', '2025-10-24 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),

-- 水中健身 (course_id: 18) - 周二、四
(18, 9, '2025-10-21 14:00:00', '2025-10-21 15:00:00', '游泳池', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '水中健身课程', 0, NULL),
(18, 9, '2025-10-23 14:00:00', '2025-10-23 15:00:00', '游泳池', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '水中健身课程', 0, NULL),

-- 传统运动课程排班
-- 太极课程 (course_id: 19) - 周一、三、五
(19, 7, '2025-10-20 08:30:00', '2025-10-20 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),
(19, 7, '2025-10-22 08:30:00', '2025-10-22 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),
(19, 7, '2025-10-24 08:30:00', '2025-10-24 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),

-- 拉伸放松课程排班
-- 拉伸放松 (course_id: 20) - 每天
(20, 7, '2025-10-20 12:30:00', '2025-10-20 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2025-10-21 12:30:00', '2025-10-21 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2025-10-22 12:30:00', '2025-10-22 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2025-10-23 12:30:00', '2025-10-23 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2025-10-24 12:30:00', '2025-10-24 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2025-10-25 12:30:00', '2025-10-25 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL);

-- 插入课程预约示例数据 (gym_booking)
INSERT INTO `gym_booking` (
  `schedule_id`, `member_id`, `booking_time`, `checkin_time`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`
) VALUES
-- 张三 (member_id: 1) 的预约
(52, 1, '2025-10-19 08:00:00', '2025-10-20 07:55:00', 'system', NOW(), 'system', NOW(), '晨间瑜伽预约', 0, NULL),
(57, 1, '2025-10-19 08:30:00', '2025-10-20 08:55:00', 'system', NOW(), 'system', NOW(), '动感单车预约', 0, NULL),
(61, 1, '2025-10-19 09:00:00', '2025-10-20 10:25:00', 'system', NOW(), 'system', NOW(), '力量训练基础预约', 0, NULL),
(66, 1, '2025-10-19 10:00:00', '2025-10-20 10:55:00', 'system', NOW(), 'system', NOW(), '普拉提核心预约', 0, NULL),

-- 李四 (member_id: 2) 的预约
(53, 2, '2025-10-19 09:00:00', '2025-10-20 18:55:00', 'system', NOW(), 'system', NOW(), '高温瑜伽预约', 0, NULL),
(58, 2, '2025-10-19 09:30:00', '2025-10-22 06:25:00', 'system', NOW(), 'system', NOW(), '晨跑团预约', 0, NULL),
(62, 2, '2025-10-19 10:00:00', '2025-10-21 10:25:00', 'system', NOW(), 'system', NOW(), '杠铃操预约', 0, NULL),
(67, 2, '2025-10-19 10:30:00', '2025-10-21 10:55:00', 'system', NOW(), 'system', NOW(), 'HIIT高强度训练预约', 0, NULL),

-- 王五 (member_id: 3) 的预约
(54, 3, '2025-10-19 11:00:00', '2025-10-20 19:25:00', 'system', NOW(), 'system', NOW(), '瑜伽流预约', 0, NULL),
(55, 3, '2025-10-19 11:30:00', '2025-10-21 19:55:00', 'system', NOW(), 'system', NOW(), '冥想瑜伽预约', 0, NULL),
(59, 3, '2025-10-19 12:00:00', '2025-10-20 18:55:00', 'system', NOW(), 'system', NOW(), '有氧舞蹈预约', 0, NULL),
(60, 3, '2025-10-19 12:30:00', '2025-10-21 18:55:00', 'system', NOW(), 'system', NOW(), 'Zumba舞蹈预约', 0, NULL),

-- 赵六 (member_id: 4) 的预约 - 部分已签到
(56, 4, '2025-10-19 13:00:00', '2025-10-20 20:55:00', 'system', NOW(), 'system', NOW(), '夜间瑜伽预约', 0, NULL),
(63, 4, '2025-10-19 13:30:00', '2025-10-20 18:25:00', 'system', NOW(), 'system', NOW(), 'CrossFit训练预约', 0, NULL),
(64, 4, '2025-10-19 14:00:00', NULL, 'system', NOW(), 'system', NOW(), '拳击训练预约（未签到）', 0, NULL),
(65, 4, '2025-10-19 14:30:00', NULL, 'system', NOW(), 'system', NOW(), '泰拳训练预约（未签到）', 0, NULL),

-- 孙七 (member_id: 5) 的预约 - 已过期会员
(68, 5, '2025-10-19 15:00:00', '2025-10-20 13:55:00', 'system', NOW(), 'system', NOW(), '游泳训练预约', 0, NULL),
(69, 5, '2025-10-19 15:30:00', '2025-10-21 13:55:00', 'system', NOW(), 'system', NOW(), '水中健身预约', 0, NULL),
(70, 5, '2025-10-19 16:00:00', '2025-10-20 08:25:00', 'system', NOW(), 'system', NOW(), '太极课程预约', 0, NULL),

-- 钱八 (member_id: 6) 的预约 - 请假状态
(71, 6, '2025-10-19 16:30:00', '2025-10-20 12:25:00', 'system', NOW(), 'system', NOW(), '拉伸放松预约', 0, NULL),
(52, 6, '2025-10-19 17:00:00', NULL, 'system', NOW(), 'system', NOW(), '晨间瑜伽预约（请假）', 0, NULL),
(57, 6, '2025-10-19 17:30:00', NULL, 'system', NOW(), 'system', NOW(), '动感单车预约（请假）', 0, NULL),

-- 一些取消的预约（软删除）
(53, 1, '2025-10-18 10:00:00', NULL, 'system', NOW(), 'system', NOW(), '高温瑜伽预约（已取消）', 1, '2025-10-19 09:00:00'),
(58, 3, '2025-10-18 10:30:00', NULL, 'system', NOW(), 'system', NOW(), '晨跑团预约（已取消）', 1, '2025-10-19 10:00:00'),
(61, 2, '2025-10-18 11:00:00', NULL, 'system', NOW(), 'system', NOW(), '力量训练基础预约（已取消）', 1, '2025-10-19 11:00:00');




-- ========================================
-- 追加更多教练数据
-- ========================================

-- 插入更多教练数据
INSERT INTO `gym_user` (`user_id`, `name`, `phone`, `email`, `password`, `gender`, `birth_date`, `role`, `status`, `experience`, `description`, `hire_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`) VALUES
-- 普拉提专业教练
('C005', '郑教练', '13900139005', 'zhengjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1987-03-15', 'coach', 'active', '8年', '资深普拉提教练，国际认证', '2024-01-14', 'admin', '2024-01-14 09:00:00', 'admin', '2024-01-14 09:00:00', '普拉提专业教练，帮助会员改善体态', 0, NULL),

-- 动感单车专业教练
('C006', '王教练', '13900139006', 'wangjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1992-11-08', 'coach', 'active', '5年', '动感单车教练，音乐节拍专家', '2024-01-15', 'admin', '2024-01-15 10:30:00', 'admin', '2024-01-15 10:30:00', '动感单车和团操课程专业教练', 0, NULL),

-- CrossFit专业教练
('C007', '李教练', '13900139007', 'lijiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1989-05-22', 'coach', 'active', '6年', 'CrossFit认证教练，功能性训练专家', '2024-01-16', 'admin', '2024-01-16 11:15:00', 'admin', '2024-01-16 11:15:00', 'CrossFit和功能性训练专业教练', 0, NULL),

-- 拳击专业教练
('C008', '张教练', '13900139008', 'zhangjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1991-08-14', 'coach', 'active', '4年', '拳击教练，前职业拳击手', '2024-01-17', 'admin', '2024-01-17 14:20:00', 'admin', '2024-01-17 14:20:00', '拳击和格斗训练专业教练', 0, NULL),

-- 游泳专业教练（国家一级运动员）
('C009', '赵教练', '13900139009', 'zhaojiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1986-12-03', 'coach', 'active', '9年', '游泳教练，国家一级运动员', '2024-01-18', 'admin', '2024-01-18 08:45:00', 'admin', '2024-01-18 08:45:00', '游泳和水上运动专业教练', 0, NULL),

-- 舞蹈专业教练
('C010', '孙教练', '13900139010', 'sunjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1993-07-19', 'coach', 'active', '3年', '舞蹈教练，现代舞专业', '2024-01-19', 'admin', '2024-01-19 16:30:00', 'admin', '2024-01-19 16:30:00', '舞蹈和形体训练专业教练', 0, NULL),

-- 康复训练专业教练
('C011', '马教练', '13900139011', 'majiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1984-09-27', 'coach', 'active', '10年', '康复训练师，运动医学背景', '2024-01-20', 'admin', '2024-01-20 09:15:00', 'admin', '2024-01-20 09:15:00', '康复训练和运动损伤预防专家', 0, NULL),

-- HIIT专业教练
('C012', '黄教练', '13900139012', 'huangjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1990-04-11', 'coach', 'active', '5年', 'HIIT教练，高强度训练专家', '2024-01-21', 'admin', '2024-01-21 13:45:00', 'admin', '2024-01-21 13:45:00', 'HIIT和高强度间歇训练专业教练', 0, NULL),

-- 太极专业教练
('C013', '林教练', '13900139013', 'linjiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1988-01-16', 'coach', 'active', '7年', '太极教练，传统武术传承人', '2024-01-22', 'admin', '2024-01-22 07:30:00', 'admin', '2024-01-22 07:30:00', '太极和传统武术专业教练', 0, NULL),

-- 新手友好型教练
('C014', '何教练', '13900139014', 'hejiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 0, '1994-06-08', 'coach', 'active', '2年', '新手教练，健身爱好者转专业', '2024-01-23', 'admin', '2024-01-23 15:20:00', 'admin', '2024-01-23 15:20:00', '新手友好型教练，适合初学者', 0, NULL),

-- 资深教练（暂时离职）
('C015', '罗教练', '13900139015', 'luojiaolian@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, '1985-10-25', 'coach', 'inactive', '12年', '资深教练，因个人原因暂时离职', '2024-01-24', 'admin', '2024-01-24 10:00:00', 'admin', '2024-01-25 12:00:00', '资深教练，暂时离职', 0, NULL);



ALTER TABLE `gym_membership` 
ADD COLUMN `membership_type_id` bigint(20) NOT NULL COMMENT '会籍类型ID，外键' AFTER `user_id`;

ALTER TABLE `gym_membership` 
ADD INDEX `idx_membership_type_id` (`membership_type_id`);

ALTER TABLE `gym_membership` 
ADD CONSTRAINT `fk_membership_type` 
FOREIGN KEY (`membership_type_id`) REFERENCES `gym_membership_type`(`id`) ON DELETE RESTRICT;

UPDATE `gym_membership` SET `membership_type_id` = 1 WHERE `membership_type` = 'month';
UPDATE `gym_membership` SET `membership_type_id` = 2 WHERE `membership_type` = 'quarter';
UPDATE `gym_membership` SET `membership_type_id` = 3 WHERE `membership_type` = 'half_year';
UPDATE `gym_membership` SET `membership_type_id` = 4 WHERE `membership_type` = 'year';
UPDATE `gym_membership` SET `membership_type_id` = 5 WHERE `membership_type` = 'visit_card';