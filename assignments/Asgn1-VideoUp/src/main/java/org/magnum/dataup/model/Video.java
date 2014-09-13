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

package org.magnum.dataup.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fluentinterface.ReflectionBuilder;
import com.fluentinterface.builder.Builder;

/**
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

public class Video {

	public static VideoBuilder create() {
		return ReflectionBuilder.implementationFor(VideoBuilder.class).create();
	}

	public interface VideoBuilder extends Builder<Video> {
		public VideoBuilder withTitle(String title);
		public VideoBuilder withDuration(long duration);
		public VideoBuilder withSubject(String subject);
		public VideoBuilder withContentType(String contentType);
	}

	private long id;
	private String title;
	private long duration;
	private String location;
	private String subject;
	private String contentType;

	@JsonIgnore
	private String dataUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty
	public String getDataUrl() {
		return dataUrl;
	}

	@JsonIgnore
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getDuration());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Video)
				&& Objects.equals(getTitle(), ((Video) obj).getTitle())
				&& getDuration() == ((Video) obj).getDuration();
	}

}
