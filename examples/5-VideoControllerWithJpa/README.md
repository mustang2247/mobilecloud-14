## 运行 Video Service 应用程序

运行应用程序：

右键点击 org.magnum.mobilecloud.video 程序包，选择 Run As->Java Application。

停止运行应用程序：

打开 Eclipse 的 Debug Perspective（Window->Open Perspective->Debug），在“Debug”视图中（
如果视图没有打开，Windows->Show View->Debug），右键点击运行中的应用程序然后选择 Terminate。

## 如何访问服务

查看已经被添加的视频列表，打开浏览器然后输入下面的地址：

http://localhost:8080/video

添加一个测试数据，运行 VideoSvcClientApiTest，可以通过在 Eclipse 中右键点击选择
Run As->JUnit Test（确保先运行服务器端的应用！）

## 注意事项

在这个版本的 VideoSvc 应用程序中，我们添加了数据的持久化：

1. VideoRepository 接口定义了应用程序与数据库之间的接口。在这个项目中没有包含对 VideoRepository
   接口的实现，当 Spring 框架在发现接口被 @Repository 标注时，将动态的创建接口实现。
2. VideoSvc 的 “videos” 成员变量将被自动装配成 Spring 创建的基于 VideoRepository 接口的实现。
3. VideoRepository 继承了很多方法，例如 save(...)，findAll() 等等，这些方法都在它继承的
   CrudRepository 接口中进行了定义。
4. build.gradle 文件中的 “compile("com.h2database:h2")" 语句行添加了应用程序对 H2 数据库的
   依赖，Spring Boot 会自动的发现这条配置并在应用程序中嵌入一个数据库实例。在默认情况下，数据库是
   配置为仅内存的模式，也就是说在重启应用的时候数据不会持久化。但是，可以很容易的用其他的数据进行替
   换，从而实现数据的长期持久化。
5. 值得注意的是，VideoRepositoryNotice 是被 Spring 自动发现的。