/*
 * 
 * 版权所有 2014 []
 * 
 * 根据 2.0 版本Apache许可证（"许可证"）授权；根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 *   
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * 除非因适用法律需要或书面同意，根据许可证分发的软件是基于"按原样"基础提供，无任何
 * 明示的或暗示的保证或条件。详见根据许可证许可下，特定语言的管辖权限和限制。
 */

package org.magnum.dataup;

/**
 *                       请不要修改这个接口定义
                    ___                    ___           ___                            
     _____         /\  \                  /\  \         /\  \                           
    /::\  \       /::\  \                 \:\  \       /::\  \         ___              
   /:/\:\  \     /:/\:\  \                 \:\  \     /:/\:\  \       /\__\             
  /:/  \:\__\   /:/  \:\  \            _____\:\  \   /:/  \:\  \     /:/  /             
 /:/__/ \:|__| /:/__/ \:\__\          /::::::::\__\ /:/__/ \:\__\   /:/__/              
 \:\  \ /:/  / \:\  \ /:/  /          \:\~~\~~\/__/ \:\  \ /:/  /  /::\  \              
  \:\  /:/  /   \:\  /:/  /            \:\  \        \:\  /:/  /  /:/\:\  \             
   \:\/:/  /     \:\/:/  /              \:\  \        \:\/:/  /   \/__\:\  \            
    \::/  /       \::/  /                \:\__\        \::/  /         \:\__\           
     \/__/         \/__/                  \/__/         \/__/           \/__/           
      ___           ___                                     ___                         
     /\  \         /\  \         _____                     /\__\                        
    |::\  \       /::\  \       /::\  \       ___         /:/ _/_         ___           
    |:|:\  \     /:/\:\  \     /:/\:\  \     /\__\       /:/ /\__\       /|  |          
  __|:|\:\  \   /:/  \:\  \   /:/  \:\__\   /:/__/      /:/ /:/  /      |:|  |          
 /::::|_\:\__\ /:/__/ \:\__\ /:/__/ \:|__| /::\  \     /:/_/:/  /       |:|  |          
 \:\~~\  \/__/ \:\  \ /:/  / \:\  \ /:/  / \/\:\  \__  \:\/:/  /      __|:|__|          
  \:\  \        \:\  /:/  /   \:\  /:/  /   ~~\:\/\__\  \::/__/      /::::\  \          
   \:\  \        \:\/:/  /     \:\/:/  /       \::/  /   \:\  \      ~~~~\:\  \         
    \:\__\        \::/  /       \::/  /        /:/  /     \:\__\          \:\__\        
     \/__/         \/__/         \/__/         \/__/       \/__/           \/__/        
 */
import java.util.Collection;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Streaming;
import retrofit.mime.TypedFile;

/**
 * 这个接口定义了 VideoSvc 的 API，是用于定义 client/server 之间
 * 交互的协议标准。这个接口定义中还使用了 Retrofit 的标注类型进行标注，
 * 使得客户端可以自动的将接口转化为可以发送相应 HTTP 请求的客户端对象。
 * 
 * 在后端的服务中必须实现以下 HTTP API，才能保证这个接口种定义的方法
 * 可以正确的工作：
 * 
 * GET /video
 *   - 以 JSON 的方式返回已经被添加到服务器端的视频列表。服务器端不需
 *     要在服务器重新启动时进行视频对象列表的持久化。以 JSON 方式返回
 *     的 Video 对象列表数据能够被客户端映射为 Collection<Video>。
 *     
 * POST /video
 *   - 视频数据通过 application/json 类型的 Request Body 发送给服
 *     务器。当 Spring 使用默认的 Jackson 类库对接收到的 JSON 数据
 *     进行反串列化时，将实例化出一个有效的 Video 类型的对象。
 *   - 将存储或更新的 Video 对象以 JSON 的方式返回给客户端。
 *       -- 服务器应当为 Video 对象生成一个唯一的标识 Id，然后通过调用 Video
 *          的 setId(...) 方法进行设置。返回的 Video JSON 数据中应当包含
 *          服务器端生成的标识 Id，这样客户端可以在上传二进制的 MPEG 视频内容
 *          时进行引用，指明是哪一个 Video 对象的视频内容。
 *       -- 服务器应当为 Video 对象生成一个指向对应的二进制数据文件的 URL（
 *          例如，.mp4 的 MPEG 文件）。URL 应当是*完整*的 URL 而不仅仅是路径。
 *          你可以使用类似下面的方法来获得服务器的名字：
 *     
 *     	    private String getUrlBaseForLocalServer() {
 *		       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
 *		       String base = "http://"+request.getServerName()+((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
 *		       return base;
 *		    }
 *     
 * POST /video/{id}/data
 *   - 视频的二进制 MPEG 数据应当以 Multipart 请求的方式并以“data”为键值发送到服务器。
 *     路径中的 {id} 应当用服务器为 Video 对象生成的唯一标识替换。在向服务器 POST 
 *     /video/{id}/data 请求之前，客户端必须首先通过向服务器 POST /video 来*创建*
 *     一个 Video 对象，并获得新创建的 Video 对象的唯一标识。
 *     
 * GET /video/{id}/data
 *   - 返回指定 id 的视频所对应的二进制 MPEG 数据（如果存在）。如果指定的视频没有找到，
 *     或者指定的视频所对应的 MPEG 数据还没有被上传，那么服务器应当返回一个 404 的状态
 *     编码（Status Code）。
 *       
 *     
 * 下面描述的 VideoSvcApi 接口定义明确了本次作业要求实现哪些代码功能。如果上面的代码注
 * 释中的对接口的详细说明与下面的 VideoSvcApi 接口定义有任何的冲突，请以下面的接口定义
 * 为准，并且通过道学老师反应其中的错误。
 *  
 * 关于本次作业最终的判分标准，请查看 src/test/jafa 中的 AutoGradingTest，我们将运行
 * 这个测试程序来对作业进行打分。
 * 
 */
public interface VideoSvcApi {

	public static final String DATA_PARAMETER = "data";

	public static final String ID_PARAMETER = "id";

	public static final String VIDEO_SVC_PATH = "/video";
	
	public static final String VIDEO_DATA_PATH = VIDEO_SVC_PATH + "/{id}/data";

	/**
	 * 这个 API 将返回一个已经添加到服务器的视频列表。Video 对象应当以 JSON 的
	 * 格式返回。
	 * 
	 * 如果手动测试这个 API，运行你的服务器程序然后打开浏览器并访问以下的 URL：
	 * http://localhost:8080/video
	 * 
	 * @return Collection<Video>
	 */
	@GET(VIDEO_SVC_PATH)
	public Collection<Video> getVideoList();
	
	/**
	 * 这个 API 可以让客户端通过向服务器发送 POST 请求来添加 Video 对象，Video 对象
	 * 的数据信息通过 application/json 类型的 Body 来发送。 
	 * 
	 * @return Video
	 */
	@POST(VIDEO_SVC_PATH)
	public Video addVideo(@Body Video v);
	
	/**
	 * 这个 API 可以让客户端为前面添加的 Video 对象上传 MPEG 视频数据，视频数据通过
	 * POST 请求发送到服务器。发送的 POST 请求的 URL 中需要包含 Video 的 ID，指定发送的
	 * 视频数据与哪个 Video 对象关联（例如，将 /video/{id}/data 中的 {id} 替换成为一个
	 * 有效的视频 ID，如 /video/1/data -- 假设 “1” 是一个有效的视频 ID）。   
	 * 
	 * @return VideoStatus
	 */
	@Multipart
	@POST(VIDEO_DATA_PATH)
	public VideoStatus setVideoData(@Path(ID_PARAMETER) long id, @Part(DATA_PARAMETER) TypedFile videoData);
	
	/**
	 * 这个 API 应当返回与某个 Video 对象相关联的视频数据，如果视频数据还没有被上传，则返回
	 * 404 错误。URL 的结构与上一个方法相同，并且客户端需要提供获取的视频数据所对应的 Video 
	 * 对象的 ID。
	 * 
	 * 这个方法使用了 Retrofit 中的 @Stream 标注，表示这个方法将获取大量的流数据（例如，服
	 * 务器上的视频数据）。客户端可以通过从 Response 对象中获取一个 InputStream 来访问返回
	 * 的流数据，代码实例如下：
	 * 
	 * VideoSvcApi client = ... // 使用 Retrofit 创建一个客户端
	 * Response response = client.getData(someVideoId);
	 * InputStream videoDataStream = response.getBody().in();
	 * 
	 * @param id
	 * @return Response
	 */
	@Streaming
    @GET(VIDEO_DATA_PATH)
    public Response getData(@Path(ID_PARAMETER) long id);
	
}
