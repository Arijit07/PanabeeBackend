package com.niit.PanabeeBackend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.PanabeeBackend.model.ForumComment;
@Repository	


public interface ForumCommentDao {
	
	public boolean save(ForumComment forumComment);
	public boolean update(ForumComment forumComment);
	public boolean saveOrUpdate(ForumComment forumComment);
	public boolean delete(ForumComment forumComment);
	public ForumComment getByForumCommentId(int id);
	public List<ForumComment> listByUserId(String id);
	public List<ForumComment> getAllForumComments();
	public List<ForumComment> getForumCommentsByForumId(int forumId);

}
