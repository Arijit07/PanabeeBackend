package com.niit.PanabeeBackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;
@Entity
@Component
public class ForumJoining extends BaseDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_AUTO_FORUMJOINING_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private int forumJoiningId;
	private String userId;
	private int forumId;
	private String userName;
	private String forumJoiningStatus;
	
	public int getForumJoiningId() {
		return forumJoiningId;
	}
	public void setForumJoiningId(int forumJoiningId) {
		this.forumJoiningId = forumJoiningId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getForumJoiningStatus() {
		return forumJoiningStatus;
	}
	public void setForumJoiningStatus(String forumJoiningStatus) {
		this.forumJoiningStatus = forumJoiningStatus;
	}
}
