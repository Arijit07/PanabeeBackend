package com.niit.PanabeeBackend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.PanabeeBackend.model.Blog;
@Repository	


public interface BlogDao {
	
    public List<Blog> getAllBlogs();
	
	public boolean saveBlog(Blog blog);
	
	public boolean deleteBlog(Blog blog);
	
	public boolean updateBlog(Blog blog);
	
	public Blog getBlogByBlogId(int blogId);
	
	public List<Blog> getAllApproveBlogs();
	
	

}
