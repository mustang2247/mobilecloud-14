package org.magnum.mobilecloud.video.controller;

import com.google.common.base.Objects;

/**
 * 一个简单的对象，用来描述视频的名称、URL、视频时长。
 */
public class Video {

	private String name;
	private String url;
	private long duration;

	public Video(){}
	
	public Video(String name, String url, long duration) {
		super();
		this.name = name;
		this.url = url;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * 如果两个视频具有完全相同的 name，url 和 duration，那么这个方法会生成
	 * 相同的 hashcode。
	 */
	@Override
	public int hashCode() {
		// Google Guava 提供了强大的哈希工具。
		return Objects.hashCode(name,url,duration);
	}

	/**
	 * 如果两个视频具备完全相同的 name，url 和 duration 值，
	 * 那么他们被认为是相等的。
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Video){
			Video other = (Video)obj;
			// Google Guava 同样提供了强大的判断相等的工具！
			return Objects.equal(name, other.name) 
					&& Objects.equal(url, other.url)
					&& duration == other.duration;
		}
		else {
			return false;
		}
	}

}
