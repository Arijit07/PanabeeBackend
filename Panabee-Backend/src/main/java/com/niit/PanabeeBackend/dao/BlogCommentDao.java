package com.niit.PanabeeBackend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.PanabeeBackend.model.BlogComment;

@Repository
public interface BlogCommentDao {
	public List<BlogComment> getAllBlogComments();
	public boolean save(BlogComment blogComment);
	public boolean update(BlogComment blogComment);
	public boolean saveOrUpdate(BlogComment blogComment);
	public boolean delete(BlogComment blogComment);
	public BlogComment getByBlogCommentId(int id);
	public List<BlogComment> listByBlogId(int id);
	public List<BlogComment> listByUserId(String id);

}
