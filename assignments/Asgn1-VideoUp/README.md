# 作业 1 （覆盖 1-4 周的内容）

## 如何运行应用程序

__不要使用 “jettyRun” 来启动这个应用程序。请阅读以下说明__

运行应用程序：

右键点击 org.magnum.dataup.package 程序包中的 Application.class，
选择 Run As->Java Application。

停止应用程序：

打开 Eclipse Debug Perspective（Window->Open Perspective->Debug），右键 “Debug” 视图中
的应用程序（如果视图没有打开，Window->Show View->Debug），然后选择 Terminate（终止）。

## 简介

管理移动设备上传的多媒体数据是一个非常典型的云计算服务/应用。这个作业是创建一个非常基本
的云应用程序，用于将视频数据上传到云服务中并管理视频的元数据信息。当你掌握了这个最基本的
云服务开发的基础结构时，你就拥有了构建更复杂云服务的核心知识。


## 说明

首先，克隆这个 Git 代码库，然后导入到 Eclipse 当中，如可配置开发环境可以参见：
[http://learn.kaikeba.com/courses/840/assignments/9220?module_item_id=47607].

这个作业的目的是测试你能够创建一个 Web 应用程序，让客户端可以将视频数据上传到服务器。服务
器可以允许客户端首先上传视频的元数据信息（例如，duration，title，等等），然后再上传该视频
实际的二进制数据。服务器应当支持用 Multipart 请求上传视频的二进制数据。

AutoGradingTest 将用于为你提交的作业打分，该测试程序位于 src/test/java 工程目录的 
org.magnum.dataup 代码包中。**_以 AutoGradingTest 源代码中描述的信息作为作业实现的唯一
标准_。**你提交的应用程序应该通过所有的测试程序，并且没有任何的错误。所有的测试方法用 
@Rubric 进行了标注，说明了每个测试方法的分值、测试目的和相关的学习视频。

你需要实现下面列出的所有 HTTP API，才有可能通过测试：

GET /video
   - 以 JSON 的方式返回已经被添加到服务器端的视频列表。服务器端不需
     要在服务器重新启动时进行视频对象列表的持久化。以 JSON 方式返回
     的 Video 对象列表数据能够被客户端映射为 Collection<Video>。
   - 返回的 content-type 应当是 application/json，它将是 @ResponseBody 
     默认的返回类型。
    
POST /video
   - 视频数据通过 application/json 类型的 Request Body 发送给服
     务器。当 Spring 使用默认的 Jackson 类库对接收到的 JSON 数据
     进行反串列化时，将实例化出一个有效的 Video 类型的对象。
   - **_服务器应当为 Video 对象生成一个唯一的标识 Id，然后通过调用 
     Video 的 setId(...) 方法进行设置。_**
   - 所有的视频 ID 应当 > 0。
   - 返回的 Video JSON 数据中应当包含服务器端生成的标识 Id，这样客
     户端可以在上传二进制的 MPEG 视频内容时进行引用，指明是哪一个 
     Video 对象的视频内容。
   - 服务器还应当为 Video 对象生成一个指向对应的二进制数据文件的 URL
     （例如，http://localhost:8080/video/1/data 是一个有效的视频
     数据的 url）。查看提示的部分了解如何生成这些 URL。
     
POST /video/{id}/data
   - 视频的二进制 MPEG 数据应当以 Multipart 请求的方式并以“data”为
     键值发送到服务器。路径中的 {id} 应当用服务器为 Video 对象生成的
     唯一标识替换。在向服务器 POST /video/{id}/data 请求之前，客户
     端必须首先通过向服务器 POST /video 来_创建_一个 Video 对象，并
     获得新创建的 Video 对象的唯一标识。
   - 如果请求成功应当返回一个 state=VideoState.READY 的 VideoStatus
     对象，否则应当返回适当的 HTTP 错误状态信息。VideoState.PROCESSING 
     不需要在作业中使用，但是先保留在那里。
   - 在这里没有使用 PUT 请求，而是使用了 POST 请求，原因是在默认情况下 
     Spring 不支持通过 PUT 请求发送 Multipart 数据，这是一个在设计 
     Commons File Upload 类库时的决策：https://issues.apache.org/jira/browse/FILEUPLOAD-197。
     
  
GET /video/{id}/data
   - 返回指定 id 的视频所对应的二进制 MPEG 数据（如果存在）。如果指定的视频没有找到，
     或者指定的视频所对应的 MPEG 数据还没有被上传，那么服务器应当返回一个 404 的状态
     编码（Status Code）。
     
 AutoGradingTest 将作为作业实现的最终标准。如果上面的描述当中与 AutoGradingTest 不一致，
 以 AutoGradingTest 作为正确的标准，并且通过导学老师将错误反馈给我们。另外，你需要仔细阅读 
 AutoGradingTest 中的源代码，确保理解所有的作业要求。如果你不能理解 AutoGradingTest 中
 的某些内容，你可以在讨论区中提问，但是不要把你的作业答案发布在讨论区中。
 
 有一个 VideoSevApi 接口定义，它用 Retrofit 的标注类型进行了标注，使得客户端可以与你要创
 建的视频服务进行通讯。你的控制器不要直接以 Java 的方式来实现接口（例如，编写一个类直接实
 现 VideoSvcApi 的所有接口），而需要用 HTTP API 的方式，按照接口定义上方的文本描述和
 AutoGradingTest 中说明的测试标准来进行实现。
 
 重申 -- AutoGradingTest 定义了作业是如何被打分的，你必须实现所有的作业要求才能通过该类中
 定义的所有测试方法。如果某个测试用例在这个 README 文件中没有被提到，你仍然需要通过这些测试
 程序，因为打分只依据你的程序是否能通过测试。__确保详细阅读 AutoGradingTest 的源代码和每一
 个测试方法的描述！__
 
 请不要修改 Video，VideoStatus，VideoSvcApi，AutoGradingTest 中的代码。

## 测试你的编写的代码

在测试你的代码之前，首先按照前面描述的方法运行应用程序。当应用程序启动以后，右键点击 AutoGradingTest， 
然后选择 Run As->JUnit Test 来运行测试程序。Elipse 会输出测试成功或失败的报告，结合 @Rubric 
中定义的每项测试的分数，你可以计算出你的作业成绩。

## 提交你的作业

再提交作业之前，把你的源代码打包成 .zip 文件，然后把打包好的压缩包上传到学习中心，导学老师会对作业进行
最终打分。
 
## 已有的代码

- __org.magnum.dataup.Video__：这个类代表了视频的元数据，该类可以使用以下方法进行实例化：
  
```java
  Video video = Video.create().withContentType("video/mpeg")
			.withDuration(123).withSubject("Mobile Cloud")
			.withTitle("Programming Cloud Services for ...").build();
```
  从 application/json 类型的 Request Body 中接收一个 Video 视频对象，或者返回某个视频对象，例如：
```java
  	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video v){
		// Do something with the Video
		// ...
		return v;
	}
```
- __org.magnum.dataup.VideoFileManager__：你可以用这个类（但不是必须的）将视频二进制数据存储到文
  件系统。默认情况下，它会将所有的视频存储到当前工作路径下的 “videos” 目录。可以参考下面的方式使用这个类：
```java
    // 初始化这个类的变量
    // videoDataMgr = VideoFileManager.get()
    private VideoFileManager videoDataMgr;

    // 然后你可以在 Controller 中调用 videoFileManager 中的方法...
  	public void saveSomeVideo(Video v, MultipartFile videoData) throws IOException {
  	     videoDataMgr.saveVideoData(v, videoData.getInputStream());
  	}
  	
  	public void serveSomeVideo(Video v, HttpServletResponse response) throws IOException {
  	     // 需要给客户端返回视频数据时可以使用
  	     // ...
  	     videoDataMgr.copyVideoData(v, response.getOutputStream());
  	}
```
  
 
## 提示

- 这个 Github 上 examples 中的小例子会对完成本次作业有帮助
- 在项目中至少有一个标注为 @Controller 的类
- 至少需要有 4 个不同的方法标注为 @RequestMapping，才能实现所有的的 HTTP API
- 所有的 Controller 可以接收 HttpServletRequest 和 HttpServletResponse 对象作为参数，以便获得底层
  HTTP 消息的访问/控制。当 Controller 的方法被调用时，Spring 将自动的填入这些参数：
```java
        ...
        @RequestMapping("/some/path/{id}")
        public MyObject doSomething(
                   @PathVariable("id") String id, 
                   @RequestParam("something") String data,
                   HttpServletResponse response) {
         
            // 这样做的必要性在于你可能需要设置 Response 的状态码，或者将二进制
            // 数据写入到从 HttpServletResponse 对象获取的 OutputStream 中。
            ....       
        }
        
```
- ID 必须是 long 类型。测试程序会向服务器发送 long 类型的数据，如果你使用 int 类型的话，会产生一个 400 错误。
- 如果你得到一个 400 错误，说明你没有正确的设置方法应当接受的参数类型，以及这些参数与 HTTP 参数之间的映射关系。
- Controller 中的某个标注了 @RequestMapping 的方法需要接收一个 HttpServletResponse 对象作为参数，并使用这
  个对象将需要发送给客户端的二进制数据写入输出流。
- 应用程序中的大多数功能都有多种实现方法，但只要你的代码能够通过测试程序就可以获得满分。
- 你编写的所有 Controller 和其他的 Class 都不应该 implements VideoSvcApi -- 这个接口仅用于创建 Retrofit
  客户端。你的类不应该看起来像这样： 
```java
        public class SomeClass implements VideoSvcApi // 不要实现这个接口！ 
        {
          ...
        }
```
- 你可以参考下面的方法来查找服务器的地址，并生成视频数据文件的完整 URL：
```java
        private String getDataUrl(long videoId){
            String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
            return url;
        }

     	private String getUrlBaseForLocalServer() {
		   HttpServletRequest request = 
		       ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		   String base = 
		      "http://"+request.getServerName() 
		      + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
		   return base;
		}
```
- 为每个视频生成唯一 ID 的的方法有很多，其中一种方法是使用 AtomicLong，代码类似于：
```java
    private static final AtomicLong currentId = new AtomicLong(0L);
	
	private Map<Long,Video> videos = new HashMap<Long, Video>();

  	public Video save(Video entity) {
		checkAndSetId(entity);
		videos.put(entity.getId(), entity);
		return entity;
	}

	private void checkAndSetId(Video entity) {
		if(entity.getId() == 0){
			entity.setId(currentId.incrementAndGet());
		}
	}
```



