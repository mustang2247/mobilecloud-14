package org.magnum.mobilecloud.video.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.magnum.mobilecloud.video.controller.Video;

/**
 * An implementation of the VideoRepository that allows duplicate
 * Videos. 
 * VideoRepository 接口的一种实现，允许存储重复的视频对象。
 * 
 * 当然...NoDuplicatesVideoRepository 中有好多重复的代码可以通过重构的方式放到
 * 基类或者 helper 对象当中。但是这个代码示例的目标是说明什么是依赖注入，而且类的
 * 数量越少越好，所以我们就没有进行重构。
 * 
 * @author anonymous
 *
 */
public class AllowsDuplicatesVideoRepository implements VideoRepository {

	// List（列表）类型允许存储相等的对象，也就是 .equals() 返回真的对象。
	//
	// 假设读的次数要远远大雨写的次数。
	private List<Video> videoList = new CopyOnWriteArrayList<Video>();
	
	@Override
	public boolean addVideo(Video v) {
		return videoList.add(v);
	}

	@Override
	public Collection<Video> getVideos() {
		return videoList;
	}

	// 搜索所有与 title 匹配的视频。
	@Override
	public Collection<Video> findByTitle(String title) {
		Set<Video> matches = new HashSet<>();
		for(Video video : videoList){
			if(video.getName().equals(title)){
				matches.add(video);
			}
		}
		return matches;
	}

}
