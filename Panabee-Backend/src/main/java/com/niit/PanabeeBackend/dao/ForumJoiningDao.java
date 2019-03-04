package com.niit.PanabeeBackend.dao;

import java.util.List;

import com.niit.PanabeeBackend.model.ForumJoining;

public interface ForumJoiningDao {

	public boolean saveForumJoining(ForumJoining forumJoining);
	public boolean isExist(String userId,int forumId);
	public List<ForumJoining> getForumJoiningsByUserId(String userId);
	public List<ForumJoining> getForumJoiningsByForumId(int forumId);
}
