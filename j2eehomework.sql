# Host: localhost  (Version 5.7.23)
# Date: 2019-01-06 22:50:47
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "commodity"
#

DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `com_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` double(8,2) DEFAULT '0.00',
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`com_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "commodity"
#

INSERT INTO `commodity` VALUES (1,'古剑奇谭3',99.00,'国产单机的希望之光'),(2,'DotA2 Plus',24.00,'真正的免费游戏');

#
# Structure for table "order"
#

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `price` double(8,2) DEFAULT '0.00',
  `actual_payment` double(8,2) DEFAULT '0.00',
  `time` datetime DEFAULT NULL,
  `state` enum('paid','unpaid','canceled') DEFAULT 'unpaid',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

#
# Data for table "order"
#

INSERT INTO `order` VALUES (1,'xunner',123.00,123.00,'2018-12-24 23:49:26','unpaid'),(2,'xunner',495.00,346.50,'2018-12-24 23:49:59','unpaid'),(3,'xunner',294.00,205.80,'2018-12-24 23:53:52','unpaid'),(4,'xunner',1230.00,861.00,'2018-12-24 23:57:57','unpaid'),(5,'xunner',468.00,327.60,'2018-12-25 21:15:46','unpaid'),(6,'xunner',0.00,0.00,'2019-01-06 10:55:39','unpaid'),(8,'xunner',519.00,363.30,'2019-01-06 21:42:29','unpaid'),(9,'xunner',3000.00,2100.00,'2019-01-06 22:18:09','unpaid');

#
# Structure for table "order_com_map"
#

DROP TABLE IF EXISTS `order_com_map`;
CREATE TABLE `order_com_map` (
  `order_id` int(11) NOT NULL DEFAULT '0',
  `com_id` int(11) NOT NULL DEFAULT '0',
  `number` int(11) DEFAULT '0',
  PRIMARY KEY (`order_id`,`com_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "order_com_map"
#

INSERT INTO `order_com_map` VALUES (1,1,1),(1,2,1),(2,1,5),(3,1,2),(3,2,4),(4,1,10),(4,2,10),(5,1,4),(5,2,3),(8,1,5),(8,2,1),(9,1,24),(9,2,26);

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('xunner','123456');
