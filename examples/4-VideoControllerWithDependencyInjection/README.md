## 运行 Video Service 应用程序

运行应用程序：

右键点击 org.magnum.mobilecloud.video 包中的 Application 类，选择 Run As->Java Application。

停止运行应用程序：

打开 Eclipse 的 Debug Perspective（Window->Open Perspective->Debug），在“Debug”视图中（
如果视图没有打开，Windows->Show View->Debug），右键点击运行中的应用程序然后选择 Terminate。

## 如何访问服务

查看已经被添加的视频列表，打开浏览器然后输入下面的地址：

http://localhost:8080/video

添加一个测试数据，运行 VideoSvcClientApiTest，可以通过在 Eclipse 中右键点击选择
Run As->JUnit Test（确保先运行服务器端的应用！）

## 注意事项

在这个版本的 VideoSvc 应用程序中，我们使用了依赖注入：

1. VideoSvc 的 “videos” 成员变量并没有在 VideoSvc 类的代码中显式的实例化，而是将这个成员变量标注
   成了 @Autowired。Spring 框架自动的将这个成员变量的值连接到具体的某个对象上，实际上将连接到我们
   标注了 @Bean 的 Application.videoRepository() 方法返回的，实现了 VideoRepository 接口的，
   任意对象上。只要你在 Application 类中对某个具体接口唯一声明一个标注了 @Bean 的方法来返回该接口
   的对象实例，Spring 就可以自动的找到为该接口的变量标注了 @Autowired 的位置，并将你编写的方法的返
   回值注入到这些位置的对象中。
2. 这个案例更新了 VideoSvcTest 代码，目的是演示伪装对象是如何被注入到 @Autowired 成员变量中的。在
   这个案例里面，我们把一个用于测试的、实现了 VideoRepository 接口的伪装对象（是使用 Mockito 框架
   进行构建的）注入到 VideoSvc 中的 “videos” 成员变量当中。
3. 在测试程序中添加了一个集成测试，通过依赖注入用真实的对象、而不是伪装的对象装配了 Controller，然后
   向它发送伪装的 HTTP 请求。这种集成测试用来帮助确保 Application 可以正确的配置所有的应用程序中的
   @Autowired 值，并且验证它们能装配在一起正确的运行。