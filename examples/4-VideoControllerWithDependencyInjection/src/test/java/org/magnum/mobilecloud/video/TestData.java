package org.magnum.mobilecloud.video;

import java.util.UUID;

import org.magnum.mobilecloud.video.controller.Video;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 这是一个工具类，帮助创建具有随机 name，url 和 duration 的
 * Video 对象。这个类还提供了一个数据转换的方法，通过 Jackson
 * 将对象转化为 JSON 数据，作为集成测试过程中 VideoSvc Controller
 * 的输入数据。
 * 
 * @author anonymous
 *
 */
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 构建并返回一个 Video 对象，使用随机的 name，url 和 duration。
	 * 
	 * @return Video
	 */
	public static Video randomVideo() {
		// Video 对象的各种信息
		// 勇 Java 的 UUID 类创建一个随机的标识 ID
		String id = UUID.randomUUID().toString();
		String title = "Video-"+id;
		String url = "http://www.kaikeba.com/some/video-"+id;
		long duration = 60 * (int)Math.rint(Math.random() * 60) * 1000; // 1小时以内的随机数
		return new Video(title, url, duration);
	}
	
	/**
	 * 使用 Jackson 的 ObjectMapper 将对象转换成 JSON
	 *  
	 * @param o
	 * @return String
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}
}
