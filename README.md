# J2EE Homework

> 软院 大三 161250042 胡本霖

### 迭代

- 第7次作业：Hibernate

### 体系结构

- （手写的）MVC模式
- 未使用JSP，前后端分离
  - 后端：Tomcat + Java Servlet + Hibernate(MySql)
  - 前端：Vue-cli
    - 后端项目结构：![后端](img/后端.png)

### 部署

1. 未打包；保留.idea文件夹（或许可以）方便地直接在IDEA上启动项目。
2. 使用Maven导入项目。
3. 在Hibernate官网下载`hibernate-release-5.4.0.Final.zip`，将其中`/lib/required`/和`/lib/optional/c3p0/`下的jar包解压到本项目的`/web/WEB-INF/lib/`下。
4. 将项目根目录下的`j2eehomework.sql`文件导入名为"j2eehomework"的MySQL空数据库。
5. 用IDEA导入的Tomcat配置启动，或手动将项目部署到Tomcat上，再访问`http://localhost:8080/home`。