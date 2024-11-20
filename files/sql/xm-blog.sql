/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : xm-blog

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 20/11/2024 16:22:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动名称',
  `descr` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动简介',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '活动内容',
  `start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开始时间',
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结束时间',
  `form` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动形式',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动地址',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主办方',
  `read_count` int DEFAULT '0' COMMENT '浏览量',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='活动';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` (`id`, `name`, `descr`, `content`, `start`, `end`, `form`, `address`, `host`, `read_count`, `cover`) VALUES (3, '没有互动', '福卡进房间', '<p>大叔快放假了 大法<img src=\"http://localhost:9090/files/1714731613889-357ef2053c4b4028aea2fc52eff1da9f.jpeg\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th></th><th></th><th></th><th></th><th></th></tr><tr><td></td><td></td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr><tr><td></td><td>法大赛法；了k<br/>大福卡<br/><br/></td><td></td><td></td><td></td></tr></tbody></table>', '2024-04-25', '2024-05-30', '线上', '中央大街', '中央军委', 34, 'http://localhost:9090/files/1716713089512-d09678beec684ecba7392713d480f13b.png');
INSERT INTO `activity` (`id`, `name`, `descr`, `content`, `start`, `end`, `form`, `address`, `host`, `read_count`, `cover`) VALUES (4, '放大到发疯', '放大法', '<p>放大法立法的啊了</p>', '2024-05-06', '2025-04-16', '线下', '打发打发', '放大法', 42, 'http://localhost:9090/files/1716713082776-06d625f4bac54706b65d066a0491d260.jpeg');
INSERT INTO `activity` (`id`, `name`, `descr`, `content`, `start`, `end`, `form`, `address`, `host`, `read_count`, `cover`) VALUES (5, '抽奖保时捷', '充钱就能获得抽奖机会', '<p>抽奖送车fdfs</p>', '2024-05-31', '2024-11-21', '线上', 'www.baidu.com', '保时捷官方', 72, 'http://localhost:9090/files/1716776583313-cf7dc0a1aa5e4e02af3d0d67e2ffca40.jpeg');
INSERT INTO `activity` (`id`, `name`, `descr`, `content`, `start`, `end`, `form`, `address`, `host`, `read_count`, `cover`) VALUES (6, '端午节活动的发到法法法大方大沙发---长沙站', '端午安康', '<p>打发打发</p>', '2024-05-27', '2024-06-25', '线下', '法法法', '啊啊啊f', 21, 'http://localhost:9090/files/1717493886471-下载.jpeg');
COMMIT;

-- ----------------------------
-- Table structure for activity_sign
-- ----------------------------
DROP TABLE IF EXISTS `activity_sign`;
CREATE TABLE `activity_sign` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `activity_id` int DEFAULT NULL COMMENT '活动ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报名时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2080399362 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='活动报名';

-- ----------------------------
-- Records of activity_sign
-- ----------------------------
BEGIN;
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (-1125408766, 5, 28, '2024-07-14 17:18:41');
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (-240394239, 5, 11, '2024-07-20 22:12:52');
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (-177549311, 4, 11, '2024-07-14 22:33:56');
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (8, 5, 5, '2023-12-12 16:45:05');
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (17, 3, 8, '2024-05-08 16:53:44');
INSERT INTO `activity_sign` (`id`, `activity_id`, `user_id`, `time`) VALUES (671191042, 5, 12, '2024-06-26 21:35:21');
COMMIT;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='管理员';

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` (`id`, `username`, `password`, `name`, `avatar`, `role`, `phone`, `email`) VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'http://localhost:9090/files/1717318352016-06d625f4bac54706b65d066a0491d260.jpeg', 'ADMIN', '13677889922f', 'admin@xm.com');
INSERT INTO `admin` (`id`, `username`, `password`, `name`, `avatar`, `role`, `phone`, `email`) VALUES (2, 'jjxian', 'e10adc3949ba59abbe56e057f20f883e', '大帅', 'http://47.98.43.176:9000/xian-blog/2024/07/08/c9f7f4e5d7f14727b9c92d1dffc09767.jpeg', 'ADMIN', '15998888888', 'fdafdfaffds@163.com');
INSERT INTO `admin` (`id`, `username`, `password`, `name`, `avatar`, `role`, `phone`, `email`) VALUES (16, 'boss', 'e10adc3949ba59abbe56e057f20f883e', '体验账号', 'http://47.98.43.176:9000/xian-blog/2024/07/14/8acef1548b2047b09a80e3f386e10ab2.jpeg', 'ADMIN', '15995746377', 'tiyan@163.com');
COMMIT;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签',
  `user_id` int DEFAULT NULL COMMENT '发布人ID',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布日期',
  `read_count` int DEFAULT '0' COMMENT '浏览量',
  `category_id` int DEFAULT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='博客信息';

-- ----------------------------
-- Records of blog
-- ----------------------------
BEGIN;
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (1, '聊聊我的前端面试心得', '<p>最近的面试中有一个面试官问我按钮级别的权限怎么控制，我说直接v-if啊，他说不够好，我说我们项目中按钮级别的权限控制情况不多，所以v-if就够了，他说不够通用，最后他对我的评价是做过很多东西，但是都不够深入，好吧，那今天我们就来深入深入。</p><p><br/>因为我自己没有相关实践，所以接下来就从这个有16.2k星星的后台管理系统项目Vue vben admin中看看它是如何做的。</p><p><br/></p><p>要做权限控制，肯定需要一个<code>code</code>，无论是权限码还是角色码都可以，一般后端会一次性返回，然后全局存储起来就可以了，<code>Vue vben admin</code>是在登录成功以后获取并保存到全局的<code>store</code>中：</p><pre><code class=\"JavaScript\"><span class=\"hljs-keyword\">import</span> { defineStore } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\'pinia\'</span>;\n<span class=\"hljs-keyword\">export</span> <span class=\"hljs-keyword\">const</span> usePermissionStore = <span class=\"hljs-title function_\">defineStore</span>({\n<span class=\"hljs-attr\">state</span>: () =&amp;gt; ({\n<span class=\"hljs-comment\">// 权限代码列表</span>\n<span class=\"hljs-attr\">permCodeList</span>: [],\n}),\n<span class=\"hljs-attr\">getters</span>: {\n<span class=\"hljs-comment\">// 获取</span>\n<span class=\"hljs-title function_\">getPermCodeList</span>(<span class=\"hljs-params\"></span>){\n<span class=\"hljs-keyword\">return</span> <span class=\"hljs-variable language_\">this</span>.<span class=\"hljs-property\">permCodeList</span>;\n},\n},\n<span class=\"hljs-attr\">actions</span>: {\n<span class=\"hljs-comment\">// 存储</span>\n<span class=\"hljs-title function_\">setPermCodeList</span>(<span class=\"hljs-params\">codeList</span>) {\n<span class=\"hljs-variable language_\">this</span>.<span class=\"hljs-property\">permCodeList</span> = codeList;\n},\n\n<span class=\"hljs-comment\">// 请求权限码</span>\n<span class=\"hljs-keyword\">async</span> <span class=\"hljs-title function_\">changePermissionCode</span>(<span class=\"hljs-params\"></span>) {\n<span class=\"hljs-keyword\">const</span> codeList = <span class=\"hljs-keyword\">await</span> <span class=\"hljs-title function_\">getPermCode</span>();\n<span class=\"hljs-variable language_\">this</span>.<span class=\"hljs-title function_\">setPermCodeList</span>(codeList);\n}\n}\n})</code></pre><p>1111</p>', '最近的面试中有一个面试官问我按钮级别的权限怎么控制，最近的面试中有一个面试官问我按钮级别的权限怎么控制，最近的面试中有一个面试官问我按钮级别的权限怎么控制，', 'http://localhost:9090/files/1716713069540-下载.jpeg', '[\"前端\"]', 4, '2023-11-17', 130, 2);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (4, '程序员更需要钝感力', '<p>讲一个大多数同学都会遇到的事，之前在一家单位随着部门发展，空降了个老大哥（前端领导）。怎么说呢那个时候自己陷入了一个极其痛苦的时期，他呢每天都很闲不干活，活呢都交给我跟另外一个哥们干，而且功劳还是他的。我很不爽，当我跟我对象说这个事的时候，她说谁让人家是领导呢！！现在再总结的时候发现好像都释怀了，那个时候每天晚上失眠睡不着。<br/><br/>那个时候痛苦的点是：<br/><br/>1. 身为领导不作为。<br/>2. 身为领导技术能力不行。<br/>3. 把我的产出都当作自己的汇报给领导。<br/><br/>其实那个时候或多或少会跟他对着干，我对象也跟我聊过这个事说不要跟领导对着干，人家是领导不干活就不干活了。好像确实没落到什么好处，最后还是人家啥活不干，痛苦的还是自己。还是要学会向上管理。 <br/>都是打工的，工期是按照人日排的，就是换个领导，怎么着你都要干活的啊！谁让你不是领导呢。</p><p>&nbsp; <br/>到现在听到有吐槽领导会不干活、技术能力不行、功劳是自己的，锅都是我的一些吐槽。我都会笑笑不说什么，其实反过来想领导的位置不允许犯错，一般当领导的年龄都很大了（可能不是那么容易找到那么符合预期的工作了），假如再犯一些低级的错误，会被领导的领导不信任等。你如果能跟领导相处很好，就是犯错了锅是你的，最后你得绩效还是好的。</p><p><br/>领导积极管理组织分享、一块搞些新奇的东西也好，不作为也好，干好自己的工作积极响应，自己擅长的领域就好好发挥，管他结果是啥呢，大家都聪明人，都看得明白的。有时间了就学点新东西，都是打工的，没必要搞得都心里不舒服，凡事多多人情世故下 哈哈 真不爽了离开就是了。<br/><br/></p>', '程序员每个人都是聪明的，都是反应灵敏的。 一个同事说过这么一句话：\"都当的了程序员，大家都是聪明人，不过有些事不好意思点破罢了，他要是真的太过分，就真的撕破脸！\"。', 'http://localhost:9090/files/1716713063859-06d625f4bac54706b65d066a0491d260.jpeg', '[\"后端\",\"Java\"]', 9, '2023-11-17', 35, 5);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (5, '从零开始写一个Vue3+Element Plus的后台管理系统', '<div id=\"article-root\" itemprop=\"articleBody\" class=\"article-viewer markdown-body cache result\" data-v-536a572c=\"\"><style>.markdown-body{word-break:break-word;line-height:1.75;font-weight:400;font-size:16px;overflow-x:hidden;color:#252933}.markdown-body h1,.markdown-body h2,.markdown-body h3,.markdown-body h4,.markdown-body h5,.markdown-body h6{line-height:1.5;margin-top:35px;margin-bottom:10px;padding-bottom:5px}.markdown-body h1{font-size:24px;line-height:38px;margin-bottom:5px}.markdown-body h2{font-size:22px;line-height:34px;padding-bottom:12px;border-bottom:1px solid #ececec}.markdown-body h3{font-size:20px;line-height:28px}.markdown-body h4{font-size:18px;line-height:26px}.markdown-body h5{font-size:17px;line-height:24px}.markdown-body h6{font-size:16px;line-height:24px}.markdown-body p{line-height:inherit;margin-top:22px;margin-bottom:22px}.markdown-body img{max-width:100%}.markdown-body hr{border:none;border-top:1px solid #ddd;margin-top:32px;margin-bottom:32px}.markdown-body code{word-break:break-word;border-radius:2px;overflow-x:auto;background-color:#fff5f5;color:#ff502c;font-size:.87em;padding:.065em .4em}.markdown-body code,.markdown-body pre{font-family:Menlo,Monaco,Consolas,Courier New,monospace}.markdown-body pre{overflow:auto;position:relative;line-height:1.75}.markdown-body pre>code{font-size:12px;padding:15px 12px;margin:0;word-break:normal;display:block;overflow-x:auto;color:#333;background:#f8f8f8}.markdown-body a{text-decoration:none;color:#0269c8;border-bottom:1px solid #d1e9ff}.markdown-body a:active,.markdown-body a:hover{color:#275b8c}.markdown-body table{display:inline-block!important;font-size:12px;width:auto;max-width:100%;overflow:auto;border:1px solid #f6f6f6}.markdown-body thead{background:#f6f6f6;color:#000;text-align:left}.markdown-body tr:nth-child(2n){background-color:#fcfcfc}.markdown-body td,.markdown-body th{padding:12px 7px;line-height:24px}.markdown-body td{min-width:120px}.markdown-body blockquote{color:#666;padding:1px 23px;margin:22px 0;border-left:4px solid #cbcbcb;background-color:#f8f8f8}.markdown-body blockquote:after{display:block;content:\"\"}.markdown-body blockquote>p{margin:10px 0}.markdown-body ol,.markdown-body ul{padding-left:28px}.markdown-body ol li,.markdown-body ul li{margin-bottom:0;list-style:inherit}.markdown-body ol li .task-list-item,.markdown-body ul li .task-list-item{list-style:none}.markdown-body ol li .task-list-item ol,.markdown-body ol li .task-list-item ul,.markdown-body ul li .task-list-item ol,.markdown-body ul li .task-list-item ul{margin-top:0}.markdown-body ol ol,.markdown-body ol ul,.markdown-body ul ol,.markdown-body ul ul{margin-top:3px}.markdown-body ol li{padding-left:6px}.markdown-body .contains-task-list{padding-left:0}.markdown-body .task-list-item{list-style:none}@media (max-width:720px){.markdown-body h1{font-size:24px}.markdown-body h2{font-size:20px}.markdown-body h3{font-size:18px}}</style><style data-highlight=\"\" data-highlight-key=\"juejin\">.markdown-body pre,.markdown-body pre>code.hljs{color:#333;background:#f8f8f8}.hljs-comment,.hljs-quote{color:#998;font-style:italic}.hljs-keyword,.hljs-selector-tag,.hljs-subst{color:#333;font-weight:700}.hljs-literal,.hljs-number,.hljs-tag .hljs-attr,.hljs-template-variable,.hljs-variable{color:teal}.hljs-doctag,.hljs-string{color:#d14}.hljs-section,.hljs-selector-id,.hljs-title{color:#900;font-weight:700}.hljs-subst{font-weight:400}.hljs-class .hljs-title,.hljs-type{color:#458;font-weight:700}.hljs-attribute,.hljs-name,.hljs-tag{color:navy;font-weight:400}.hljs-link,.hljs-regexp{color:#009926}.hljs-bullet,.hljs-symbol{color:#990073}.hljs-built_in,.hljs-builtin-name{color:#0086b3}.hljs-meta{color:#999;font-weight:700}.hljs-deletion{background:#fdd}.hljs-addition{background:#dfd}.hljs-emphasis{font-style:italic}.hljs-strong{font-weight:700}</style><h2 data-id=\"heading-0\">写在开始之前</h2>\n<p>接触Vue3也有一年的时间了，除了刚开始用Vue3做了一个小小的项目，其后一直没有机会在项目中真正使用Vue3，反而一直维护Vue2的老项目。作为一个有追求（wuliao）的前端，那就自己开一个git仓库练手吧，想试验什么就随便试，既没有deadline的压力，也不用去考虑产品需求，UI还原度。</p>\n<p>萌芽期的git仓库地址 <a href=\"https://link.juejin.cn?target=https%3A%2F%2Fgithub.com%2Flucidity99%2Fmocha-vue3-system\" target=\"_blank\" title=\"https://github.com/lucidity99/mocha-vue3-system\" ref=\"nofollow noopener noreferrer\">github.com/lucidity99/…</a></p>\n<h2 data-id=\"heading-1\">主要使用的工具（排名不分先后）：</h2>\n<ul class=\"contains-task-list\">\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Vue3</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Vite</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> TypeScript</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Element Plus</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Pinia</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> SCSS</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Tailwind CSS</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> VueRouter</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> eCharts</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> Eslint、Prettier</li>\n</ul>\n<p>模拟接口数据本来打算用Mockjs，后来决定使用Apipost（最终改成了apiFox），感觉更贴近实际开发，接口管理也方便。</p>\n<p>个人目前的难点是Typescript，还不能真正的把ts用好，代码动不动就爆红，所以练手的目的之一就是精进ts。</p>\n<p>计划要做的内容比较多，除了基础的系统设置、功能模块，还有一些一直想尝试但是未曾在Vue项目中完整实现的功能。</p>\n<ul class=\"contains-task-list\">\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 切片上传文件</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 复杂的表格设置</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 换肤功能（目前已实现暗黑模式，但是更复杂的换肤还没有开始做）</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" checked=\"\" disabled=\"\"/> 多页签组件 <a href=\"https://juejin.cn/post/7229907502900461625\" target=\"_blank\" title=\"https://juejin.cn/post/7229907502900461625\">juejin.cn/post/722990…</a></li>\n<li class=\"task-list-item\"><input type=\"checkbox\" checked=\"\" disabled=\"\"/> 各种主流富文本插件引入 <a href=\"https://juejin.cn/post/7234418257759879223\" target=\"_blank\" title=\"https://juejin.cn/post/7234418257759879223\">juejin.cn/post/723441…</a></li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 尝试Vue3新加入的功能</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 尝试Vue3 hook，vueuse</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> 路由的配置</li>\n<li class=\"task-list-item\"><input type=\"checkbox\" checked=\"\" disabled=\"\"/> 更多配置的axios拦截器 <a href=\"https://juejin.cn/post/7234078736421240869\" target=\"_blank\" title=\"https://juejin.cn/post/7234078736421240869\">juejin.cn/post/723407…</a></li>\n<li class=\"task-list-item\"><input type=\"checkbox\" disabled=\"\"/> ...</li>\n</ul>\n<p>Tailwind CSS是我很感兴趣的原子类CSS库，在以往的使用中，感觉能够实实在在的提升开发体验，后期还打算尝试现在流行的unocss</p></div>', '最近接到了百度的面试，个人觉得基础知识准备的比较充分，就去网上找了一些百度的面经...', 'http://localhost:9090/files/1716713056460-001724646f1d4a82a1bc985e88a82fb0.jpeg', '[\"后端\",\"Java\",\"面试\"]', 10, '2023-11-17', 30, 1);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (7, '一次令人窒息的百度面试', '<h1>看简历</h1><p>上来首先是万年不变的自我介绍，介绍完之后面试官就开始逐行看我的简历，并针对简历上的项目经历进行询问。询问的十分详细。</p><h1>如何实现新手指引</h1><p>问这个问题的原因是我简历上写到了使用driver.js库实现了新手指引。</p><p><br/></p><p>实现思路是：<br/><br/>点击“开始指引”：找到id为mask的元素，为该元素添加蒙层样式（setMask）<br/>添加提示信息：找到id为tip的元素，将提示信息添加为该元素的子元素(setTip)<br/>高亮当前步骤元素：找到当前目标元素，克隆目标元素，然后将克隆后的目标元素添加为curStepMask的子元素(setTip)<br/>定位tip和curStepMask的元素：curStepMask元素在当前目标元素的正上方，tip元素根据情况而定<br/>每次添加当前提示信息时要移除上一次添加的提示信息和覆盖元素（removeTip，removeStepMask）<br/><br/></p>', '最近接到了百度的面试，个人觉得基础知识准备的比较充分，就去网上找了一些百度的面经', 'http://localhost:9090/files/1716713049675-07e6a2ae2bf048bfab86d887694ca9ad.jpeg', '[\"后端\",\"Java\",\"面试\"]', 4, '2023-11-17', 157, 10);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (8, '2W字全面剖析Mybatis中的9种设计模式', '<div id=\"article-root\" itemprop=\"articleBody\" class=\"article-viewer markdown-body cache result\" data-v-536a572c=\"\"><style>.markdown-body{color:#595959;font-size:15px;font-family:-apple-system,system-ui,BlinkMacSystemFont,Helvetica Neue,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Arial,sans-serif;background-image:linear-gradient(90deg,rgba(60,10,30,.04) 3%,transparent 0),linear-gradient(1turn,rgba(60,10,30,.04) 3%,transparent 0);background-size:20px 20px;background-position:50%}.markdown-body p{color:#595959;font-size:15px;line-height:2;font-weight:400}.markdown-body p+p{margin-top:16px}.markdown-body h1,.markdown-body h2,.markdown-body h3,.markdown-body h4,.markdown-body h5,.markdown-body h6{padding:30px 0;margin:0;color:#135ce0}.markdown-body h1{position:relative;text-align:center;font-size:22px;margin:50px 0}.markdown-body h1:before{position:absolute;content:\"\";top:-10px;left:50%;width:32px;height:32px;transform:translateX(-50%);background-size:100% 100%;opacity:.36;background-repeat:no-repeat;background:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAABfVBMVEX///8Ad/8AgP8AgP8AgP8Aff8AgP8Af/8AgP8AVf8Af/8Af/8AgP8AgP8Af/8Afv8AAP8Afv8Afv8Aef8AgP8AdP8Afv8AgP8AgP8Acf8Ae/8AgP8Af/8AgP8Af/8Af/8AfP8Afv8AgP8Af/8Af/8Afv8Afv8AgP8Afv8AgP8Af/8Af/8AgP8AgP8Afv8AgP8Af/8AgP8AgP8AgP8Ae/8Afv8Af/8AgP8Af/8AgP8Af/8Af/8Aff8Af/8Abf8AgP8Af/8AgP8Af/8Af/8Afv8AgP8AgP8Afv8Afv8AgP8Af/8Aff8AgP8Afv8AgP8Aff8AgP8AfP8AgP8Ae/8AgP8Af/8AgP8AgP8AgP8Afv8AgP8AgP8AgP8Afv8AgP8AgP8AgP8AgP8AgP8Af/8AgP8Af/8Af/8Aev8Af/8AgP8Aff8Afv8AgP8AgP8AgP8Af/8AgP8Af/8Af/8AgP8Afv8AgP8AgP8AgP8AgP8Af/8AeP8Af/8Af/8Af//////rzEHnAAAAfXRSTlMAD7CCAivatxIDx5EMrP19AXdLEwgLR+6iCR/M0yLRzyFF7JupSXn8cw6v60Q0QeqzKtgeG237HMne850/6Qeq7QaZ+WdydHtj+OM3qENCMRYl1B3K2U7wnlWE/mhlirjkODa9FN/BF7/iNV/2kASNZpX1Wlf03C4stRGxgUPclqoAAAABYktHRACIBR1IAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH4gEaBzgZ4yeM3AAAAT9JREFUOMvNUldbwkAQvCAqsSBoABE7asSOBRUVVBQNNuy9996789+9cMFAMHnVebmdm+/bmdtbQv4dOFOW2UjPzgFyLfo6nweKfIMOBYWwFtmMPGz2Yj2pJI0JDq3udJW6VVbmKa9I192VQFV1ktXUAl5NB0cd4KpnORqsEO2ZIRpF9gJfE9Dckqq0KuZt7UAH5+8EPF3spjsRpCeQNO/tA/qDwIDA+OCQbBoKA8NOdjMySgcZGVM6jwcgRuUiSs0nlPFNSrEpJfU0jTLD6llqbvKxei7OzvkFNQohi0vAsj81+MoqsCaoPOQFgus/1LyxichW+hS2JWCHZ7VlF9jb187pIAYcHiViHAMnp5mTjJ8B5xeEXF4B1ze/fTh/C0h398DDI9HB07O8ci+vRBdvdGnfP4gBuM8vw7X/G3wDmFhFZEdxzjMAAAAldEVYdGRhdGU6Y3JlYXRlADIwMTgtMDEtMjZUMDc6NTY6MjUrMDE6MDA67pVWAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDE4LTAxLTI2VDA3OjU2OjI1KzAxOjAwS7Mt6gAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAAWdEVYdFRpdGxlAGp1ZWppbl9sb2dvIGNvcHlxapmKAAAAV3pUWHRSYXcgcHJvZmlsZSB0eXBlIGlwdGMAAHic4/IMCHFWKCjKT8vMSeVSAAMjCy5jCxMjE0uTFAMTIESANMNkAyOzVCDL2NTIxMzEHMQHy4BIoEouAOoXEXTyQjWVAAAAAElFTkSuQmCC)}.markdown-body h2{position:relative;font-size:20px;border-left:4px solid;padding:0 0 0 10px;margin:30px 0}.markdown-body h3{font-size:16px}.markdown-body ul{list-style:disc outside;margin-left:2em;margin-top:1em}.markdown-body li{line-height:2;color:#595959}.markdown-body img.loaded{margin:0 auto;display:block}.markdown-body blockquote{background:#fff9f9;margin:2em 0;padding:2px 20px;border-left:4px solid #b2aec5}.markdown-body blockquote p{color:#666;line-height:2}.markdown-body a{color:#036aca;border-bottom:1px solid rgba(3,106,202,.8);font-weight:400;text-decoration:none}.markdown-body em strong,.markdown-body strong{color:#036aca}.markdown-body hr{border-top:1px solid #135ce0}.markdown-body pre{overflow:auto}.markdown-body code,.markdown-body pre{overflow:auto;position:relative;line-height:1.75;font-family:Menlo,Monaco,Consolas,Courier New,monospace}.markdown-body pre>code{font-size:12px;padding:15px 12px;margin:0;word-break:normal;display:block;overflow-x:auto;color:#333;background:#f8f8f8}.markdown-body code{word-break:break-word;border-radius:2px;overflow-x:auto;background-color:#fff5f5;color:#ff502c;font-size:.87em;padding:.065em .4em}.markdown-body table{border-collapse:collapse;margin:1rem 0;overflow-x:auto}.markdown-body table td,.markdown-body table th{border:1px solid #dfe2e5;padding:.6em 1em}.markdown-body table tr{border-top:1px solid #dfe2e5}.markdown-body table tr:nth-child(2n){background-color:#f6f8fa}</style><style data-highlight=\"\">.markdown-body pre,.markdown-body pre>code.hljs{color:#333;background:#f8f8f8}.hljs-comment,.hljs-quote{color:#998;font-style:italic}.hljs-keyword,.hljs-selector-tag,.hljs-subst{color:#333;font-weight:700}.hljs-literal,.hljs-number,.hljs-tag .hljs-attr,.hljs-template-variable,.hljs-variable{color:teal}.hljs-doctag,.hljs-string{color:#d14}.hljs-section,.hljs-selector-id,.hljs-title{color:#900;font-weight:700}.hljs-subst{font-weight:400}.hljs-class .hljs-title,.hljs-type{color:#458;font-weight:700}.hljs-attribute,.hljs-name,.hljs-tag{color:navy;font-weight:400}.hljs-link,.hljs-regexp{color:#009926}.hljs-bullet,.hljs-symbol{color:#990073}.hljs-built_in,.hljs-builtin-name{color:#0086b3}.hljs-meta{color:#999;font-weight:700}.hljs-deletion{background:#fdd}.hljs-addition{background:#dfd}.hljs-emphasis{font-style:italic}.hljs-strong{font-weight:700}</style><p>在学习设计模式的过程中，我们大多数还是只停留在概念层面，很少有机会能在实际开发中用到，任何一个知识点，如果我们不能达到至少从2个方面去窥探它的话，很难真正去理解它。</p>\n<p>而阅读优秀框架的源码是我们窥探设计模式很好的途径，在Mybatis中用到了大概有9种设计模式：</p>\n<ol>\n<li>Builder模式</li>\n<li>工厂模式</li>\n<li>单例模式</li>\n<li>代理模式</li>\n<li>组合模式</li>\n<li>模板方法模式</li>\n<li>适配器模式</li>\n<li>装饰着模式</li>\n<li>迭代器模式</li></ol>\n<p>对于23种设计模式我们并不需要刻意的记忆，而且很多设计模式的实现原理很像，只不过是名字不一样，并且如果去阅读一些框架的源码的话，可以发现其实框架对很多设计模式的实 现，都并非标准的代码实现，都做了比较多的自我改进。\n实际上，这就是所谓的灵活应用, 只借鉴不照搬, 根据具体问题针对性地去解决。</p>\n<p>软件设计模式犹如剑法一样，当哪天我们真正做到：<strong>手中无剑，心中有剑，人剑合一的境界</strong>，我们才真正理解软件设计。</p></div>', '在学习设计模式的过程中，我们大多数还是只停留在概念层面，很少有机会能在实际开发中用到，任何一个知识点，如果我们不能达到至少从2个方面去窥探它的话，很难真正去理解它。', 'http://localhost:9090/files/1716713043909-cf7dc0a1aa5e4e02af3d0d67e2ffca40.jpeg', '[\"后端\",\"Java\",\"面试\"]', 4, '2023-11-17', 45, 10);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (9, '适合小公司的自动化部署脚本', '<p>背景（偷懒）<br/>在小小的公司里面，挖呀挖呀挖。快挖不动了，一件事重复个5次，还在人肉手工，身体和心理就开始不舒服了，并且违背了个人的座右铭：“偷懒”是人类进步的第一推动力。<br/>每次想要去测试环境验证个新功能，又或者被测试无情的催促着部署新版本后；都需要本地打那个200多M的jar包；以龟速般的每秒几十KB网络，通过ftp上传到服务器；用烂熟透的jps命令查找到进程，kill后，重启服务。<br/>是的，我想偷懒，想从已陷入到手工部署的沼泽地里走出来。如何救赎？<br/>自我救赎之路<br/>我的诉求很简单，想要一款“一键CI/CD的工具”，然后可以继续偷懒。为了省事，我做了以下工作<br/>找了一款停止服务的脚本，并做了小小的优化<br/>首推 陈皮大哥的停服脚本（我在里面加了个sleep 5）；脚本见下文。只需要修改 APP_MAINCLASS的变量“XXX-1.0.0.jar”替换为自己jar的名字即可，其它不用动。<br/>该脚本主要是通过jps + jar的名字获得进程号，进行kill。( 脚本很简单，注释也很详细，就不展开了，感兴趣可以阅读下，不到5分钟，写过代码的你能看懂的)<br/><br/>作者：程序员猪佩琪<br/>链接：<a href=\"https://juejin.cn/post/7257440759569055802\" target=\"_blank\">https://juejin.cn/post/7257440759569055802</a><br/>来源：稀土掘金<br/>著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。<br/></p>', '在小小的公司里面，挖呀挖呀挖。快挖不动了，一件事重复个5次，还在人肉手工，身体和心理就开始不舒服了，并且违背了个人的座右铭：“偷懒”是人类进步的第一推动力。', 'http://localhost:9090/files/1716713037226-下载.jpeg', '[\"后端\"]', 4, '2023-11-22', 271, 8);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (12, 'sfafa', '<p>dfaffgfjhgfdsdfghjhgfdsdfghjhgfdeedfghjjhgfds</p>', 'dfadfaf', 'http://localhost:9090/files/1716713030326-3a9e00ef08124bf5b412778f4abee003.png', '[\"Java\",\"面试\",\"Vue\"]', 12, '2024-05-13', 27, 1);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (13, 'zhognwen', '<ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>dfafaf</li></ul><ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>fdafnljk&nbsp;</li></ul><ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>f</li></ul><ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>baoch&nbsp;</li></ul><ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>cald&nbsp;</li></ul><ul class=\"w-e-todo\"><li><span contenteditable=\"false\"><input type=\"checkbox\"/></span>dfas</li></ul>', 'jkdflal', 'http://localhost:9090/files/1716713022130-d09678beec684ecba7392713d480f13b.png', '[\"算法\",\"Java\",\"前端\",\"大数据\"]', 12, '2024-05-13', 44, 8);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (14, 'wode ', '<p>dfsfasfafa</p>', 'jkfdlajf ', 'http://localhost:9090/files/1716712858514-3a9e00ef08124bf5b412778f4abee003.png', '[\"算法\",\"Java\"]', 8, '2024-05-13', 68, 5);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (31, '你好啊', '<p>ni好啊。的飞机啊接口 法多少啊</p>', '接电话算法哦家', 'http://47.98.43.176:9000/xian-blog/2024/07/14/120bc1dd42c84cd390afc1e6e15a40a5.png', '[\"Java\",\"Vue\",\"程序员\"]', 11, '2024-06-28', 119, 4);
INSERT INTO `blog` (`id`, `title`, `content`, `descr`, `cover`, `tags`, `user_id`, `date`, `read_count`, `category_id`) VALUES (47, 'LeetCode.T208实现Trie前缀树', '<pre><code class=\"Java\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">class</span> <span class=\"hljs-title class_\">Trie</span>{\n    <span class=\"hljs-keyword\">private</span> TrieNode root ;\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-title\">Trie</span><span class=\"hljs-params\">()</span> </span>{\n        root = <span class=\"hljs-keyword\">new</span> <span class=\"hljs-built_in\">TrieNode</span>();\n    }\n\n<span class=\"hljs-comment\">//    向前缀树中加入字典</span>\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">void</span> <span class=\"hljs-title\">insert</span><span class=\"hljs-params\">(<span class=\"hljs-type\">String</span> <span class=\"hljs-type\">word</span>)</span> </span>{\n        TrieNode node = root;\n        <span class=\"hljs-keyword\">for</span>(<span class=\"hljs-type\">char</span> c : <span class=\"hljs-type\">word</span>.<span class=\"hljs-built_in\">toCharArray</span>()){\n            <span class=\"hljs-keyword\">if</span>(!node.<span class=\"hljs-built_in\">containsKey</span>(c)){\n                node.<span class=\"hljs-built_in\">put</span>(c,<span class=\"hljs-keyword\">new</span> <span class=\"hljs-built_in\">TrieNode</span>());\n            }\n            node = node.<span class=\"hljs-built_in\">get</span>(c);\n        }\n        node.<span class=\"hljs-built_in\">setEnd</span>();\n    }\n\n<span class=\"hljs-comment\">//    如果在前缀树中 就返回true 否则返回false</span>\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">boolean</span> <span class=\"hljs-title\">search</span><span class=\"hljs-params\">(<span class=\"hljs-type\">String</span> <span class=\"hljs-type\">word</span>)</span> </span>{\n        TrieNode node =  <span class=\"hljs-built_in\">searchPrefix</span>(<span class=\"hljs-type\">word</span>);\n        <span class=\"hljs-keyword\">return</span> node != null &amp;&amp; node.<span class=\"hljs-built_in\">isEnd</span>();\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">boolean</span> <span class=\"hljs-title\">startsWith</span><span class=\"hljs-params\">(<span class=\"hljs-type\">String</span> prefix)</span> </span>{\n        TrieNode node = <span class=\"hljs-built_in\">searchPrefix</span>(prefix);\n        <span class=\"hljs-keyword\">return</span> node != null;\n    }\n\n\n<span class=\"hljs-comment\">//    辅助方法 查找前缀对应的节点</span>\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> TrieNode <span class=\"hljs-title\">searchPrefix</span><span class=\"hljs-params\">(<span class=\"hljs-type\">String</span> prefix)</span></span>{\n        TrieNode node = root;\n        <span class=\"hljs-keyword\">for</span>(<span class=\"hljs-type\">char</span> c : prefix.<span class=\"hljs-built_in\">toCharArray</span>()){\n            <span class=\"hljs-keyword\">if</span>(node.<span class=\"hljs-built_in\">containsKey</span>(c)){\n                node = node.<span class=\"hljs-built_in\">get</span>(c);\n            }<span class=\"hljs-keyword\">else</span>{\n                <span class=\"hljs-keyword\">return</span> null;\n            }\n        }\n        <span class=\"hljs-keyword\">return</span> node;\n    }\n\n}\n<span class=\"hljs-keyword\">class</span> <span class=\"hljs-title class_\">TrieNode</span>{\n    <span class=\"hljs-keyword\">private</span> TrieNode[] links;\n    <span class=\"hljs-keyword\">private</span> <span class=\"hljs-keyword\">final</span> <span class=\"hljs-type\">int</span> R = <span class=\"hljs-number\">26</span>;\n    <span class=\"hljs-keyword\">private</span> <span class=\"hljs-type\">boolean</span> isEnd;\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-title\">TrieNode</span><span class=\"hljs-params\">()</span></span>{\n        links = <span class=\"hljs-keyword\">new</span> TrieNode[R];\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">boolean</span> <span class=\"hljs-title\">containsKey</span><span class=\"hljs-params\">(<span class=\"hljs-type\">char</span> c)</span></span>{\n        <span class=\"hljs-keyword\">return</span> links[c - <span class=\"hljs-string\">\'a\'</span>] != null;\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> TrieNode <span class=\"hljs-title\">get</span><span class=\"hljs-params\">(<span class=\"hljs-type\">char</span> ch)</span></span>{\n        <span class=\"hljs-keyword\">return</span> links[ch-<span class=\"hljs-string\">\'a\'</span>];\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">void</span> <span class=\"hljs-title\">put</span><span class=\"hljs-params\">(<span class=\"hljs-type\">char</span> ch, TrieNode node)</span></span>{\n        links[ch-<span class=\"hljs-string\">\'a\'</span>] = node;\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">void</span> <span class=\"hljs-title\">setEnd</span><span class=\"hljs-params\">()</span></span>{\n        isEnd = <span class=\"hljs-literal\">true</span>;\n    }\n\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-type\">boolean</span> <span class=\"hljs-title\">isEnd</span><span class=\"hljs-params\">()</span></span>{\n        <span class=\"hljs-keyword\">return</span> isEnd;\n    }\n}</code></pre>', '字典树', 'http://47.98.43.176:9000/xian-blog/2024/07/15/c420f1a0617c4244a4263b7d28ce47df.png', '[\"后端\",\"算法\"]', 11, '2024-07-15', 32, 6);
COMMIT;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='博客分类';

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` (`id`, `name`) VALUES (1, '后端');
INSERT INTO `category` (`id`, `name`) VALUES (2, '前端');
INSERT INTO `category` (`id`, `name`) VALUES (4, '大数据');
INSERT INTO `category` (`id`, `name`) VALUES (5, '代码人生');
INSERT INTO `category` (`id`, `name`) VALUES (6, '算法');
INSERT INTO `category` (`id`, `name`) VALUES (7, '人工智能');
INSERT INTO `category` (`id`, `name`) VALUES (8, '开发工具');
INSERT INTO `category` (`id`, `name`) VALUES (9, '职场经验');
INSERT INTO `category` (`id`, `name`) VALUES (10, '面试');
INSERT INTO `category` (`id`, `name`) VALUES (11, '央视新闻');
INSERT INTO `category` (`id`, `name`) VALUES (12, '湖南卫视');
COMMIT;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int DEFAULT NULL COMMENT '关联ID',
  `user_id` int DEFAULT NULL COMMENT '点赞人ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏';

-- ----------------------------
-- Records of collect
-- ----------------------------
BEGIN;
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (61, 5, 8, '博客');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (67, 30, 12, '博客');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (68, 1, 12, '博客');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (73, 5, 12, '活动');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (79, 12, 11, '博客');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (89, 5, 11, '活动');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (110, 31, 28, '博客');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (111, 3, 28, '活动');
INSERT INTO `collect` (`id`, `fid`, `user_id`, `module`) VALUES (116, 31, 11, '博客');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `user_id` int DEFAULT NULL COMMENT '评论人',
  `pid` int DEFAULT NULL COMMENT '父级ID',
  `root_id` int DEFAULT NULL COMMENT '根节点ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论时间',
  `fid` int DEFAULT NULL COMMENT '关联ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='评论表';

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (95, '不错 很有帮助 希望继续写高质量文章', 12, NULL, 95, '2024-05-08 09:04:06', 5, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (109, '看见好看就好看', 12, NULL, 109, '2024-05-08 15:00:47', 4, '活动');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (110, '鲁昆吉里', 12, 109, 109, '2024-05-08 15:00:53', 4, '活动');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (111, '是的', 8, 110, 109, '2024-05-08 20:16:20', 4, '活动');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (114, 'jhkjjhk', 8, NULL, 114, '2024-05-13 11:59:49', 3, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (115, 'fdfads\n', 8, NULL, 115, '2024-05-13 13:53:00', 12, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (116, 'pinglunwne \n', 8, NULL, 116, '2024-05-13 13:53:14', 12, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (120, '好好好', 12, NULL, 120, '2024-06-27 10:21:32', 1, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (121, '是的', 12, 120, 120, '2024-06-27 10:21:39', 1, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (122, 'meiwenti', 12, NULL, 122, '2024-06-27 10:47:33', 30, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (123, 'fdfasdfa', 12, NULL, 123, '2024-06-28 11:16:56', 12, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (124, 'fdsfaf\n', 12, NULL, 124, '2024-06-28 12:06:09', 5, '活动');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (126, 'nishowode weisi \n', 11, NULL, 126, '2024-06-29 14:08:27', 13, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (127, 'haoya ', 11, 126, 126, '2024-06-29 14:08:33', 13, '博客');
INSERT INTO `comment` (`id`, `content`, `user_id`, `pid`, `root_id`, `time`, `fid`, `module`) VALUES (135, 'fdfdsa ', 11, NULL, 135, '2024-07-14 15:21:26', 5, '活动');
COMMIT;

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int DEFAULT NULL COMMENT '关联ID',
  `user_id` int DEFAULT NULL COMMENT '点赞人ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞';

-- ----------------------------
-- Records of likes
-- ----------------------------
BEGIN;
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (37, 13, 8, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (40, 12, 8, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (56, 30, 8, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (69, 4, 12, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (72, 6, 12, '活动');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (78, 5, 12, '活动');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (82, 13, 12, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (98, 13, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (99, 12, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (103, 5, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (105, 5, 11, '活动');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (113, 7, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (123, 31, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (136, 1, 11, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (143, 31, 28, '博客');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (144, 3, 28, '活动');
INSERT INTO `likes` (`id`, `fid`, `user_id`, `module`) VALUES (150, 14, 11, '博客');
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公告信息表';

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
INSERT INTO `notice` (`id`, `title`, `content`, `time`, `user`) VALUES (1, '喜报', '项目基本功能完成，再进一步进行优化！！', '2023-09-05', 'admin');
INSERT INTO `notice` (`id`, `title`, `content`, `time`, `user`) VALUES (2, '橘子洲景区闭园', '自今日起，橘子洲景区因强降雨关闭，为保障游客安全，采取闭园措施。', '2023-09-05', 'admin');
INSERT INTO `notice` (`id`, `title`, `content`, `time`, `user`) VALUES (3, '强降雨黄色预警', '今晚即将迎来本次第二轮强降雨，注意防洪！', '2023-09-05', 'admin');
INSERT INTO `notice` (`id`, `title`, `content`, `time`, `user`) VALUES (4, '庆祝建党成立103周年！', '践行初心，担当使命！', '2024-04-28', 'admin');
INSERT INTO `notice` (`id`, `title`, `content`, `time`, `user`) VALUES (6, 'fsfa', 'fdafa', '2024-07-14', 'booss');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (4, 'wangsulong', 'f9c8c0b3ee7d5779e47f3632de35a39d', '汪苏泷', 'http://localhost:9090/files/1716713117958-3a9e00ef08124bf5b412778f4abee003.png', NULL, NULL, '15887667677', NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (6, 'zhouxun', 'f9c8c0b3ee7d5779e47f3632de35a39d', '周迅', 'http://47.98.43.176:9000/xian-blog/2024/common/defaultAvatar/momo.jpeg', NULL, '女', NULL, NULL, NULL, '2024-04-04');
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (7, 'huanglei', 'f9c8c0b3ee7d5779e47f3632de35a39d', '黄磊', 'http://47.98.43.176:9000/xian-blog/2024/common/defaultAvatar/momo.jpeg', NULL, '男', NULL, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (8, 'xiaowang', 'f9c8c0b3ee7d5779e47f3632de35a39d', '小王', 'http://localhost:9090/files/1716713117958-3a9e00ef08124bf5b412778f4abee003.png', 'USER', '男', '18299889833', 'fdakdlf@163.com', NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (9, 'wangyuan', 'e10adc3949ba59abbe56e057f20f883e', '王源', 'http://47.98.43.176:9000/xian-blog/2024/common/defaultAvatar/momo.jpeg', 'USER', '女', '', NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (10, '万顷', 'f9c8c0b3ee7d5779e47f3632de35a39d', '万顷', 'http://47.98.43.176:9000/xian-blog/2024/common/defaultAvatar/momo.jpeg', 'USER', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (11, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'http://47.98.43.176:9000/xian-blog/2024/07/11/7dcdf981289e461f82c441648476ac90.png', 'USER', NULL, '15887888988', 'jjxian@163.com', '本硕985大厂程序员', NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (12, 'jjxian', 'e10adc3949ba59abbe56e057f20f883e', 'jjxian', 'http://localhost:9090/files/1716712795036-357ef2053c4b4028aea2fc52eff1da9f.jpeg', 'USER', '男', '17667667333', '2837777487@qq.com', 'fdalkf', NULL);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `avatar`, `role`, `sex`, `phone`, `email`, `info`, `birth`) VALUES (13, 'wuji', '123456', '无极', 'http://47.98.43.176:9000/xian-blog/2024/07/05/f52156d838064e3988b98cdca9914ece.jpeg', 'USER', NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
