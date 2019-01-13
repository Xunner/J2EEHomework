# J2EE Homework

> 软院 大三 161250042 胡本霖

### 迭代

- 第4次作业
- 最新迭代次数：3

### 体系结构

- 后端项目结构：![后端](img/后端.png)
- 未使用JSP，前后端分离
  - 后端：Tomcat + Java Servlet + MySQL
  - 前端：Vue-cli
- （手写的）MVC模式

### 部署

1. 未打包；保留.idea文件夹（或许可以）方便地直接在IDEA上启动项目
2. 使用Maven导入项目
3. 将项目根目录下的`j2eehomework.sql`文件导入名为"j2eehomework"的MySQL空数据库
4. 用IDEA导入的Tomcat配置启动，或手动将项目部署到Tomcat上，再访问`http://localhost:8080/home`