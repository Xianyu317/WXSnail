# Host: 127.0.01  (Version 5.7.10-log)
# Date: 2017-08-07 19:19:39
# Generator: MySQL-Front 5.3  (Build 6.26)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "feedback"
#

CREATE TABLE `feedback` (
  `feed_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `feed_content` varchar(255) DEFAULT '' COMMENT '建议内容',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：0删除，1正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='建议反馈';

#
# Data for table "feedback"
#

INSERT INTO `feedback` VALUES (1,1,'测试问题',1,'2017-07-30 18:07:23','2017-07-30 18:32:49'),(2,1,'水电费水电费',1,'2017-07-30 18:35:52','2017-07-30 21:54:01'),(3,NULL,'当然的故事帝国时代',1,'2017-07-30 19:19:54','2017-07-30 21:54:05'),(4,NULL,'水电费水电费',1,'2017-07-30 19:19:58','2017-07-30 19:19:58'),(5,NULL,'法国恢复和',1,'2017-07-30 19:20:02','2017-07-30 19:20:02'),(6,NULL,'法规的非官方',1,'2017-07-30 19:20:06','2017-07-30 19:20:06'),(7,NULL,'个人铁道部',1,'2017-07-30 19:20:12','2017-07-30 19:20:12'),(8,NULL,'活动覆盖',1,'2017-07-30 19:20:18','2017-07-30 19:20:18'),(9,NULL,'房管局环境规划',1,'2017-07-30 19:20:23','2017-07-30 19:20:23'),(10,NULL,'接口开发规划及法定',1,'2017-07-30 19:20:27','2017-07-30 19:20:27'),(11,NULL,'水电费十多个',1,'2017-07-30 19:20:30','2017-07-30 19:20:30'),(12,NULL,'；供货商东方',1,'2017-07-30 19:20:35','2017-07-30 19:20:35'),(13,NULL,'水电费水电费',1,'2017-07-30 19:20:39','2017-07-30 19:20:39'),(14,NULL,'水电费法规和地方',1,'2017-07-30 19:20:43','2017-07-30 19:20:43'),(15,NULL,'VB并不能设置VC',1,'2017-07-30 19:20:48','2017-07-30 19:20:48'),(16,NULL,'vcbcvhfghzsdfgdvv梵蒂冈',1,'2017-07-30 19:20:57','2017-07-30 19:20:57'),(17,NULL,'，鼎折覆餗规范',1,'2017-07-30 19:21:02','2017-07-30 19:21:02'),(18,NULL,'是的同时认为',1,'2017-07-30 19:21:06','2017-07-30 19:21:06'),(19,NULL,'色调若翁人',1,'2017-07-30 19:21:09','2017-07-30 19:21:11'),(20,NULL,'完完全全群群群群',1,'2017-07-30 19:21:17','2017-07-30 19:21:17'),(21,NULL,'发给发个非官方',1,'2017-07-30 19:21:22','2017-07-30 19:21:22');

#
# Structure for table "goods_type"
#

CREATE TABLE `goods_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_topid` int(11) DEFAULT NULL COMMENT '父ID',
  `is_parent` int(1) NOT NULL DEFAULT '0' COMMENT '是否总类别:0子类，1父类',
  `type_name` varchar(100) DEFAULT NULL COMMENT '类别名',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：0禁用，1可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型';

#
# Data for table "goods_type"
#

INSERT INTO `goods_type` VALUES (1,0,1,'蔬菜','蔬菜',1,'2017-08-01 16:41:23','2017-08-01 16:41:38');

#
# Structure for table "point_detail"
#

CREATE TABLE `point_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `point_id` int(11) DEFAULT NULL COMMENT '积分ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_id` int(11) DEFAULT NULL COMMENT '积分来源订单号',
  `point_value` int(11) NOT NULL DEFAULT '0' COMMENT '积分值',
  `remark` varchar(50) DEFAULT NULL COMMENT '积分来源备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态:0无效，1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分明细';

#
# Data for table "point_detail"
#


#
# Structure for table "scroll_graph"
#

CREATE TABLE `scroll_graph` (
  `graph_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `graph_path` varchar(100) DEFAULT '' COMMENT '图片路径',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态:0隐藏，1显示',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`graph_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播图';

#
# Data for table "scroll_graph"
#


#
# Structure for table "sys_auth"
#

CREATE TABLE `sys_auth` (
  `authId` int(11) NOT NULL AUTO_INCREMENT,
  `authName` varchar(20) DEFAULT NULL,
  `authPath` varchar(100) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `authDescription` varchar(200) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `iconCls` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`authId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='系统权限';

#
# Data for table "sys_auth"
#

INSERT INTO `sys_auth` VALUES (1,'小程序系统','',-1,NULL,'closed','icon-home'),(2,'系统管理','',1,NULL,'closed','icon-permission'),(3,'商品管理','',1,NULL,'closed','icon-student'),(4,'订单管理','',1,NULL,'closed','icon-course'),(5,'商品类别管理','goodsType.html',3,NULL,'open','icon-item'),(6,'商品信息管理','goodsManage.html',3,NULL,'open','icon-item'),(7,'用户订单','userOrder.html',4,NULL,'open','icon-item'),(8,'购物车管理','',1,NULL,'closed','icon-userManage'),(9,'用户购物车','userCart.html',8,NULL,'open','icon-item'),(10,'历史订单','oldUserOrder.html',4,NULL,'open','icon-item'),(11,'被删订单','deleteOrder.html',4,NULL,'open','icon-item'),(12,'管理员管理','userManage.html',2,NULL,'open','icon-userManage'),(13,'角色管理','roleManage.html',2,NULL,'open','icon-roleManage'),(14,'菜单管理','menuManage.html',2,NULL,'open','icon-menuManage'),(15,'积分管理',NULL,1,NULL,'closed','icon-course'),(16,'积分查看','pointManage.html',15,NULL,'open','icon-item'),(17,'意见反馈管理','feedback.html',1,NULL,'open','icon-item'),(18,'首页轮播图','scrollGraph.html',1,NULL,'open','icon-item'),(19,'修改密码','',1,NULL,'open','icon-modifyPassword'),(20,'安全退出','',1,NULL,'open','icon-exit');

#
# Structure for table "sys_role"
#

CREATE TABLE `sys_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) DEFAULT NULL,
  `authIds` varchar(100) DEFAULT NULL,
  `roleDescription` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='系统角色';

#
# Data for table "sys_role"
#

INSERT INTO `sys_role` VALUES (1,'超级管理员','1,2,12,13,14,3,5,6,7,8,4,9,10,11,17,15,16,17,18,19,20,21,22,23,24','具有最高权限'),(2,'管理员','1,3,5,6,7,8,15,16','管理信息'),(3,'普通员工','1.2','员工');

#
# Structure for table "sys_user"
#

CREATE TABLE `sys_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `userType` int(4) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `userDescription` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `FK_t_user` (`roleId`),
  CONSTRAINT `FK_t_user` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='系统管理员';

#
# Data for table "sys_user"
#

INSERT INTO `sys_user` VALUES (1,'java1234','1234',1,1,'最高管理员'),(2,'java','javacc',2,3,'普通员工'),(3,'zhangsan','javacc',2,2,'管理员'),(4,'lisi','123456',2,3,'这是一个管理员'),(39,'123','123',2,2,'123');


#
# Structure for table "user_cart"
#

CREATE TABLE `user_cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `quantity` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品数量',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：0，删除，1正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';

#
# Data for table "user_cart"
#


#
# Structure for table "user_order"
#

CREATE TABLE `user_order` (
  `order_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `address_id` int(11) DEFAULT NULL COMMENT '收货地址',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态：0未支付，1已支付，3、删除，4、退货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订单表';

#
# Data for table "user_order"
#



#
# Structure for table "user_order_detail"
#

CREATE TABLE `user_order_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '详细订单ID',
  `order_id` int(11) DEFAULT NULL COMMENT '主订单ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：1正常，0删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细';

#
# Data for table "user_order_detail"
#


#
# Structure for table "user_point"
#

CREATE TABLE `user_point` (
  `point_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `point_value` int(11) NOT NULL DEFAULT '0' COMMENT '积分值',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态:0:禁用，1可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`point_Id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分表';

#
# Data for table "user_point"
#

INSERT INTO `user_point` VALUES (1,1,222,1,'2017-08-07 13:44:49','2017-08-07 13:44:54');

#
# Structure for table "wx_user"
#

CREATE TABLE `wx_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` char(32) NOT NULL DEFAULT '' COMMENT '微信OpenID',
  `wxnick_name` varchar(50) DEFAULT NULL COMMENT '微信昵称',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实名字',
  `wx_address` varchar(100) DEFAULT NULL COMMENT '微信地址',
  `phone_num` char(11) DEFAULT NULL COMMENT '手机号码',
  `login_count` int(11) NOT NULL DEFAULT '1' COMMENT '登录次数',
  `remak` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态:0禁用，1正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Data for table "wx_user"
#

INSERT INTO `wx_user` VALUES (1,'o9xy7twhjjlpiMFDyMCqp42XFDTI','wwww','www','wwwww','2222',1,'www',1,'2017-08-01 13:43:42','2017-08-01 13:44:27');





###################新增#######################
#
# Structure for table "reserve_order"
#

CREATE TABLE `reserve_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `address_id` int(11) DEFAULT NULL COMMENT '收货地址ID',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态：0派送成功，1未派送，2派送中，3派送失败，4、取消订单',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约订单表';

#
# Data for table "reserve_order"
#

INSERT INTO `reserve_order` (`order_id`, `user_id`, `order_money`, `address_id`, `remark`, `state`, `create_time`, `update_time`) VALUES
(2, 100, '16.30', 10, NULL, 2, '2017-08-26 09:26:42', '2017-08-26 09:26:42'),
(3, 100, '16.30', 10, NULL, 2, '2017-08-26 09:26:56', '2017-08-26 09:26:56'),
(4, 100, '16.30', 10, NULL, 2, '2017-08-26 09:48:58', '2017-08-26 09:48:58'),
(5, 100, '16.30', 10, NULL, 2, '2017-08-26 09:51:08', '2017-08-26 09:51:08'),
(6, 100, '16.30', 10, NULL, 2, '2017-08-26 09:54:07', '2017-08-26 09:54:07'),
(7, 100, '16.30', 10, NULL, 2, '2017-08-27 09:35:23', '2017-08-27 09:35:23'),
(8, 100, '16.30', 10, NULL, 2, '2017-08-27 09:35:45', '2017-08-27 09:35:45'),
(9, 100, '16.30', 10, NULL, 2, '2017-08-27 09:36:56', '2017-08-27 09:36:56'),
(10, 100, '28.88', 10, NULL, 2, '2017-08-27 10:15:07', '2017-08-27 10:15:07');


#
# Structure for table "reserve_order_detail"
#

CREATE TABLE `reserve_order_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '详细订单ID',
  `order_id` int(11) DEFAULT NULL COMMENT '预约订单ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `buy_quantity` int(3) DEFAULT NULL COMMENT '购买数量',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约订单明细表';

#
# Data for table "reserve_order_detail"
#

INSERT INTO `reserve_order_detail` (`detail_id`, `order_id`, `goods_id`, `buy_quantity`, `remark`, `create_time`, `update_time`) VALUES
(1, 8, 2, 3, NULL, '2017-08-27 09:36:55', '2017-08-27 14:27:04'),
(2, 9, 1, 3, NULL, '2017-08-27 09:36:56', '2017-09-02 11:18:37'),
(3, 9, 2, 2, NULL, '2017-08-27 09:36:56', '2017-08-27 14:27:10'),
(4, 10, 1, 5, NULL, '2017-08-27 10:15:07', '2017-08-27 14:27:13');

#
# Structure for table "user_address"
#

CREATE TABLE `user_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：1-可用，0-删除',
  `isDefault` int(1) NOT NULL DEFAULT '0' COMMENT '默认地址: 0否，1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';

#
# Data for table "user_address"
#

INSERT INTO `user_address` (`address_id`, `user_id`, `name`, `mobile`, `address_detail`, `remark`, `state`, `isDefault`, `create_time`, `update_time`) VALUES
(10, 100, '张三', '18812345678', '广东省深圳市福田区车公庙劲松大厦808室', '公司地址', 1, 0, '2017-08-27 10:49:17', '2017-08-27 10:49:17');

#
# Structure for table "order_address"
#

CREATE TABLE `order_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单地址表';

#
# Data for table "order_address"
#

#
# Structure for table "goods_detail"
#

CREATE TABLE `goods_detail` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `type_id` int(11) DEFAULT NULL COMMENT '类别ID',
  `stock_size` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `company` int(1) NOT NULL DEFAULT '1' COMMENT '单位:1:斤，2：千克，3：两，4：克',
  `integral` int(5) DEFAULT NULL COMMENT '积分',
  `simple_des` varchar(100) NOT NULL DEFAULT '' COMMENT '简述',
  `detail_des` varchar(255) DEFAULT NULL COMMENT '详述',
  `master_img` varchar(100) DEFAULT NULL COMMENT '主图路径',
  `slave_img` varchar(100) DEFAULT NULL COMMENT '辅图路径',
  `carry_fare` smallint(6) DEFAULT '0' COMMENT '运费',
  `carry_fare_des` varchar(100) DEFAULT '' COMMENT '运费描述',
  `keyword` varchar(50) DEFAULT NULL COMMENT '关键字',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `state` int(3) NOT NULL DEFAULT '1' COMMENT '状态：1正常，0删除，2下架',
  `added_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

#
# Data for table "goods_detail"
#

INSERT INTO `goods_detail` VALUES (1,'土豆',2.50,1,200,1,10,'土豆土豆土豆土豆土豆土豆','土豆土豆土豆土豆土豆土豆土豆','image/tudou.png','image/tudou.png',0,'5斤包邮','土豆','土豆',1,'2017-09-02 17:21:22','2017-08-05 17:21:22','2017-08-05 17:21:49');
INSERT INTO `goods_detail` VALUES (2,'茄子',3.50,1,100,1,8,'茄子茄子茄子茄子茄子茄子','茄子茄子茄子茄子茄子茄子茄子茄子','image/tudou.png','image/tudou.png',0,'5斤包邮','茄子','茄子',1,'2017-09-02 17:21:22','2017-08-05 17:21:22','2017-08-05 17:21:49');
INSERT INTO `goods_detail` VALUES (3,'西红柿',1.50,1,100,1,null,'西红柿西红柿西红柿西红柿西红柿','西红柿西红柿西红柿西红柿西红柿','image/tudou.png','image/tudou.png',0,'5斤包邮','西红柿','西红柿',1,'2017-09-02 17:21:22','2017-08-05 17:21:22','2017-08-05 17:21:49');


