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

注意观察之前的 VideoServlet 与目前的云服务版本有什么变化：

1. web.xml 文件被删除了，现在只剩下了一个 Application.java。
2. VideoSvc 相比之前的 VideoServlet 有了极大的简化，这个版本的程序需要依赖于 Spring 框架来进行
   自动将数据编组（Marshalling）后发送到客户端，或将从客户端接收到的数据反编组（Unmarshalling）。
3. 通过类型安全（type-safe）的接口进行 VideoSvc 与客户端之间的交互。当这个接口与 Retrofit 联合
   起来使用的时候，极大的简化了客户端/服务器的交互。
4. 查看 src/test/java 中的两个测试程序，测试代码比之前的 VideoServlet 版本的测试代码简单了许多。
   实际上，Retrofit 版本的测试代码（VideoSvcClientApiTest）与只测试 VideoSvc 对象的测试代码
   （VideoSvcTest）本质上是一样的，都是在调用服务器上的服务。使用 Retrofit 的好处在于，它看上去
   就像是在调用本地的对象————但实际上，当你调用该对象的方法时，它会发送 HTTP 请求给服务器。 
