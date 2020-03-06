项目运行注意事项：

1.在web/WEB-INF下手动新建 classes和lib目录

2.File  -->  Project Structure -->  Modules  -->  Paths  
  -->  Use module compile output path(Output path和Test output path均选择新建的classes目录)
  -->  Dependencies  -->  点击“+”,选择1 JARs or directories  -->  选择手动新建的lib目录  -->  选中jar Directory
  
3.File  -->  Project Structure -->  Modules  -->  Dependencies -->  +  -->  library  -->  添加tomcat的jar包依赖

4.配置tomcat时，要在deployment中将项目部署到tomcat

5.配置Artifacts: BookShop:war exploded, 部署到tomcat中的项目也用该方式