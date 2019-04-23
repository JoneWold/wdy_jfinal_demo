/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : wdy

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 23/04/2019 14:21:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 'JFinal Demo Title here', 'JFinal Demo Content here');
INSERT INTO `blog` VALUES (2, 'test 1', 'test 1');
INSERT INTO `blog` VALUES (3, 'test 2', 'test 2');
INSERT INTO `blog` VALUES (4, 'test 3', 'test 3');
INSERT INTO `blog` VALUES (5, 'test 4', 'test 4');
INSERT INTO `blog` VALUES (6, '2019年3月5日17:19:26', '2019年3月5日17:19:30');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码(MD5加密)',
  `salt2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `picture_obj` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '照片对象',
  `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `union_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(4) NULL DEFAULT 1,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件地址',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `status` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态 {1:正常,2:锁定,3:禁用}',
  `online_status` int(11) NULL DEFAULT NULL,
  `login_fail_times` int(11) NULL DEFAULT NULL COMMENT '登录失败次数',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '解锁时间(密码错误五次锁定账户24小时)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `manager_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager_level_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'bc8c37b9-2e64-461d-850b-2546232b21b11', '管理员', 'admin', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, '{\"bucket\":\"ck.default\",\"uid\":\"rc-upload-1542983208538-9\",\"size\":2990,\"name\":\"user_default_head.jpg\",\"lastModified\":1542983312215,\"type\":\"image/jpeg\",\"percent\":0,\"url\":\"/api/v1/file/get?object=e13cf82b-61e7-4d65-bc87-5d68bbd6d48a.jpg&bucket=ck.default\",\"status\":\"done\",\"object\":\"e13cf82b-61e7-4d65-bc87-5d68bbd6d48a.jpg\"}', '1133', '', 1, '222', '1822304****', '1', NULL, NULL, '2019-02-22 14:54:41', NULL, NULL, NULL, NULL, NULL, '2019-02-22 14:56:59', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 'bc8c37b9-2e64-461d-850b-2546232b21b2', '管理员1', 'tcsq', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'jd2', '', 1, '123456', '1822304****', '1', NULL, NULL, '2018-11-07 19:23:50', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (3, 'bc8c37b9-2e64-461d-850b-2546232b21b3', '管理员2', 'ghjsq', '730005236eef42ec47390d21153de572', 'a231d71136a44da0e4b2584d9f55f390', '001001001', '葛城街道', NULL, NULL, 'oqcM46CU02WnoaQteMWoFegJkE_4', '', 1, '', '1822304****', '1', NULL, NULL, '2018-12-19 11:27:26', NULL, NULL, NULL, NULL, NULL, '2018-12-19 11:27:13', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (4, 'bc8c37b9-2e64-461d-850b-2546232b21b41', '管理员3', 'lysq', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'sq2', '', 1, '', '1822304****', '1', NULL, NULL, '2018-11-04 21:27:39', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (5, 'bc8c37b9-2e64-461d-850b-2546232b21b5', '管理员4', 'wtsq', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'sq1', '', 1, '', '1822304****', '1', NULL, NULL, '2018-12-07 10:14:14', NULL, NULL, NULL, NULL, NULL, '2018-12-07 10:14:45', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (6, 'bc8c37b9-2e64-461d-850b-2546232b21b6', '管理员5', 'dfhsq', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be9892', '001001001', '葛城街道', NULL, NULL, 'wgdw1', '', 1, '111', '1822304****', '1', NULL, NULL, '2018-11-04 21:27:45', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (7, 'bc8c37b9-2e64-461d-850b-2546232b21b7', '管理员6', 'fhsq', '202cb962ac59075b964b07152d234b70', '6d83f74020a5f0a27522a380be149080', '001001001', '葛城街道', NULL, NULL, 'wgdw2', NULL, 1, '123', '1822304****', '1', NULL, NULL, '2018-11-04 21:27:50', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (8, 'bc8c37b9-2e64-461d-850b-2546232b21b8', '管理员7', 'bhsq', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'wg1', NULL, 1, '123', '1822304****', '1', NULL, NULL, '2018-11-23 14:13:18', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (9, 'bc8c37b9-2e64-461d-850b-2546232b21b81', '管理员8', 'bhsq2', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'wg2', NULL, 1, '123', '1822304****', '1', NULL, NULL, '2018-11-04 21:27:55', NULL, NULL, NULL, NULL, NULL, '2018-12-10 15:05:43', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (50, 'bc8c37b9-2e64-461d-850b-2546232b21b88', '管理员9', 'dy', 'f19830d9047f7989b6bd279e12af084d', '3be6fd92248e5731609b41224eefd7b7', '001001001', '葛城街道', '/api/v1/file/get?object=b9cc7186-222c-4f49-b498-edfe2c048a8f.jpg&bucket=ck.default', '{\"bucket\":\"ck.default\",\"uid\":\"rc-upload-1543475038975-3\",\"size\":9759,\"name\":\"11111.jpg\",\"lastModified\":1541408373784,\"type\":\"image/jpeg\",\"percent\":0,\"url\":\"/api/v1/file/get?object=b9cc7186-222c-4f49-b498-edfe2c048a8f.jpg&bucket=ck.default\",\"status\":\"done\",\"object\":\"b9cc7186-222c-4f49-b498-edfe2c048a8f.jpg\"}', 'oqcM46Adwk4mgvLQ-dCk4-SE3fzs', NULL, 1, '123@qq.com', '13200452123', '1', NULL, NULL, '2018-12-19 11:23:59', NULL, NULL, NULL, NULL, NULL, '2018-12-19 11:23:47', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (51, 'f59e60ec-0926-4313-9034-d00198f0f62b', '管理员10', 'wy', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, 'oqcM46GSm5zoVEkb5OQW803s9Y6I', NULL, 1, '1234124325@qq.com', '15546010533', '1', NULL, NULL, '2018-11-04 21:27:59', NULL, NULL, NULL, NULL, NULL, '2018-12-03 20:08:36', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (52, '705682d0-c6db-4c2f-9bc7-fc65b183a7e5', 'jfinal_1', 'gly11', 'b5a3b04acba5195dacf767056bd168e2', 'd09ac8de1532f17c17dc6aa467a29a4c', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '7373', '14345335', '1', NULL, NULL, '2018-11-04 21:28:01', NULL, NULL, NULL, NULL, NULL, '2019-03-07 10:23:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (53, 'f4159041-2ba2-4dea-bfa6-6673fd9a4d88', 'jfinal_2', 'gly12', 'e72e98fb2842236a8392a6c184936a8d', '5a1606581f02be279fe36d1b44d52fd5', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '24523', '1234545', '1', NULL, NULL, '2018-11-04 21:28:03', NULL, NULL, NULL, NULL, NULL, '2019-03-07 10:23:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (54, 'aa3b23f9-d4c7-43de-baa3-e63292aa62de', '管理员13', 'gly13', 'da923d2acd254abc7e564e25fafc1118', '0f807a115d2f77527190eaa85d1a6071', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '12412', '124151', '1', NULL, NULL, '2018-11-04 21:28:09', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (55, '383e7f84-f70c-4079-b5df-420a74c996f0', '管理员14', 'gly14', '4de7e3c4a14f8e308092f831e38bd459', '6206b84639307734152855357be20aac', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '234567', '1534576798', '1', NULL, NULL, '2018-11-04 21:28:06', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (56, 'bc8c37b9-2e64-461d-850b-2546232b31b2', '管理员1', 'tmw', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', '/api/v1/file/get?object=4bdead4e-b4c5-4d9e-b9eb-79ca5e81cd5a.png&bucket=ck.default', NULL, 'jd2', '', 1, '123456', '1822304****', '1', NULL, NULL, '2018-11-07 19:23:50', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (57, 'c54dbba5-21b7-4efb-b8c3-50814938309d', 'dyqz', 'dyqz', '7e745b444bd5bc82d8acfb111f193336', '281e51a4a74246b7a71994e90c37681b', '001001001', '葛城街道', NULL, NULL, 'dyqz', NULL, 1, NULL, '12345678910', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-06 09:43:58', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (58, 'aa5e847f-22ff-4d8a-b0b9-8f319474eb82', 'name', 'abc', 'c97058d3d0742958c5fd37cf85ed8a31', '6507ee4f93624e72a1a56c0fd69f1fab', '001001001', '葛城街道', NULL, NULL, 'qwer123456', NULL, 1, NULL, '12345678910', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (59, 'edb9615e-8b8a-4aca-97d0-2a5d8c80b10c', 'name', 'abc', '43350ed7b831f219428e548bd1dc51f6', '62438285da034adf8eaaa807e4bab02d', '001001001', '葛城街道', NULL, NULL, 'qwer123456', NULL, 1, NULL, '12345678910', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (60, '93e43df3-0123-4acf-8de0-3fbce2ecb5e1', 'dyqz1', 'dyqz1', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, 'dyqz1', NULL, 1, 'ggg', 'gg', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-06 14:49:12', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (61, '27f50797-c61d-4bf4-9d09-13c2442c235a', 'pp', 'pp', 'b555706f496ca92b76c7f8ef4cf02763', '728b9f03097e5a3ceb1e69100e828e56', '001001001', '葛城街道', NULL, NULL, 'oqcM46ErYTYZ0SR_d_f9nMN0fKZY', NULL, 1, 'pp', 'pp', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-09 23:38:51', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (62, 'dd16a2c5-ccd8-4a0f-889b-2327c4c2284c', '测试用户', 'wy', '2fd0bf40bb1ee1d6f83b9a71741caeca', 'ff38dc761ab211ec12e2b4186cc9a741', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123', '123', '1', NULL, NULL, '2018-11-27 22:36:11', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (63, 'fda3d49b-f10b-4136-88b4-9e71ea9b1567', '方法', '发发发', '7b7537252744f69f975127f00bc824e5', 'f9334ff0cbf0df50d9faf18d6402ec0c', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, 'gggg', 'asd', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (64, '7822a22d-087f-4101-9035-47c44daaef18', 'wy', '测试用户', 'c49b9f890b6a6f96dfcf3544776d2cca', 'fdd1bf3b0d406658c09004751ba417a7', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123', '123', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (65, '9b051af8-7e6e-428d-9828-6ea77a12b67e', 'admin', 'wy', 'cda7ababc8cb4f94b836e539450f2902', '58001403c12b3782462b05ca9e4b078b', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123456', '123456', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (66, '02cab51f-9ff3-4e01-b2ed-33d83e065d5b', 'admin', 'wy', '6479b5ac12155e9b1382751b081e4bca', 'a7431007810774dc08cbac319b31d014', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123456', '123456', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (67, '8761097c-0305-4409-ba0a-e7d3b9144864', '这是用户姓名', '这是登录名', '26dd8d64a29f05ada182e64ba2e01b4a', 'e2a04faa792f37794a1463321c4ae74c', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '这是邮箱', '这是电话', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (68, '8004e068-527b-41fb-baa7-d4463d413948', '123', '123', 'adf66cefae78c5876cd65d83bc310230', '8876bab900aec66eb703e85a53d27315', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123', '123', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (69, '0551c350-f96e-4e7e-8c16-8ad0e3db203b', '汪杨测试账号', 'wangyang', '88ff301919e6c83f33414a535c472887', '75076d5c02fbd287dfa81189180933ca', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '123456897@qq.com', '18580631889', '1', NULL, NULL, '2018-11-16 16:41:13', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (70, '29ae22b5-263e-435c-8977-45bf0672b714', 't1', 't', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, '4@qq.com', '13811111111', '1', NULL, NULL, '2018-11-17 15:05:19', NULL, NULL, NULL, NULL, NULL, '2018-12-09 23:12:04', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (71, '4c4d963c-2888-4d4e-8ceb-2aaf210a3b1d', 'wxj', 'wxj', '5d71746bd8e8eb1ef2210b581b5aff07', '50f20af1b7602b667648e357214be989', '001001001', '葛城街道', NULL, NULL, 'oqcM46LbqVHXrtjwJQ8HWn94_IPc', NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (72, 'cfa0512a-d575-4907-8e43-f6f518770601', '测试1', '测试网格员', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, 'oqcM46JhQCQVtRcdXfLEjKVDjpso', NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-14 17:35:09', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (73, '89f21235-4b84-40f5-a85c-a8f78405ccb1', '测试1', 'wy3', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-03 19:35:24', NULL, b'1', NULL, NULL);
INSERT INTO `sys_user` VALUES (74, '91fdb1d0-6e24-404b-886d-173ebc33d48b', '测试人员1', '测试人员1', 'd495292168b5308bf7e0526ea7827d97', 'd8cd68c322a881a0b80fc69a8c5b37fd', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (75, '3ebfd11a-20f2-40f4-bcac-1de82e1b3613', '街道1', 'jd1', '08598e0ef22e472c64b33e49dee15b7f', 'eaef236246d83f6ec4f0f4891372792a', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, '2018-11-28 16:18:23', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:05', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (76, '7db5a9ac-a9b0-49ff-93c2-1edc5209ff1b', '测试', 'cs', '343716175ea5562be54f2551e8461239', '13bf67f652f34c50a80dad6b40f247ff', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (77, '968383d3-d4bc-466b-ac0d-2c29539c9b93', '网格员1', 'cswg', '4425600e58dfcbf8e2168c1bf22081a4', '59bd1fab3bf9825c6378be1087e40f46', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (78, 'd8ee9379-529f-4af9-bd02-e4d9db0420ba', '社区1', 'wy5', '6838d5047cb4ba10e87894eae3a57923', 'a423f46c2afa7118196cedc3ee262b00', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, '2018-11-23 22:32:00', NULL, NULL, NULL, NULL, NULL, '2018-12-03 19:38:32', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (79, '0c0a9bb4-c75b-4b15-ac74-f1b87eb11216', '123', '1', 'd12636ddf45854b65bde708775e5b759', '7df15b73192b4ca1ade54854b6997e10', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '18223049511', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (80, 'e46b9fc7-2d00-4abc-9725-87f79aa1ff1c', 'sq1', 'sq1', '9e1bc8c07c2b05a17a79a2887baad21a', '6ac102fc8579b49ddb26ab0b209635a7', '001001001', '葛城街道', NULL, NULL, 'oqcM46LGEAMXPvpw8i2YyeR39Sxg', NULL, 1, NULL, '15202345614', '1', NULL, NULL, '2018-11-28 16:17:56', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (81, 'c4186f04-9b89-4716-8855-cc6c2a51947b', 'wg1', 'wg1', '62d7ee5fcbae2bbad3b0abe9792de9ab', 'd045f5a0a2a0cba26f7c1059f4bad515', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345614', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (82, 'd074e1f8-14d3-4b12-9b9e-b8c62c70158e', '123', '111', '89bc243d635fb582a1e933f158599877', '9af3b540cfb143ec9beb3c4ecb9a510c', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '13822222222', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (83, '13f90b96-012a-4632-ba45-2008d8adc047', '刘天顺', 'ltsjd', 'f2536e2bd31671be741ba69fba797c5e', 'ff7047da1cb0ae5668685f9bf9749a22', '001001001', '葛城街道', '/api/v1/file/get?object=35bd6a0e-d821-4b64-b3a0-5c6a3d2aac42.jpg&bucket=ck.default', '{\"bucket\":\"ck.default\",\"uid\":\"rc-upload-1543128729128-2\",\"size\":360891,\"name\":\"017c5d554909920000019ae9d202fe.jpg@1280w_1l_2o_100sh.jpg\",\"lastModified\":1543124046624,\"type\":\"image/jpeg\",\"percent\":0,\"url\":\"/api/v1/file/get?object=35bd6a0e-d821-4b64-b3a0-5c6a3d2aac42.jpg&bucket=ck.default\",\"status\":\"done\",\"object\":\"35bd6a0e-d821-4b64-b3a0-5c6a3d2aac42.jpg\"}', NULL, NULL, 1, NULL, '13883120073', '1', NULL, NULL, '2018-11-25 16:39:20', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (84, '5a3907bd-b665-4a08-b7ad-80608669ff0c', '刘天顺', 'ltssq', '62765f4c39e7f2594ad406b497e049e5', '75bdad1a34b237bae53279e88a2a317b', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '13883120073', '1', NULL, NULL, '2018-11-25 15:36:50', NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (85, '9984f0ef-7554-4d8b-93e7-53ec1e9cdc4f', '刘天顺', 'ltswgzr', 'bbba5afe51bd37eb7672f2bc96236570', 'b10b4d887d180d3cefc5e186f88cb25d', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '13883120073', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (86, '36c23d4e-b43b-437e-8e3a-60a3e31346d0', '刘天顺', 'ltswgy', 'a35ad4849c37beebc038e0afd799eb9c', 'd21db90443970f0ed14c43d560b3d9d7', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '13883120073', '1', NULL, NULL, '2018-11-27 18:12:24', NULL, NULL, NULL, NULL, NULL, '2018-12-09 23:38:40', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (87, 'c2731047-8fe8-4350-a22f-de3f6dbb1da2', 'demoooo', 'admin1', 'b31fa65b8765d2c18b5b1514d7877a19', '368a0147bcbcf2ce03954137f302cbf4', '001001001', '葛城街道', NULL, NULL, 'oqcM46CU02WnoaQteMWoFegJkE_4', NULL, 1, '132@qq.com', '15575010755', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (88, 'fba486ef-c4a4-48e8-8156-3b8e6cc997de', 'wg2', 'wg2', '151854384686ace5ce1206897b3a4fcd', 'f0e9357d6ab2f9ee380b9ae3d6ab789f', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345611', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (89, 'b080f3a1-c48c-4b5b-b498-78594dc390c1', '123', '123', '3ae1d8193557c465f371a2045f029ea6', 'f7295d2dc0cb4c6dac432ba499348e96', NULL, NULL, NULL, NULL, 'jd1', NULL, 1, NULL, '18223407969', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (90, '9c1430db-0af9-434d-86c8-26b670910756', 'jd2', 'jd2', 'c0faf521321d9002022ba2d01ca9f9cd', '41ff96bde69148d508d11c41cedeae89', '001001001', '葛城街道', NULL, NULL, NULL, NULL, 1, NULL, '15202345616', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);
INSERT INTO `sys_user` VALUES (91, 'b928db60-6f8f-40bd-a73c-469663f68010', '1sq', '1sq', 'b0bfce8c468f95d30ecad36aab7b4c10', '514db14185ac9b93714ef66d5103a893', '001001001', '葛城街道', NULL, NULL, 'oqcM46Dp_TUnwH7xY_Q7VRMtVQN4', NULL, 1, '11@11.com', '15203254514', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-30 22:03:16', NULL, b'0', NULL, NULL);

-- ----------------------------
-- Table structure for verify_rule
-- ----------------------------
DROP TABLE IF EXISTS `verify_rule`;
CREATE TABLE `verify_rule`  (
  `VSC001` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU001` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU002` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU003` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU004` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU005` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU006` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU007` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU008` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VRU009` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
