DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order1`;
CREATE TABLE `order1` (
                          `id` bigint NOT NULL,
                          `product_count` int NOT NULL,
                          `product_id` bigint NOT NULL,
                          `product_size` varchar(255) DEFAULT NULL,
                          `user_id` bigint NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                           `id` bigint NOT NULL,
                           `available` bit(1) NOT NULL,
                           `count_available` int NOT NULL,
                           `details` varchar(255) DEFAULT NULL,
                           `img` varchar(255) DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `price` double NOT NULL,
                           `ratio` int NOT NULL,
                           `tag` varchar(255) DEFAULT NULL,
                           `type` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint NOT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `role` varchar(255) DEFAULT NULL,
                        `status` varchar(255) DEFAULT NULL,
                        `user_name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;