DROP TABLE IF EXISTS `costom_skill`;
CREATE TABLE `costom_skill`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `post_id` BIGINT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);