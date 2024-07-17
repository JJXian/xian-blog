<p align="center">
        <a href="http://xianblogs.cn">
        <img alt="logo" src="http://47.98.43.176:9000/xian-blog/2024/github/images/logo3.png" style=" width: 120px; height: 120px;"></a>
</p>
<h1 align="center" style="margin: 10px 0 20px; font-weight: bold;">xian-blog v1.0.0</h1>
<h3 align="center">基于SpringBoot开发的前后端分离的博客系统</h3>
<p align="center">
  <a href="https://img.shields.io/badge/MybatisPlus-v3.5.3-blue"><img alt="Static Badge" src="https://img.shields.io/badge/MybatisPlus-v3.5.3-blue"></a>
  <a href="https://img.shields.io/badge/JDK-1.8-green"><img alt="Static Badge" src="https://img.shields.io/badge/JDK-1.8-green"></a>
  <a href="https://img.shields.io/badge/blog-v1.0-brightgreen"><img alt="Static Badge" src="https://img.shields.io/badge/blog-v1.0-brightgreen"></a>
	<a href="https://img.shields.io/badge/SpringBoot-2.5.9-blue"><img alt="Static Badge" src="https://img.shields.io/badge/SpringBoot-2.5.9-blue"></a>
	<a href="https://img.shields.io/badge/ElementUI-v2.15.14-success"><img alt="Static Badge" src="https://img.shields.io/badge/ElementUI-v2.15.14-success"></a>
	<a href="https://img.shields.io/badge/Vue-v2.6.14-orange"><img alt="Static Badge" src="https://img.shields.io/badge/Vue-v2.6.14-orange"></a>
</p>

## 在线体验

- 用户：user/user123
- 管理员： boss/boss123

🔔Tips: 用户也可通过注册账号自行注册～


演示地址：http://xianblogs.cn

## 项目亮点
- 基于 **SpringBoot** 开发，代码结构清晰，扩展性强，可以根据具体业务需求进行功能扩展。
- 采用**前后端分离**架构，实现了前端页面与后端逻辑的高效交互，提高了开发效率和系统的可维护性。
- 使用 **JWT** 令牌进行用户登录身份验证，增强了系统的安全性和用户体验。
- 对用户密码进行**MD5加密**存储，提高了用户数据的安全性。
- 通过 **Redis** 缓存博客查询结果，采用**主动更新策略**保持缓存与数据库一致性，并通过**互斥锁机制**解决缓存击穿问题，提升了系统的性能和稳定性。
- 项目部署在云服务器上，使用 **MinIO** 搭建对象存储服务，并引入CDN进行全站加速，提升了用户访问速度和资源加载效率。
- 前端使用 **Vue** 和 **ElementUI** ，提供了直观友好的用户界面，展示博客、论坛活动和评论等相关信息，提高了用户体验。

## 项目结构
```
├─basic:全局通用功能
│   └─file-starter:文件上传模块 
├─common：全局通用配置  
│   ├─constants：封装通用常量
│   ├─enums：枚举类
│   ├─exception：自定义异常
│   ├─redis：公用Redis工具类
│   ├─regex：通用正则模块
│   ├─result：返回结果封装
│   └─utils：通用工具类
├─model：数据模型模块
├─service：项目业务处理模块
├─files：本地存储对象模块
└─vue：前端ui模块
```