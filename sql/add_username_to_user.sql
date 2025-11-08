-- 为 gym_user 表添加 username 字段的 SQL 语句

-- 1. 添加 username 字段（允许为空，稍后填充数据）
ALTER TABLE `gym_user` 
ADD COLUMN `username` varchar(50) NULL COMMENT '用户名，用于登录' AFTER `user_id`;

-- 2. 为现有数据生成随机 username
-- 方案1：优先使用 user_id（转换为小写）作为 username
-- 如果 user_id 是字符串类型（如 M001, C001），直接使用
UPDATE `gym_user` 
SET `username` = LOWER(`user_id`) 
WHERE `username` IS NULL AND `user_id` IS NOT NULL;

-- 方案2：如果 user_id 为空或不是字符串格式，基于 name 和 id 生成
-- 生成格式：name首字母小写 + id，例如：z000001
UPDATE `gym_user` 
SET `username` = CONCAT(
    LOWER(SUBSTRING(`name`, 1, 1)), 
    LPAD(`id`, 6, '0')
)
WHERE `username` IS NULL 
  AND `id` IS NOT NULL 
  AND `name` IS NOT NULL;

-- 方案3：如果上述方案都不适用，使用 id 生成唯一 username
-- 生成格式：user + id（确保唯一性）
UPDATE `gym_user` 
SET `username` = CONCAT('user', LPAD(`id`, 6, '0'))
WHERE `username` IS NULL AND `id` IS NOT NULL;

-- 3. 处理重复的 username（如果存在）
-- 使用临时表来处理重复值，避免在同一表上同时 SELECT 和 UPDATE
CREATE TEMPORARY TABLE IF NOT EXISTS `temp_duplicate_usernames` AS
SELECT `username` FROM `gym_user` 
GROUP BY `username` HAVING COUNT(*) > 1;

-- 为重复的 username 重新生成（使用 id 确保唯一）
UPDATE `gym_user` 
SET `username` = CONCAT('user', LPAD(`id`, 6, '0'))
WHERE `username` IN (SELECT `username` FROM `temp_duplicate_usernames`);

-- 删除临时表
DROP TEMPORARY TABLE IF EXISTS `temp_duplicate_usernames`;

-- 4. 添加唯一索引（确保用户名唯一）
ALTER TABLE `gym_user` 
ADD UNIQUE INDEX `idx_username` (`username`);

-- 5. 将 username 字段设置为 NOT NULL（在确保所有数据都有值且唯一后）
ALTER TABLE `gym_user` 
MODIFY COLUMN `username` varchar(50) NOT NULL COMMENT '用户名';

