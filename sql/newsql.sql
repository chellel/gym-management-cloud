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
  `status` varchar(20) NOT NULL DEFAULT 'confirmed' COMMENT '状态：confirmed-已确认，cancelled-已取消，attended-已参加，absent-缺席',
  `checkin_time` datetime DEFAULT NULL COMMENT '签到时间',
  `notes` text DEFAULT NULL COMMENT '预约备注',
  
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
  KEY `idx_status` (`status`),
  KEY `idx_is_deleted` (`is_deleted`),
  FOREIGN KEY (`schedule_id`) REFERENCES `gym_schedule`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`member_id`) REFERENCES `gym_user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程预约表';










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





-- 插入排班示例数据 (gym_schedule)
INSERT INTO `gym_schedule` (
  `course_id`, `coach_id`, `start_time`, `end_time`, `location`, `max_capacity`, `status`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`
) VALUES
-- 瑜伽类课程排班
-- 晨间瑜伽 (course_id: 1)
(1, 7, '2024-02-01 07:00:00', '2024-02-01 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),
(1, 7, '2024-02-02 07:00:00', '2024-02-02 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),
(1, 7, '2024-02-03 07:00:00', '2024-02-03 08:00:00', '瑜伽室A', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨间瑜伽课程', 0, NULL),

-- 高温瑜伽 (course_id: 2)
(2, 7, '2024-02-01 18:00:00', '2024-02-01 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),
(2, 7, '2024-02-03 18:00:00', '2024-02-03 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),
(2, 7, '2024-02-05 18:00:00', '2024-02-05 19:00:00', '高温瑜伽室', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '高温瑜伽课程', 0, NULL),

-- 瑜伽流 (course_id: 3)
(3, 7, '2024-02-01 19:30:00', '2024-02-01 20:30:00', '瑜伽室B', 18, 'waiting', 'admin', NOW(), 'admin', NOW(), '瑜伽流课程', 0, NULL),
(3, 7, '2024-02-04 19:30:00', '2024-02-04 20:30:00', '瑜伽室B', 18, 'waiting', 'admin', NOW(), 'admin', NOW(), '瑜伽流课程', 0, NULL),

-- 冥想瑜伽 (course_id: 4)
(4, 7, '2024-02-02 20:00:00', '2024-02-02 21:00:00', '冥想室', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '冥想瑜伽课程', 0, NULL),
(4, 7, '2024-02-05 20:00:00', '2024-02-05 21:00:00', '冥想室', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '冥想瑜伽课程', 0, NULL),

-- 夜间瑜伽 (course_id: 5)
(5, 7, '2024-02-01 21:00:00', '2024-02-01 22:00:00', '瑜伽室A', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '夜间瑜伽课程', 0, NULL),
(5, 7, '2024-02-03 21:00:00', '2024-02-03 22:00:00', '瑜伽室A', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '夜间瑜伽课程', 0, NULL),

-- 有氧运动课程排班
-- 动感单车 (course_id: 6)
(6, 7, '2024-02-01 09:00:00', '2024-02-01 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),
(6, 7, '2024-02-02 09:00:00', '2024-02-02 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),
(6, 7, '2024-02-03 09:00:00', '2024-02-03 10:00:00', '单车室', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '动感单车课程', 0, NULL),

-- 晨跑团 (course_id: 7)
(7, 7, '2024-02-01 06:30:00', '2024-02-01 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),
(7, 7, '2024-02-03 06:30:00', '2024-02-03 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),
(7, 7, '2024-02-05 06:30:00', '2024-02-05 07:30:00', '户外跑道', 30, 'waiting', 'admin', NOW(), 'admin', NOW(), '晨跑团活动', 0, NULL),

-- 有氧舞蹈 (course_id: 8)
(8, 7, '2024-02-01 19:00:00', '2024-02-01 20:00:00', '舞蹈室', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '有氧舞蹈课程', 0, NULL),
(8, 7, '2024-02-04 19:00:00', '2024-02-04 20:00:00', '舞蹈室', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '有氧舞蹈课程', 0, NULL),

-- Zumba舞蹈 (course_id: 9)
(9, 7, '2024-02-02 19:00:00', '2024-02-02 20:00:00', '舞蹈室', 22, 'waiting', 'admin', NOW(), 'admin', NOW(), 'Zumba舞蹈课程', 0, NULL),
(9, 7, '2024-02-05 19:00:00', '2024-02-05 20:00:00', '舞蹈室', 22, 'waiting', 'admin', NOW(), 'admin', NOW(), 'Zumba舞蹈课程', 0, NULL),

-- 力量训练课程排班
-- 力量训练基础 (course_id: 10)
(10, 7, '2024-02-01 10:30:00', '2024-02-01 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),
(10, 7, '2024-02-03 10:30:00', '2024-02-03 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),
(10, 7, '2024-02-05 10:30:00', '2024-02-05 11:30:00', '力量训练区', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '力量训练基础课程', 0, NULL),

-- 杠铃操 (course_id: 11)
(11, 7, '2024-02-02 10:30:00', '2024-02-02 11:30:00', '杠铃区', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '杠铃操课程', 0, NULL),
(11, 7, '2024-02-04 10:30:00', '2024-02-04 11:30:00', '杠铃区', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '杠铃操课程', 0, NULL),

-- CrossFit训练 (course_id: 12)
(12, 7, '2024-02-01 18:30:00', '2024-02-01 19:30:00', 'CrossFit区', 10, 'waiting', 'admin', NOW(), 'admin', NOW(), 'CrossFit训练课程', 0, NULL),
(12, 7, '2024-02-03 18:30:00', '2024-02-03 19:30:00', 'CrossFit区', 10, 'waiting', 'admin', NOW(), 'admin', NOW(), 'CrossFit训练课程', 0, NULL),

-- 格斗类课程排班
-- 拳击训练 (course_id: 13)
(13, 7, '2024-02-01 20:00:00', '2024-02-01 21:00:00', '拳击台', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '拳击训练课程', 0, NULL),
(13, 7, '2024-02-04 20:00:00', '2024-02-04 21:00:00', '拳击台', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '拳击训练课程', 0, NULL),

-- 泰拳训练 (course_id: 14)
(14, 7, '2024-02-02 20:00:00', '2024-02-02 21:00:00', '格斗区', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '泰拳训练课程', 0, NULL),
(14, 7, '2024-02-05 20:00:00', '2024-02-05 21:00:00', '格斗区', 8, 'waiting', 'admin', NOW(), 'admin', NOW(), '泰拳训练课程', 0, NULL),

-- 核心训练课程排班
-- 普拉提核心 (course_id: 15)
(15, 7, '2024-02-01 11:00:00', '2024-02-01 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),
(15, 7, '2024-02-03 11:00:00', '2024-02-03 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),
(15, 7, '2024-02-05 11:00:00', '2024-02-05 12:00:00', '普拉提室', 16, 'waiting', 'admin', NOW(), 'admin', NOW(), '普拉提核心课程', 0, NULL),

-- HIIT高强度训练 (course_id: 16)
(16, 7, '2024-02-02 11:00:00', '2024-02-02 12:00:00', 'HIIT训练区', 14, 'waiting', 'admin', NOW(), 'admin', NOW(), 'HIIT高强度训练', 0, NULL),
(16, 7, '2024-02-04 11:00:00', '2024-02-04 12:00:00', 'HIIT训练区', 14, 'waiting', 'admin', NOW(), 'admin', NOW(), 'HIIT高强度训练', 0, NULL),

-- 游泳课程排班
-- 游泳训练 (course_id: 17)
(17, 9, '2024-02-01 14:00:00', '2024-02-01 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),
(17, 9, '2024-02-03 14:00:00', '2024-02-03 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),
(17, 9, '2024-02-05 14:00:00', '2024-02-05 15:00:00', '游泳池', 12, 'waiting', 'admin', NOW(), 'admin', NOW(), '游泳训练课程', 0, NULL),

-- 水中健身 (course_id: 18)
(18, 9, '2024-02-02 14:00:00', '2024-02-02 15:00:00', '游泳池', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '水中健身课程', 0, NULL),
(18, 9, '2024-02-04 14:00:00', '2024-02-04 15:00:00', '游泳池', 15, 'waiting', 'admin', NOW(), 'admin', NOW(), '水中健身课程', 0, NULL),

-- 传统运动课程排班
-- 太极课程 (course_id: 19)
(19, 7, '2024-02-01 08:30:00', '2024-02-01 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),
(19, 7, '2024-02-03 08:30:00', '2024-02-03 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),
(19, 7, '2024-02-05 08:30:00', '2024-02-05 09:30:00', '太极广场', 25, 'waiting', 'admin', NOW(), 'admin', NOW(), '太极课程', 0, NULL),

-- 拉伸放松课程排班
-- 拉伸放松 (course_id: 20)
(20, 7, '2024-02-01 12:30:00', '2024-02-01 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2024-02-02 12:30:00', '2024-02-02 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2024-02-03 12:30:00', '2024-02-03 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2024-02-04 12:30:00', '2024-02-04 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL),
(20, 7, '2024-02-05 12:30:00', '2024-02-05 13:00:00', '拉伸区', 20, 'waiting', 'admin', NOW(), 'admin', NOW(), '拉伸放松课程', 0, NULL);


-- 插入预约示例数据 (gym_booking)
INSERT INTO `gym_booking` (
  `schedule_id`, `member_id`, `booking_time`, `status`, `checkin_time`, `notes`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `is_deleted`, `delete_time`
) VALUES
-- 张三的预约记录 (member_id: 1)
(1, 1, '2024-01-30 10:00:00', 'confirmed', NULL, '期待晨间瑜伽课程', 'admin', NOW(), 'admin', NOW(), 'VIP会员预约', 0, NULL),
(4, 1, '2024-01-30 15:00:00', 'confirmed', NULL, '高温瑜伽体验', 'admin', NOW(), 'admin', NOW(), 'VIP会员预约', 0, NULL),
(10, 1, '2024-01-30 16:00:00', 'confirmed', NULL, '力量训练基础', 'admin', NOW(), 'admin', NOW(), 'VIP会员预约', 0, NULL),

-- 李四的预约记录 (member_id: 2)
(2, 2, '2024-01-30 11:00:00', 'confirmed', NULL, '新会员体验', 'admin', NOW(), 'admin', NOW(), '新会员预约', 0, NULL),
(6, 2, '2024-01-30 12:00:00', 'confirmed', NULL, '动感单车课程', 'admin', NOW(), 'admin', NOW(), '新会员预约', 0, NULL),
(15, 2, '2024-01-30 13:00:00', 'confirmed', NULL, '普拉提核心训练', 'admin', NOW(), 'admin', NOW(), '新会员预约', 0, NULL),

-- 王五的预约记录 (member_id: 3)
(1, 3, '2024-01-30 14:00:00', 'confirmed', NULL, '瑜伽爱好者', 'admin', NOW(), 'admin', NOW(), '瑜伽爱好者预约', 0, NULL),
(3, 3, '2024-01-30 15:30:00', 'confirmed', NULL, '瑜伽流课程', 'admin', NOW(), 'admin', NOW(), '瑜伽爱好者预约', 0, NULL),
(5, 3, '2024-01-30 16:30:00', 'confirmed', NULL, '夜间瑜伽', 'admin', NOW(), 'admin', NOW(), '瑜伽爱好者预约', 0, NULL),

-- 赵六的预约记录 (member_id: 4) - 已停用会员
(7, 4, '2024-01-30 17:00:00', 'cancelled', NULL, '因个人原因取消', 'admin', NOW(), 'admin', NOW(), '已停用会员', 0, NULL),

-- 孙七的预约记录 (member_id: 5) - 已过期会员
(8, 5, '2024-01-30 18:00:00', 'cancelled', NULL, '会员卡已过期', 'admin', NOW(), 'admin', NOW(), '已过期会员', 0, NULL),

-- 钱八的预约记录 (member_id: 6) - 请假中
(9, 6, '2024-01-30 19:00:00', 'cancelled', NULL, '因工作出差请假', 'admin', NOW(), 'admin', NOW(), '请假中会员', 0, NULL),

-- 一些已完成和已参加的预约记录
(1, 1, '2024-01-29 10:00:00', 'attended', '2024-02-01 07:05:00', '准时参加', 'admin', NOW(), 'admin', NOW(), '已完成课程', 0, NULL),
(4, 1, '2024-01-29 15:00:00', 'attended', '2024-02-01 18:02:00', '高温瑜伽体验', 'admin', NOW(), 'admin', NOW(), '已完成课程', 0, NULL),
(10, 1, '2024-01-29 16:00:00', 'attended', '2024-02-01 10:35:00', '力量训练', 'admin', NOW(), 'admin', NOW(), '已完成课程', 0, NULL),

-- 缺席记录
(2, 2, '2024-01-29 11:00:00', 'absent', NULL, '未按时参加', 'admin', NOW(), 'admin', NOW(), '缺席记录', 0, NULL),
(6, 2, '2024-01-29 12:00:00', 'absent', NULL, '临时有事', 'admin', NOW(), 'admin', NOW(), '缺席记录', 0, NULL),

-- 游泳课程预约
(17, 1, '2024-01-30 20:00:00', 'confirmed', NULL, '游泳训练', 'admin', NOW(), 'admin', NOW(), '游泳课程预约', 0, NULL),
(18, 2, '2024-01-30 21:00:00', 'confirmed', NULL, '水中健身', 'admin', NOW(), 'admin', NOW(), '水中健身预约', 0, NULL),

-- 格斗课程预约
(13, 1, '2024-01-30 22:00:00', 'confirmed', NULL, '拳击训练', 'admin', NOW(), 'admin', NOW(), '拳击课程预约', 0, NULL),
(14, 3, '2024-01-30 23:00:00', 'confirmed', NULL, '泰拳训练', 'admin', NOW(), 'admin', NOW(), '泰拳课程预约', 0, NULL),

-- 太极课程预约
(19, 1, '2024-01-31 08:00:00', 'confirmed', NULL, '太极课程', 'admin', NOW(), 'admin', NOW(), '太极课程预约', 0, NULL),
(19, 3, '2024-01-31 08:30:00', 'confirmed', NULL, '太极课程', 'admin', NOW(), 'admin', NOW(), '太极课程预约', 0, NULL),

-- 拉伸放松课程预约
(20, 1, '2024-01-31 12:00:00', 'confirmed', NULL, '拉伸放松', 'admin', NOW(), 'admin', NOW(), '拉伸课程预约', 0, NULL),
(20, 2, '2024-01-31 12:15:00', 'confirmed', NULL, '拉伸放松', 'admin', NOW(), 'admin', NOW(), '拉伸课程预约', 0, NULL),
(20, 3, '2024-01-31 12:30:00', 'confirmed', NULL, '拉伸放松', 'admin', NOW(), 'admin', NOW(), '拉伸课程预约', 0, NULL);

