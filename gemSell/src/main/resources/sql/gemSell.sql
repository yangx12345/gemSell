/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : gemsell

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 25/04/2021 10:20:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `province` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市',
  `detailed` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `postcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `user_id` int(11) NOT NULL COMMENT '所属人id',
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '北京市', '东城区', '北京市', 'xxxx', '110101', 'xxx', '15128131729', 1);

-- ----------------------------
-- Table structure for authenticate
-- ----------------------------
DROP TABLE IF EXISTS `authenticate`;
CREATE TABLE `authenticate`  (
  `authenticate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '鉴定单id',
  `owner_id` int(11) NOT NULL COMMENT '请求人id',
  `owner_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求人名',
  `img_address` json NULL COMMENT '鉴品图片',
  `treasure_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鉴品名称',
  `treasure_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鉴品编码',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '分类id',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `texture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质地',
  `weight` double NULL DEFAULT NULL COMMENT '重量',
  `form_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产地',
  `auth_user_id` int(11) NULL DEFAULT NULL COMMENT '鉴定人id',
  `auth_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鉴定人名',
  `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鉴定结果',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `success_time` datetime(0) NULL DEFAULT NULL COMMENT '鉴定时间',
  PRIMARY KEY (`authenticate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '鉴定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authenticate
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车订单id',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  `good_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购物车状态，0代表在购物车中，1代表已结算',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `number` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `img_address` json NULL COMMENT '商品图片',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (1, 2, '南非钻石', '', 140.00, 1, 2, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `good_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `good_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类型id',
  `type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品介绍',
  `img_address` json NULL COMMENT '图片地址',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `purchase_price` decimal(10, 2) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL COMMENT '商品状态（0未发布，1已发布未售，2已售）',
  `total_number` int(11) NULL DEFAULT NULL COMMENT '商品总数量',
  `remain_number` int(11) NULL DEFAULT NULL COMMENT '商品剩余数量',
  PRIMARY KEY (`good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (2, '南非钻石', 4, '钻石', '南非钻石的故事可以追溯到 1870 年当时一个名为阿德里安·范·维克 Adriann van Wyk的农夫在北开普省金伯利Kimberley附近拥有一个农场 当他发现自己的孩子正在玩弄几颗钻石时一切便从此开始了。', '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', 140.00, 120.00, 1, 1, 2);

-- ----------------------------
-- Table structure for indent
-- ----------------------------
DROP TABLE IF EXISTS `indent`;
CREATE TABLE `indent`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  `good_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NOT NULL COMMENT '单价',
  `status` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，0待付款，1已付代发，2已发，3取消，4完成，5审核中，6拒绝',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `success_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `get_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `cancal_time` datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
  `num` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总价',
  `img_address` json NULL COMMENT '商品图片',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `address_id` int(11) NULL DEFAULT NULL COMMENT '地址id',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of indent
-- ----------------------------
INSERT INTO `indent` VALUES (3, 1, 'admin', 2, '南非钻石', 140.00, '4', '2021-04-21 11:01:50', '2021-04-21 13:20:12', NULL, NULL, NULL, 1, 140.00, NULL, NULL, NULL);
INSERT INTO `indent` VALUES (4, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-22 20:35:59', NULL, NULL, NULL, NULL, 1, 140.00, NULL, NULL, NULL);
INSERT INTO `indent` VALUES (5, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-22 20:36:16', NULL, NULL, NULL, NULL, 1, 140.00, NULL, NULL, NULL);
INSERT INTO `indent` VALUES (6, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-23 08:58:47', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (7, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-23 11:16:18', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (8, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 14:47:45', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (9, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 14:52:17', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (10, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 14:54:13', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (11, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 14:58:47', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (12, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 15:01:26', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (13, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 15:04:09', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (14, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 16:37:26', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (15, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 16:47:19', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (16, 1, 'admin', 2, '南非钻石', 140.00, '0', '2021-04-24 18:42:23', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (17, 1, 'admin', 2, '南非钻石', 140.00, '6', '2021-04-24 18:45:34', NULL, NULL, NULL, NULL, 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, NULL);
INSERT INTO `indent` VALUES (18, 1, 'admin', 2, '南非钻石', 140.00, '3', '2021-04-24 18:49:03', NULL, NULL, NULL, '2021-04-25 10:15:45', 1, 140.00, '[{\"url\": \"http://localhost:8088/gemsell-api/imgs/defaultImg.jpg\", \"name\": \"defaultImg.jpg\"}]', NULL, 1);

-- ----------------------------
-- Table structure for tb_city
-- ----------------------------
DROP TABLE IF EXISTS `tb_city`;
CREATE TABLE `tb_city`  (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `provinceid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '城市' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_city
-- ----------------------------
INSERT INTO `tb_city` VALUES (2, '天津市', 2);
INSERT INTO `tb_city` VALUES (3, '石家庄市', 3);
INSERT INTO `tb_city` VALUES (4, '唐山市', 3);
INSERT INTO `tb_city` VALUES (5, '秦皇岛市', 3);
INSERT INTO `tb_city` VALUES (6, '邯郸市', 3);
INSERT INTO `tb_city` VALUES (7, '邢台市', 3);
INSERT INTO `tb_city` VALUES (8, '保定市', 3);
INSERT INTO `tb_city` VALUES (9, '张家口市', 3);
INSERT INTO `tb_city` VALUES (10, '承德市', 3);
INSERT INTO `tb_city` VALUES (11, '沧州市', 3);
INSERT INTO `tb_city` VALUES (12, '廊坊市', 3);
INSERT INTO `tb_city` VALUES (13, '衡水市', 3);
INSERT INTO `tb_city` VALUES (14, '太原市', 4);
INSERT INTO `tb_city` VALUES (15, '大同市', 4);
INSERT INTO `tb_city` VALUES (16, '阳泉市', 4);
INSERT INTO `tb_city` VALUES (17, '长治市', 4);
INSERT INTO `tb_city` VALUES (18, '晋城市', 4);
INSERT INTO `tb_city` VALUES (19, '朔州市', 4);
INSERT INTO `tb_city` VALUES (20, '晋中市', 4);
INSERT INTO `tb_city` VALUES (21, '运城市', 4);
INSERT INTO `tb_city` VALUES (22, '忻州市', 4);
INSERT INTO `tb_city` VALUES (23, '临汾市', 4);
INSERT INTO `tb_city` VALUES (24, '吕梁市', 4);
INSERT INTO `tb_city` VALUES (25, '呼和浩特市', 5);
INSERT INTO `tb_city` VALUES (26, '包头市', 5);
INSERT INTO `tb_city` VALUES (27, '乌海市', 5);
INSERT INTO `tb_city` VALUES (28, '赤峰市', 5);
INSERT INTO `tb_city` VALUES (29, '通辽市', 5);
INSERT INTO `tb_city` VALUES (30, '鄂尔多斯市', 5);
INSERT INTO `tb_city` VALUES (31, '呼伦贝尔市', 5);
INSERT INTO `tb_city` VALUES (32, '巴彦淖尔市', 5);
INSERT INTO `tb_city` VALUES (33, '乌兰察布市', 5);
INSERT INTO `tb_city` VALUES (34, '兴安盟', 5);
INSERT INTO `tb_city` VALUES (35, '锡林郭勒盟', 5);
INSERT INTO `tb_city` VALUES (36, '阿拉善盟', 5);
INSERT INTO `tb_city` VALUES (37, '沈阳市', 6);
INSERT INTO `tb_city` VALUES (38, '大连市', 6);
INSERT INTO `tb_city` VALUES (39, '鞍山市', 6);
INSERT INTO `tb_city` VALUES (40, '抚顺市', 6);
INSERT INTO `tb_city` VALUES (41, '本溪市', 6);
INSERT INTO `tb_city` VALUES (42, '丹东市', 6);
INSERT INTO `tb_city` VALUES (43, '锦州市', 6);
INSERT INTO `tb_city` VALUES (44, '营口市', 6);
INSERT INTO `tb_city` VALUES (45, '阜新市', 6);
INSERT INTO `tb_city` VALUES (46, '辽阳市', 6);
INSERT INTO `tb_city` VALUES (47, '盘锦市', 6);
INSERT INTO `tb_city` VALUES (48, '铁岭市', 6);
INSERT INTO `tb_city` VALUES (49, '朝阳市', 6);
INSERT INTO `tb_city` VALUES (50, '葫芦岛市', 6);
INSERT INTO `tb_city` VALUES (51, '长春市', 7);
INSERT INTO `tb_city` VALUES (52, '吉林市', 7);
INSERT INTO `tb_city` VALUES (53, '四平市', 7);
INSERT INTO `tb_city` VALUES (54, '辽源市', 7);
INSERT INTO `tb_city` VALUES (55, '通化市', 7);
INSERT INTO `tb_city` VALUES (56, '白山市', 7);
INSERT INTO `tb_city` VALUES (57, '松原市', 7);
INSERT INTO `tb_city` VALUES (58, '白城市', 7);
INSERT INTO `tb_city` VALUES (59, '延边朝鲜族自治州', 7);
INSERT INTO `tb_city` VALUES (60, '哈尔滨市', 8);
INSERT INTO `tb_city` VALUES (61, '齐齐哈尔市', 8);
INSERT INTO `tb_city` VALUES (62, '鸡西市', 8);
INSERT INTO `tb_city` VALUES (63, '鹤岗市', 8);
INSERT INTO `tb_city` VALUES (64, '双鸭山市', 8);
INSERT INTO `tb_city` VALUES (65, '大庆市', 8);
INSERT INTO `tb_city` VALUES (66, '伊春市', 8);
INSERT INTO `tb_city` VALUES (67, '佳木斯市', 8);
INSERT INTO `tb_city` VALUES (68, '七台河市', 8);
INSERT INTO `tb_city` VALUES (69, '牡丹江市', 8);
INSERT INTO `tb_city` VALUES (70, '黑河市', 8);
INSERT INTO `tb_city` VALUES (71, '绥化市', 8);
INSERT INTO `tb_city` VALUES (72, '大兴安岭地区', 8);
INSERT INTO `tb_city` VALUES (73, '上海市', 9);
INSERT INTO `tb_city` VALUES (74, '南京市', 10);
INSERT INTO `tb_city` VALUES (75, '无锡市', 10);
INSERT INTO `tb_city` VALUES (76, '徐州市', 10);
INSERT INTO `tb_city` VALUES (77, '常州市', 10);
INSERT INTO `tb_city` VALUES (78, '苏州市', 10);
INSERT INTO `tb_city` VALUES (79, '南通市', 10);
INSERT INTO `tb_city` VALUES (80, '连云港市', 10);
INSERT INTO `tb_city` VALUES (81, '淮安市', 10);
INSERT INTO `tb_city` VALUES (82, '盐城市', 10);
INSERT INTO `tb_city` VALUES (83, '扬州市', 10);
INSERT INTO `tb_city` VALUES (84, '镇江市', 10);
INSERT INTO `tb_city` VALUES (85, '泰州市', 10);
INSERT INTO `tb_city` VALUES (86, '宿迁市', 10);
INSERT INTO `tb_city` VALUES (87, '杭州市', 11);
INSERT INTO `tb_city` VALUES (88, '宁波市', 11);
INSERT INTO `tb_city` VALUES (89, '温州市', 11);
INSERT INTO `tb_city` VALUES (90, '嘉兴市', 11);
INSERT INTO `tb_city` VALUES (91, '湖州市', 11);
INSERT INTO `tb_city` VALUES (92, '绍兴市', 11);
INSERT INTO `tb_city` VALUES (93, '金华市', 11);
INSERT INTO `tb_city` VALUES (94, '衢州市', 11);
INSERT INTO `tb_city` VALUES (95, '舟山市', 11);
INSERT INTO `tb_city` VALUES (96, '台州市', 11);
INSERT INTO `tb_city` VALUES (97, '丽水市', 11);
INSERT INTO `tb_city` VALUES (98, '合肥市', 12);
INSERT INTO `tb_city` VALUES (99, '芜湖市', 12);
INSERT INTO `tb_city` VALUES (100, '蚌埠市', 12);
INSERT INTO `tb_city` VALUES (101, '淮南市', 12);
INSERT INTO `tb_city` VALUES (102, '马鞍山市', 12);
INSERT INTO `tb_city` VALUES (103, '淮北市', 12);
INSERT INTO `tb_city` VALUES (104, '铜陵市', 12);
INSERT INTO `tb_city` VALUES (105, '安庆市', 12);
INSERT INTO `tb_city` VALUES (106, '黄山市', 12);
INSERT INTO `tb_city` VALUES (107, '滁州市', 12);
INSERT INTO `tb_city` VALUES (108, '阜阳市', 12);
INSERT INTO `tb_city` VALUES (109, '宿州市', 12);
INSERT INTO `tb_city` VALUES (110, '巢湖市', 12);
INSERT INTO `tb_city` VALUES (111, '六安市', 12);
INSERT INTO `tb_city` VALUES (112, '亳州市', 12);
INSERT INTO `tb_city` VALUES (113, '池州市', 12);
INSERT INTO `tb_city` VALUES (114, '宣城市', 12);
INSERT INTO `tb_city` VALUES (115, '福州市', 13);
INSERT INTO `tb_city` VALUES (116, '厦门市', 13);
INSERT INTO `tb_city` VALUES (117, '莆田市', 13);
INSERT INTO `tb_city` VALUES (118, '三明市', 13);
INSERT INTO `tb_city` VALUES (119, '泉州市', 13);
INSERT INTO `tb_city` VALUES (120, '漳州市', 13);
INSERT INTO `tb_city` VALUES (121, '南平市', 13);
INSERT INTO `tb_city` VALUES (122, '龙岩市', 13);
INSERT INTO `tb_city` VALUES (123, '宁德市', 13);
INSERT INTO `tb_city` VALUES (124, '南昌市', 14);
INSERT INTO `tb_city` VALUES (125, '景德镇市', 14);
INSERT INTO `tb_city` VALUES (126, '萍乡市', 14);
INSERT INTO `tb_city` VALUES (127, '九江市', 14);
INSERT INTO `tb_city` VALUES (128, '新余市', 14);
INSERT INTO `tb_city` VALUES (129, '鹰潭市', 14);
INSERT INTO `tb_city` VALUES (130, '赣州市', 14);
INSERT INTO `tb_city` VALUES (131, '吉安市', 14);
INSERT INTO `tb_city` VALUES (132, '宜春市', 14);
INSERT INTO `tb_city` VALUES (133, '抚州市', 14);
INSERT INTO `tb_city` VALUES (134, '上饶市', 14);
INSERT INTO `tb_city` VALUES (135, '济南市', 15);
INSERT INTO `tb_city` VALUES (136, '青岛市', 15);
INSERT INTO `tb_city` VALUES (137, '淄博市', 15);
INSERT INTO `tb_city` VALUES (138, '枣庄市', 15);
INSERT INTO `tb_city` VALUES (139, '东营市', 15);
INSERT INTO `tb_city` VALUES (140, '烟台市', 15);
INSERT INTO `tb_city` VALUES (141, '潍坊市', 15);
INSERT INTO `tb_city` VALUES (143, '泰安市', 15);
INSERT INTO `tb_city` VALUES (144, '威海市', 15);
INSERT INTO `tb_city` VALUES (145, '日照市', 15);
INSERT INTO `tb_city` VALUES (146, '莱芜市', 15);
INSERT INTO `tb_city` VALUES (147, '临沂市', 15);
INSERT INTO `tb_city` VALUES (148, '德州市', 15);
INSERT INTO `tb_city` VALUES (149, '聊城市', 15);
INSERT INTO `tb_city` VALUES (150, '滨州市', 15);
INSERT INTO `tb_city` VALUES (151, '荷泽市', 15);
INSERT INTO `tb_city` VALUES (152, '郑州市', 16);
INSERT INTO `tb_city` VALUES (153, '开封市', 16);
INSERT INTO `tb_city` VALUES (154, '洛阳市', 16);
INSERT INTO `tb_city` VALUES (155, '平顶山市', 16);
INSERT INTO `tb_city` VALUES (156, '安阳市', 16);
INSERT INTO `tb_city` VALUES (157, '鹤壁市', 16);
INSERT INTO `tb_city` VALUES (158, '新乡市', 16);
INSERT INTO `tb_city` VALUES (159, '焦作市', 16);
INSERT INTO `tb_city` VALUES (160, '濮阳市', 16);
INSERT INTO `tb_city` VALUES (161, '许昌市', 16);
INSERT INTO `tb_city` VALUES (162, '漯河市', 16);
INSERT INTO `tb_city` VALUES (163, '三门峡市', 16);
INSERT INTO `tb_city` VALUES (164, '南阳市', 16);
INSERT INTO `tb_city` VALUES (165, '商丘市', 16);
INSERT INTO `tb_city` VALUES (166, '信阳市', 16);
INSERT INTO `tb_city` VALUES (167, '周口市', 16);
INSERT INTO `tb_city` VALUES (168, '驻马店市', 16);
INSERT INTO `tb_city` VALUES (169, '武汉市', 17);
INSERT INTO `tb_city` VALUES (170, '黄石市', 17);
INSERT INTO `tb_city` VALUES (171, '十堰市', 17);
INSERT INTO `tb_city` VALUES (172, '宜昌市', 17);
INSERT INTO `tb_city` VALUES (173, '襄樊市', 17);
INSERT INTO `tb_city` VALUES (174, '鄂州市', 17);
INSERT INTO `tb_city` VALUES (175, '荆门市', 17);
INSERT INTO `tb_city` VALUES (176, '孝感市', 17);
INSERT INTO `tb_city` VALUES (177, '荆州市', 17);
INSERT INTO `tb_city` VALUES (178, '黄冈市', 17);
INSERT INTO `tb_city` VALUES (179, '咸宁市', 17);
INSERT INTO `tb_city` VALUES (180, '随州市', 17);
INSERT INTO `tb_city` VALUES (181, '恩施土家族苗族自治州', 17);
INSERT INTO `tb_city` VALUES (182, '神农架', 17);
INSERT INTO `tb_city` VALUES (183, '长沙市', 18);
INSERT INTO `tb_city` VALUES (184, '株洲市', 18);
INSERT INTO `tb_city` VALUES (185, '湘潭市', 18);
INSERT INTO `tb_city` VALUES (186, '衡阳市', 18);
INSERT INTO `tb_city` VALUES (187, '邵阳市', 18);
INSERT INTO `tb_city` VALUES (188, '岳阳市', 18);
INSERT INTO `tb_city` VALUES (189, '常德市', 18);
INSERT INTO `tb_city` VALUES (190, '张家界市', 18);
INSERT INTO `tb_city` VALUES (191, '益阳市', 18);
INSERT INTO `tb_city` VALUES (192, '郴州市', 18);
INSERT INTO `tb_city` VALUES (193, '永州市', 18);
INSERT INTO `tb_city` VALUES (194, '怀化市', 18);
INSERT INTO `tb_city` VALUES (195, '娄底市', 18);
INSERT INTO `tb_city` VALUES (196, '湘西土家族苗族自治州', 18);
INSERT INTO `tb_city` VALUES (197, '广州市', 19);
INSERT INTO `tb_city` VALUES (198, '韶关市', 19);
INSERT INTO `tb_city` VALUES (199, '深圳市', 19);
INSERT INTO `tb_city` VALUES (200, '珠海市', 19);
INSERT INTO `tb_city` VALUES (201, '汕头市', 19);
INSERT INTO `tb_city` VALUES (202, '佛山市', 19);
INSERT INTO `tb_city` VALUES (203, '江门市', 19);
INSERT INTO `tb_city` VALUES (204, '湛江市', 19);
INSERT INTO `tb_city` VALUES (205, '茂名市', 19);
INSERT INTO `tb_city` VALUES (206, '肇庆市', 19);
INSERT INTO `tb_city` VALUES (207, '惠州市', 19);
INSERT INTO `tb_city` VALUES (208, '梅州市', 19);
INSERT INTO `tb_city` VALUES (209, '汕尾市', 19);
INSERT INTO `tb_city` VALUES (210, '河源市', 19);
INSERT INTO `tb_city` VALUES (211, '阳江市', 19);
INSERT INTO `tb_city` VALUES (212, '清远市', 19);
INSERT INTO `tb_city` VALUES (213, '东莞市', 19);
INSERT INTO `tb_city` VALUES (214, '中山市', 19);
INSERT INTO `tb_city` VALUES (215, '潮州市', 19);
INSERT INTO `tb_city` VALUES (216, '揭阳市', 19);
INSERT INTO `tb_city` VALUES (217, '云浮市', 19);
INSERT INTO `tb_city` VALUES (218, '南宁市', 20);
INSERT INTO `tb_city` VALUES (219, '柳州市', 20);
INSERT INTO `tb_city` VALUES (220, '桂林市', 20);
INSERT INTO `tb_city` VALUES (221, '梧州市', 20);
INSERT INTO `tb_city` VALUES (222, '北海市', 20);
INSERT INTO `tb_city` VALUES (223, '防城港市', 20);
INSERT INTO `tb_city` VALUES (224, '钦州市', 20);
INSERT INTO `tb_city` VALUES (225, '贵港市', 20);
INSERT INTO `tb_city` VALUES (226, '玉林市', 20);
INSERT INTO `tb_city` VALUES (227, '百色市', 20);
INSERT INTO `tb_city` VALUES (228, '贺州市', 20);
INSERT INTO `tb_city` VALUES (229, '河池市', 20);
INSERT INTO `tb_city` VALUES (230, '来宾市', 20);
INSERT INTO `tb_city` VALUES (231, '崇左市', 20);
INSERT INTO `tb_city` VALUES (232, '海口市', 21);
INSERT INTO `tb_city` VALUES (233, '三亚市', 21);
INSERT INTO `tb_city` VALUES (234, '重庆市', 22);
INSERT INTO `tb_city` VALUES (235, '成都市', 23);
INSERT INTO `tb_city` VALUES (236, '自贡市', 23);
INSERT INTO `tb_city` VALUES (237, '攀枝花市', 23);
INSERT INTO `tb_city` VALUES (238, '泸州市', 23);
INSERT INTO `tb_city` VALUES (239, '德阳市', 23);
INSERT INTO `tb_city` VALUES (240, '绵阳市', 23);
INSERT INTO `tb_city` VALUES (241, '广元市', 23);
INSERT INTO `tb_city` VALUES (242, '遂宁市', 23);
INSERT INTO `tb_city` VALUES (243, '内江市', 23);
INSERT INTO `tb_city` VALUES (244, '乐山市', 23);
INSERT INTO `tb_city` VALUES (245, '南充市', 23);
INSERT INTO `tb_city` VALUES (246, '眉山市', 23);
INSERT INTO `tb_city` VALUES (247, '宜宾市', 23);
INSERT INTO `tb_city` VALUES (248, '广安市', 23);
INSERT INTO `tb_city` VALUES (249, '达州市', 23);
INSERT INTO `tb_city` VALUES (250, '雅安市', 23);
INSERT INTO `tb_city` VALUES (251, '巴中市', 23);
INSERT INTO `tb_city` VALUES (252, '资阳市', 23);
INSERT INTO `tb_city` VALUES (253, '阿坝藏族羌族自治州', 23);
INSERT INTO `tb_city` VALUES (254, '甘孜藏族自治州', 23);
INSERT INTO `tb_city` VALUES (255, '凉山彝族自治州', 23);
INSERT INTO `tb_city` VALUES (256, '贵阳市', 24);
INSERT INTO `tb_city` VALUES (257, '六盘水市', 24);
INSERT INTO `tb_city` VALUES (258, '遵义市', 24);
INSERT INTO `tb_city` VALUES (259, '安顺市', 24);
INSERT INTO `tb_city` VALUES (260, '铜仁地区', 24);
INSERT INTO `tb_city` VALUES (261, '黔西南布依族苗族自治州', 24);
INSERT INTO `tb_city` VALUES (262, '毕节地区', 24);
INSERT INTO `tb_city` VALUES (263, '黔东南苗族侗族自治州', 24);
INSERT INTO `tb_city` VALUES (264, '黔南布依族苗族自治州', 24);
INSERT INTO `tb_city` VALUES (265, '昆明市', 25);
INSERT INTO `tb_city` VALUES (266, '曲靖市', 25);
INSERT INTO `tb_city` VALUES (267, '玉溪市', 25);
INSERT INTO `tb_city` VALUES (268, '保山市', 25);
INSERT INTO `tb_city` VALUES (269, '昭通市', 25);
INSERT INTO `tb_city` VALUES (270, '丽江市', 25);
INSERT INTO `tb_city` VALUES (271, '思茅市', 25);
INSERT INTO `tb_city` VALUES (272, '临沧市', 25);
INSERT INTO `tb_city` VALUES (273, '楚雄彝族自治州', 25);
INSERT INTO `tb_city` VALUES (274, '红河哈尼族彝族自治州', 25);
INSERT INTO `tb_city` VALUES (275, '文山壮族苗族自治州', 25);
INSERT INTO `tb_city` VALUES (276, '西双版纳傣族自治州', 25);
INSERT INTO `tb_city` VALUES (277, '大理白族自治州', 25);
INSERT INTO `tb_city` VALUES (278, '德宏傣族景颇族自治州', 25);
INSERT INTO `tb_city` VALUES (279, '怒江傈僳族自治州', 25);
INSERT INTO `tb_city` VALUES (280, '迪庆藏族自治州', 25);
INSERT INTO `tb_city` VALUES (281, '拉萨市', 26);
INSERT INTO `tb_city` VALUES (282, '昌都地区', 26);
INSERT INTO `tb_city` VALUES (283, '山南地区', 26);
INSERT INTO `tb_city` VALUES (284, '日喀则地区', 26);
INSERT INTO `tb_city` VALUES (285, '那曲地区', 26);
INSERT INTO `tb_city` VALUES (286, '阿里地区', 26);
INSERT INTO `tb_city` VALUES (287, '林芝地区', 26);
INSERT INTO `tb_city` VALUES (288, '西安市', 27);
INSERT INTO `tb_city` VALUES (289, '铜川市', 27);
INSERT INTO `tb_city` VALUES (290, '宝鸡市', 27);
INSERT INTO `tb_city` VALUES (291, '咸阳市', 27);
INSERT INTO `tb_city` VALUES (292, '渭南市', 27);
INSERT INTO `tb_city` VALUES (293, '延安市', 27);
INSERT INTO `tb_city` VALUES (294, '汉中市', 27);
INSERT INTO `tb_city` VALUES (295, '榆林市', 27);
INSERT INTO `tb_city` VALUES (296, '安康市', 27);
INSERT INTO `tb_city` VALUES (297, '商洛市', 27);
INSERT INTO `tb_city` VALUES (298, '兰州市', 28);
INSERT INTO `tb_city` VALUES (299, '嘉峪关市', 28);
INSERT INTO `tb_city` VALUES (300, '金昌市', 28);
INSERT INTO `tb_city` VALUES (301, '白银市', 28);
INSERT INTO `tb_city` VALUES (302, '天水市', 28);
INSERT INTO `tb_city` VALUES (303, '武威市', 28);
INSERT INTO `tb_city` VALUES (304, '张掖市', 28);
INSERT INTO `tb_city` VALUES (305, '平凉市', 28);
INSERT INTO `tb_city` VALUES (306, '酒泉市', 28);
INSERT INTO `tb_city` VALUES (307, '庆阳市', 28);
INSERT INTO `tb_city` VALUES (308, '定西市', 28);
INSERT INTO `tb_city` VALUES (309, '陇南市', 28);
INSERT INTO `tb_city` VALUES (310, '临夏回族自治州', 28);
INSERT INTO `tb_city` VALUES (311, '甘南藏族自治州', 28);
INSERT INTO `tb_city` VALUES (312, '西宁市', 29);
INSERT INTO `tb_city` VALUES (313, '海东地区', 29);
INSERT INTO `tb_city` VALUES (314, '海北藏族自治州', 29);
INSERT INTO `tb_city` VALUES (315, '黄南藏族自治州', 29);
INSERT INTO `tb_city` VALUES (316, '海南藏族自治州', 29);
INSERT INTO `tb_city` VALUES (317, '果洛藏族自治州', 29);
INSERT INTO `tb_city` VALUES (318, '玉树藏族自治州', 29);
INSERT INTO `tb_city` VALUES (319, '海西蒙古族藏族自治州', 29);
INSERT INTO `tb_city` VALUES (320, '银川市', 30);
INSERT INTO `tb_city` VALUES (321, '石嘴山市', 30);
INSERT INTO `tb_city` VALUES (322, '吴忠市', 30);
INSERT INTO `tb_city` VALUES (323, '固原市', 30);
INSERT INTO `tb_city` VALUES (324, '中卫市', 30);
INSERT INTO `tb_city` VALUES (325, '乌鲁木齐市', 31);
INSERT INTO `tb_city` VALUES (326, '克拉玛依市', 31);
INSERT INTO `tb_city` VALUES (327, '吐鲁番地区', 31);
INSERT INTO `tb_city` VALUES (328, '哈密地区', 31);
INSERT INTO `tb_city` VALUES (330, '博尔塔拉蒙古自治州', 31);
INSERT INTO `tb_city` VALUES (331, '巴音郭楞蒙古自治州', 31);
INSERT INTO `tb_city` VALUES (332, '阿克苏地区', 31);
INSERT INTO `tb_city` VALUES (333, '克孜勒苏柯尔克孜自治州', 31);
INSERT INTO `tb_city` VALUES (334, '喀什地区', 31);
INSERT INTO `tb_city` VALUES (335, '和田地区', 31);
INSERT INTO `tb_city` VALUES (336, '伊犁哈萨克自治州', 31);
INSERT INTO `tb_city` VALUES (337, '塔城地区', 31);
INSERT INTO `tb_city` VALUES (338, '阿勒泰地区', 31);
INSERT INTO `tb_city` VALUES (339, '石河子市', 31);
INSERT INTO `tb_city` VALUES (340, '阿拉尔市', 31);
INSERT INTO `tb_city` VALUES (341, '图木舒克市', 31);
INSERT INTO `tb_city` VALUES (342, '五家渠市', 31);
INSERT INTO `tb_city` VALUES (343, '香港特别行政区', 32);
INSERT INTO `tb_city` VALUES (344, '澳门特别行政区', 33);
INSERT INTO `tb_city` VALUES (345, '台湾省', 34);

-- ----------------------------
-- Table structure for tb_province
-- ----------------------------
DROP TABLE IF EXISTS `tb_province`;
CREATE TABLE `tb_province`  (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省份' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_province
-- ----------------------------
INSERT INTO `tb_province` VALUES (1, '北京市');
INSERT INTO `tb_province` VALUES (3, '河北省');
INSERT INTO `tb_province` VALUES (4, '山西省');
INSERT INTO `tb_province` VALUES (5, '内蒙古自治区');
INSERT INTO `tb_province` VALUES (6, '辽宁省');
INSERT INTO `tb_province` VALUES (7, '吉林省');
INSERT INTO `tb_province` VALUES (8, '黑龙江省');
INSERT INTO `tb_province` VALUES (9, '上海市');
INSERT INTO `tb_province` VALUES (10, '江苏省');
INSERT INTO `tb_province` VALUES (11, '浙江省');
INSERT INTO `tb_province` VALUES (12, '安徽省');
INSERT INTO `tb_province` VALUES (13, '福建省');
INSERT INTO `tb_province` VALUES (14, '江西省');
INSERT INTO `tb_province` VALUES (15, '山东省');
INSERT INTO `tb_province` VALUES (16, '河南省');
INSERT INTO `tb_province` VALUES (17, '湖北省');
INSERT INTO `tb_province` VALUES (18, '湖南省');
INSERT INTO `tb_province` VALUES (19, '广东省');
INSERT INTO `tb_province` VALUES (20, '广西壮族自治区');
INSERT INTO `tb_province` VALUES (21, '海南省');
INSERT INTO `tb_province` VALUES (22, '重庆市');
INSERT INTO `tb_province` VALUES (23, '四川省');
INSERT INTO `tb_province` VALUES (24, '贵州省');
INSERT INTO `tb_province` VALUES (25, '云南省');
INSERT INTO `tb_province` VALUES (26, '西藏自治区');
INSERT INTO `tb_province` VALUES (27, '陕西省');
INSERT INTO `tb_province` VALUES (28, '甘肃省');
INSERT INTO `tb_province` VALUES (29, '青海省');
INSERT INTO `tb_province` VALUES (30, '宁夏回族自治区');
INSERT INTO `tb_province` VALUES (31, '新疆维吾尔自治区');
INSERT INTO `tb_province` VALUES (32, '香港特别行政区');
INSERT INTO `tb_province` VALUES (33, '澳门特别行政区');
INSERT INTO `tb_province` VALUES (34, '台湾省');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `type_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '一级分类父id为0',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (2, '精品红宝石', 1);
INSERT INTO `type` VALUES (4, '钻石', 0);
INSERT INTO `type` VALUES (5, '彩色宝石', 0);
INSERT INTO `type` VALUES (6, '玉石', 0);
INSERT INTO `type` VALUES (8, '玛瑙', 0);
INSERT INTO `type` VALUES (9, '石', 0);
INSERT INTO `type` VALUES (10, '晶', 0);
INSERT INTO `type` VALUES (11, '珊瑚', 0);
INSERT INTO `type` VALUES (12, '珠', 0);
INSERT INTO `type` VALUES (13, '珍珠', 12);
INSERT INTO `type` VALUES (14, '养珠', 12);
INSERT INTO `type` VALUES (15, '红珊瑚', 11);
INSERT INTO `type` VALUES (16, '白珊瑚', 11);
INSERT INTO `type` VALUES (17, '白水晶', 10);
INSERT INTO `type` VALUES (18, '紫水晶', 10);
INSERT INTO `type` VALUES (19, '黄水晶', 10);
INSERT INTO `type` VALUES (20, '紫黄晶', 10);
INSERT INTO `type` VALUES (21, '红水晶', 10);
INSERT INTO `type` VALUES (22, '蓝水晶', 10);
INSERT INTO `type` VALUES (23, '粉晶', 10);
INSERT INTO `type` VALUES (24, '钛晶', 10);
INSERT INTO `type` VALUES (25, '墨晶', 10);
INSERT INTO `type` VALUES (26, '幽灵晶', 10);
INSERT INTO `type` VALUES (27, '茶晶', 10);
INSERT INTO `type` VALUES (28, '寿山石', 9);
INSERT INTO `type` VALUES (29, '蛇纹石', 9);
INSERT INTO `type` VALUES (30, '绿松石', 9);
INSERT INTO `type` VALUES (31, '绿帘石', 9);
INSERT INTO `type` VALUES (32, '青金石', 9);
INSERT INTO `type` VALUES (33, '芙蓉石', 9);
INSERT INTO `type` VALUES (34, '木变石', 9);
INSERT INTO `type` VALUES (35, '孔雀石', 9);
INSERT INTO `type` VALUES (36, '东陵石', 9);
INSERT INTO `type` VALUES (37, '白玛瑙', 8);
INSERT INTO `type` VALUES (38, '灰玛瑙', 8);
INSERT INTO `type` VALUES (39, '红玛瑙', 8);
INSERT INTO `type` VALUES (40, '灯草', 8);
INSERT INTO `type` VALUES (41, '缠丝', 8);
INSERT INTO `type` VALUES (42, '藻草', 8);
INSERT INTO `type` VALUES (43, '白玉', 6);
INSERT INTO `type` VALUES (44, '碧玉', 6);
INSERT INTO `type` VALUES (45, '青玉', 6);
INSERT INTO `type` VALUES (46, '墨玉', 6);
INSERT INTO `type` VALUES (47, '黄玉', 6);
INSERT INTO `type` VALUES (48, '绿玉', 6);
INSERT INTO `type` VALUES (49, '京白玉', 6);
INSERT INTO `type` VALUES (50, '新疆玉', 6);
INSERT INTO `type` VALUES (51, '河南玉', 6);
INSERT INTO `type` VALUES (52, '翡翠', 6);
INSERT INTO `type` VALUES (53, '红宝石', 5);
INSERT INTO `type` VALUES (54, '蓝宝石', 5);
INSERT INTO `type` VALUES (55, '祖母绿', 5);
INSERT INTO `type` VALUES (56, '海蓝宝石', 5);
INSERT INTO `type` VALUES (57, '金绿宝石', 5);
INSERT INTO `type` VALUES (58, '碧玺', 5);
INSERT INTO `type` VALUES (59, '金水菩提', 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `role` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户角色（0管理员，1鉴定人员，2普通用户）',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', 'admin', '0', '15133185689', '男', '2021-04-16 13:43:35', '', '', '2021-02-16 15:28:37');
INSERT INTO `user` VALUES (4, 'user1', 'e10adc3949ba59abbe56e057f20f883e', 'user1', '2', '15100257355', '男', NULL, NULL, 'admin', '2021-04-25 09:24:40');
INSERT INTO `user` VALUES (5, 'auth1', 'e10adc3949ba59abbe56e057f20f883e', 'auh1', '1', '', '男', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, 'user2', '96e79218965eb72c92a549dd5a330112', 'test', '2', '', '男', '2021-04-25 09:20:22', 'admin', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
