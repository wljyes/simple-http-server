# simple-http-server
## 用Java实现的简单HTTP服务器   
---
### 要实现的功能  
使用原生socket api  
拦截请求->封装request，response对象，通过读取映射文件利用反射将请求转发到对应的handler，最后将response返回。有点类似Tomacat  
---
### 实现
大体上实现了类似tomcat的路由部分  
* 静态资源访问（只实现了html文件的访问）   
* xml配置uri->Servlet映射关系（自己仿制的Servlet接口）
* request和response的Java模型封装  
* 将请求转发给对应servlet，根据请求方法调用对应的doGet或doPost，返回resopnse
---
大冬天敲代码太难受了。。
