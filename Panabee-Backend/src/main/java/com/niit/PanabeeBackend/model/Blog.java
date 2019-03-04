package com.niit.PanabeeBackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Component
public class Blog extends BaseDomain implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_AUTO_BLOG_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	int blogId;
	
	String blogContent;
	
	String blogTitle;
	
	Date blogDate;
	
	String userId;
	
	String blogStatus;
	
	int blogCountLike;
	
	private String userName;
	
	int blogCommentCount;
	
	private String blogImage;
	
	@Transient
	private MultipartFile blogImageFile;
	
	
	
	
	public String getBlogImage() {
		return blogImage;
	}
	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}
	public MultipartFile getBlogImageFile() {
		return blogImageFile;
	}
	public void setBlogImageFile(MultipartFile blogImageFile) {
		this.blogImageFile = blogImageFile;
	}
	
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public Date getBlogDate() {
		return blogDate;
	}
	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}
	
	public String getBlogStatus() {
		return blogStatus;
	}
	public void setBlogStatus(String blogStatus) {
		this.blogStatus = blogStatus;
	}
	public int getBlogCountLike() {
		return blogCountLike;
	}
	public void setBlogCountLike(int blogCountLike) {
		this.blogCountLike = blogCountLike;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getBlogCommentCount() {
		return blogCommentCount;
	}
	public void setBlogCommentCount(int blogCommentCount) {
		this.blogCommentCount = blogCommentCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}