-- 为会籍表添加 membership_type_id 字段的 SQL 语句

-- 1. 添加 membership_type_id 字段
ALTER TABLE `gym_membership` 
ADD COLUMN `membership_type_id` bigint(20) NOT NULL COMMENT '会籍类型ID，外键' AFTER `user_id`;

-- 2. 添加索引
ALTER TABLE `gym_membership` 
ADD INDEX `idx_membership_type_id` (`membership_type_id`);

-- 3. 添加外键约束
ALTER TABLE `gym_membership` 
ADD CONSTRAINT `fk_membership_type` 
FOREIGN KEY (`membership_type_id`) REFERENCES `gym_membership_type`(`id`) ON DELETE RESTRICT;

-- 4. 如果需要为现有数据设置默认值（根据 membership_type 字段）
-- 注意：这里需要根据实际的套餐类型映射关系来设置
UPDATE `gym_membership` SET `membership_type_id` = 1 WHERE `membership_type` = 'month';
UPDATE `gym_membership` SET `membership_type_id` = 2 WHERE `membership_type` = 'quarter';
UPDATE `gym_membership` SET `membership_type_id` = 3 WHERE `membership_type` = 'half_year';
UPDATE `gym_membership` SET `membership_type_id` = 4 WHERE `membership_type` = 'year';
UPDATE `gym_membership` SET `membership_type_id` = 5 WHERE `membership_type` = 'visit_card';

-- 5. 可选：删除旧的 membership_type 字段（如果不再需要）
-- ALTER TABLE `gym_membership` DROP COLUMN `membership_type`;

-- 6. 可选：删除旧的索引（如果不再需要）
-- ALTER TABLE `gym_membership` DROP INDEX `idx_membership_type`;

