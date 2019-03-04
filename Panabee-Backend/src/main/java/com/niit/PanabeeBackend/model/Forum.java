package com.niit.PanabeeBackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


import org.springframework.stereotype.Component;

@Entity
@Component
public class Forum extends BaseDomain implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_AUTO_FORUM_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private int forumId;
	
	private String forumName;
	
	private String forumDescription;
	
	private String userId;
	
	private Date forumCreationDate;
	
	private String forumStatus;
	
    private String userName;
	
	private int forumCommentCount;
	
	
	
	/**
	 *  getters/setters for all the fields taken... 
	 */
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public String getForumDescription() {
		return forumDescription;
	}
	public void setForumDescription(String forumDescription) {
		this.forumDescription = forumDescription;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getForumCreationDate() {
		return forumCreationDate;
	}
	public void setForumCreationDate(Date forumCreationDate) {
		this.forumCreationDate = forumCreationDate;
	}
	public String getForumStatus() {
		return forumStatus;
	}
	public void setForumStatus(String forumStatus) {
		this.forumStatus = forumStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getForumCommentCount() {
		return forumCommentCount;
	}
	public void setForumCommentCount(int forumCommentCount) {
		this.forumCommentCount = forumCommentCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
