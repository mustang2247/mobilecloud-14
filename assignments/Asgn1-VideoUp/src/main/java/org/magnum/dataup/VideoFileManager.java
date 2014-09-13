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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.magnum.dataup.model.Video;

/**
 * 这个类用于实现将二进制的视频数据存储在文件系统的“videos”目录当中。这个类
 * 中包含了两个主要的功能，包括存储视频数据和获取视频数据。
 */
public class VideoFileManager {

	/**
	 * 这是一个静态的工厂方法，创建并返回一个类型为 VideoFileManager 的对象。
	 * 你可以根据需要为这个方法添加适当的参数来完成相应的功能。
	 * 
	 * @return
	 * @throws IOException
	 */
	public static VideoFileManager get() throws IOException {
		return new VideoFileManager();
	}
	
	private Path targetDir_ = Paths.get("videos");
	
	// VideoFileManager.get() 方法用于获取一个 VideoFileManager 实例
	private VideoFileManager() throws IOException{
		if(!Files.exists(targetDir_)){
			Files.createDirectories(targetDir_);
		}
	}
	
	// 一个私有的帮助函数，用于解析视频文件的路径。
	private Path getVideoPath(Video v){
		assert(v != null);
		
		return targetDir_.resolve("video"+v.getId()+".mpg");
	}
	
	/**
	 * 这个方法将返回一个布尔值，指明在参数中指定的视频的二进制数据是否已经存在
	 * 于文件系统当中。
	 * 
	 * @param v
	 * @return boolean
	 */
	public boolean hasVideoData(Video v){
		Path source = getVideoPath(v);
		return Files.exists(source);
	}
	
	/**
	 * 这个方法的功能是将视频的二进制数据拷贝（或输出）到在参数中指定的输出流当中。
	 * 方法的调用者需要负责确保指定的 Video 对象包含相关的二进制数据。如果这个方法
	 * 发现没有相关的视频数据，会抛出一个 FileNotFoundException 异常。
	 * 
	 * @param v 
	 * @param out
	 * @throws IOException 
	 */
	public void copyVideoData(Video v, OutputStream out) throws IOException {
		Path source = getVideoPath(v);
		if(!Files.exists(source)){
			throw new FileNotFoundException("无法找到视频文件 videoId:"+v.getId());
		}
		Files.copy(source, out);
	}
	
	/**
	 * 这个方法会读去 InputStream 中包含的所有数据并将数据存储到文件系统当中。方法的调用
	 * 者需要提供 Video 对象所对应的数据内容。
	 * 
	 * @param v
	 * @param videoData
	 * @throws IOException
	 */
	public void saveVideoData(Video v, InputStream videoData) throws IOException{
		assert(videoData != null);
		
		Path target = getVideoPath(v);
		Files.copy(videoData, target, StandardCopyOption.REPLACE_EXISTING);
	}
	
}
