package com.niit.PanabeeBackend.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.PanabeeBackend.dao.BlogCommentDao;
import com.niit.PanabeeBackend.dao.BlogDao;
import com.niit.PanabeeBackend.dao.BlogLikeDao;
import com.niit.PanabeeBackend.dao.NotificationDao;
import com.niit.PanabeeBackend.model.Blog;
import com.niit.PanabeeBackend.model.BlogComment;
import com.niit.PanabeeBackend.model.BlogLike;
import com.niit.PanabeeBackend.model.Notification;
import com.niit.PanabeeBackend.model.Users;





@RestController
public class BlogController {
	@Autowired
	BlogDao blogDao;
	@Autowired
	Blog blog;
	@Autowired
	BlogComment blogComment;
	@Autowired
	BlogCommentDao blogCommentDao;
	@Autowired
	Users user;
	@Autowired
	BlogLikeDao blogLikeDao;
	@Autowired
	BlogLike blogLike;
	@Autowired
	NotificationDao notificationDao;
	
	
	
	

	@RequestMapping(value="/blogs", method=RequestMethod.GET)
	@ResponseBody
	
	public ResponseEntity<List<Blog>> getAllBlogs(){
		List<Blog> blogs=blogDao.getAllBlogs();
		if(blogs.isEmpty()){
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.NO_CONTENT);
		}
		System.out.println(blogs.size());
		System.out.println("kkkkkk");
		return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	//******************************Create Blog********************************************//
	
	@RequestMapping(value="/blog/", method=RequestMethod.POST)
	public ResponseEntity<Blog> creatBlog(@RequestBody Blog blog,HttpSession session){
		System.out.println("Create Blog");
		try
		{
		Users user=(Users) session.getAttribute("loggedInUser");
		
		if(((user==null)||
			(blogDao.getBlogByBlogId(blog.getBlogId())!=null)))
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("BlogComment Not Created");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);	
		}
		else{
		blog.setBlogDate(new Date(System.currentTimeMillis()));
		blog.setBlogStatus("Pending");
		blog.setBlogCountLike(0);
		blog.setBlogCommentCount(0);
		blog.setUserId(user.getUserId());
		blog.setUserName(user.getUserName());
		blogDao.saveBlog(blog);
		System.out.println("blog" + blog.getBlogId());
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		}
		catch(NullPointerException e)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("User Not logged in");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
			
		}
		
		}
		
		
		//**********************************Get By blog ID****************************************//
	
	@RequestMapping(value= "/blog/{id}",method=RequestMethod.GET)
	public ResponseEntity<Blog>getAllBlogsByBlogId(@PathVariable("id")int id){
		Blog blog = blogDao.getBlogByBlogId(id);
		if (blog == null){
			blog = new Blog();
			blog.setErrorMessage("Blog does not exist with id : " + id);
				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	//**********************************Create Blog Like****************************************//
	
	
	@RequestMapping(value="/likeBlog/{blogId}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> likeBlog(@PathVariable("blogId") int blogId,HttpSession session) 
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			System.out.println(user.getUserName());
			Blog blog = blogDao.getBlogByBlogId(blogId);
			if (blog == null)
			{
				blog = new Blog();
				
				blog.setErrorMessage("No blog exist with id : " + blogId);
	
				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		}
		
			else if(blogLikeDao.isExist(blogId, user.getUserId()))
			{
				blog = new Blog();
				
				blog.setErrorMessage("User has already liked the blog: " + blogId);

				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
			}
			
		blog.setBlogCountLike(blog.getBlogCountLike()+1);
		blogDao.updateBlog(blog);
		BlogLike blogLike=new BlogLike();
		blogLike.setBlogId(blogId);blogLike.setUserId(user.getUserId());blogLike.setUserName(user.getUserName());
		blogLike.setBlogLikeDate(new Date(System.currentTimeMillis()));
		blogLikeDao.saveBlogLike(blogLike);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Blog blog=new Blog();
			blog.setErrorMessage("not logged in");
			blog.setErrorCode("404");
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);

		}
		
		
	}
	
	//**********************************Blog Like By Blog ID****************************************//
	
	
	@RequestMapping(value="/likeBlog/{blogId}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<BlogLike>> getBlogLikesByblogId(@PathVariable("blogId") int blogId,HttpSession session) {
		Blog blog = blogDao.getBlogByBlogId(blogId);
		if (blog == null) {
			return new ResponseEntity<List<BlogLike>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogLike>>(blogLikeDao.getBlogLikesByBlogId(blogId), HttpStatus.OK);
		
	}
	
	//**********************************Blog accept ****************************************//
	
	

	@RequestMapping(value="/approveBlog/{blogId}", method=RequestMethod.PUT)
	ResponseEntity<Blog>approveBlog(@PathVariable("blogId") int blogId,HttpSession session)
	{	
		try
		{
			Blog blog=blogDao.getBlogByBlogId(blogId);
			if(((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN")&&
					(blog!=null))
			{
						blog.setBlogStatus("Approved");
						blogDao.updateBlog(blog);
						Notification notification=new Notification();
					    notification.setApprovedOrRejected("Approved");
					    notification.setBlogTitle(blog.getBlogTitle());
				    	notification.setUserId(blog.getUserId());//AUTHOR OF THE BLOGPOST
				    	notification.setViewed("False");
						notificationDao.addNotification(notification);
						return new ResponseEntity<Blog>(blog,HttpStatus.OK);
					}
			else
			{
						blog=new Blog();
						blog.setErrorCode("404");
						blog.setErrorMessage("blog not approved");
						return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
					}
			
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Blog blog=new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("admin not loggedin");
			return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
		}
	}
	
	
	//**********************************Blog rejecet****************************************//
	
	
	@RequestMapping(value="/rejectBlog/{blogId}", method=RequestMethod.PUT)
	ResponseEntity<Blog>rejectBlog(@PathVariable("blogId") int blogId,HttpSession session)
	{	
		try
		{
			Blog blog=blogDao.getBlogByBlogId(blogId);
			if(((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN")&&
					(blog!=null))
			{
						blog.setBlogStatus("Reject");
						blog.setBlogCountLike(blog.getBlogCountLike());
						blog.setBlogCommentCount(blog.getBlogCommentCount());
						blog.setUserId(blog.getUserId());
						blog.setUserName(blog.getUserName());
						blogDao.updateBlog(blog);
						Notification notification=new Notification();
					    notification.setApprovedOrRejected("Rejected");
					    notification.setBlogTitle(blog.getBlogTitle());
				    	notification.setUserId(blog.getUserId());//AUTHOR OF THE BLOGPOST
				    	notification.setRejectionReason( "REJECTED BY ADMIN");
				    	notification.setViewed("False");//ENTERED BY ADMIN
						notificationDao.addNotification(notification);
						return new ResponseEntity<Blog>(blog,HttpStatus.OK);
					}
			else
			{
						blog=new Blog();
						blog.setErrorCode("404");
						blog.setErrorMessage("blog not rejected");
						return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
					}
			
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Blog blog=new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("admin not loggedin");
			return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	//**********************************All Blog Comments****************************************//
	
	@RequestMapping(value="/blogComments", method=RequestMethod.GET)
	@ResponseBody
	
	public ResponseEntity<List<BlogComment>> getAllBlogComments(){
		List<BlogComment> blogs=blogCommentDao.getAllBlogComments();
		if(blogs.isEmpty()){
			blogComment.setErrorMessage("BlogComment does not exist at all" );
			return new ResponseEntity<List<BlogComment>>(blogs,HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogs,HttpStatus.OK);
	}
	
	//**********************************All Blog Comments By Blog Id****************************************//
	
	
	@RequestMapping(value= "/blogCommentByBlogId/{id}", method=RequestMethod.GET)
	
	public ResponseEntity<List<BlogComment>> getAllBlogComments(@PathVariable("id")int id){
		List<BlogComment> blogComments=blogCommentDao.listByBlogId(id);
		if(blogComments.isEmpty()){
			blogComment.setErrorMessage("BlogComment does not exist with id : " +id);
			return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	}
	
	//**********************************Create Blog Comments****************************************//
	
	
		@RequestMapping(value="/blogComment/", method=RequestMethod.POST)
	public ResponseEntity<BlogComment> creatBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		Users user=(Users) session.getAttribute("loggedInUser");
		System.out.println("blog id is "+blogComment.getBlogId());
		//checking if the user doesnt exist or the blog doesnt exist
		Blog blog=blogDao.getBlogByBlogId(blogComment.getBlogId());
		if((user==null)||
			(blog==null)	
				)
		{
			blogComment.setErrorCode("404");
			blogComment.setErrorMessage("BlogComment Not Created");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
		
		Date blogCommentDate=new Date(System.currentTimeMillis());
		
		blogComment.setBlogCommentDate(blogCommentDate);
		blogComment.setUserId(user.getUserId());
		blogComment.setUserName(user.getUserName());
		blogComment.setBlogId(blogComment.getBlogId());
		blogCommentDao.save(blogComment);
		blog.setBlogCommentCount((blog.getBlogCommentCount())+1);
		System.out.println("at blog comment count**");
		blogDao.updateBlog(blog);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}


//**********************************Update Blog Comments****************************************//

@RequestMapping(value="/updateBlogComment/{id}", method=RequestMethod.PUT)
	public ResponseEntity<BlogComment> updateBlogComment(@PathVariable("id") int id, @RequestBody BlogComment blogComment,HttpSession session){
	
		Users user=(Users) session.getAttribute("loggedInUser");
		
		if((user==null)||(blogCommentDao.getByBlogCommentId(blogComment.getBlogCommentId())==null)){
			blogComment =new BlogComment();
			blogComment.setErrorMessage("Blog does not exist with id : " +id);
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NO_CONTENT);
		}
		Date blogCommentDate=new Date(System.currentTimeMillis());
		blogComment.setBlogCommentDate(blogCommentDate);
		blogComment.setUserId(user.getUserId());
		blogComment.setUserName(user.getUserName());
		blogCommentDao.update(blogComment);
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}

//**********************************All Blog Comments By BlogCommentID****************************************//	

@RequestMapping(value= "/blogCommentById/{id}",method=RequestMethod.GET)
	public ResponseEntity<BlogComment>getAllBlogs(@PathVariable("id")int id){
		BlogComment blogComment = blogCommentDao.getByBlogCommentId(id);
		if (blogComment == null){
			blogComment = new BlogComment();
			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
				return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}
	
//**********************************All Blog Comments By UserID****************************************//		
	
@RequestMapping(value= "/blogCommentByUserId/{id}", method=RequestMethod.GET)
	
	public ResponseEntity<List<BlogComment>> getBlogCommentsByUserId(@PathVariable("id")String id){
		List<BlogComment> blogComments=blogCommentDao.listByUserId(id);
		if(blogComments.isEmpty()){
			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
			return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	}
	
//**********************************Delete Blog Comments****************************************//	


	@RequestMapping(value="/deleteBlogComment/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<BlogComment>deleteBlogComment(@PathVariable("id")int id){
		BlogComment blogComment=blogCommentDao.getByBlogCommentId(id);
		if(blogComment== null){
			blogComment = new BlogComment();
			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
			
		}
		blogCommentDao.delete(blogComment);
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}

//**********************************Delete Blog ****************************************//

@RequestMapping(value="/blog/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Blog>deleteBlog(@PathVariable("id")int id){
		Blog blog=blogDao.getBlogByBlogId(id);
		if(blog == null){
			blog = new Blog();
			blog.setErrorMessage("Blog does not exist with id : " + id);
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
			
		}
		blogDao.deleteBlog(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}	
	
//******************************All Approved Blog********************************************//
	
	
	@RequestMapping(value= "/approvedBlog",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>>getAllApprovedBlogs(){
		List<Blog> blogs=blogDao.getAllApproveBlogs();
		if(blogs.isEmpty()){
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.NO_CONTENT);
		}
		System.out.println(blogs.size());
		System.out.println("kkkkkk");
		return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}


//******************************Update Blog********************************************//

	
@RequestMapping(value="/blog/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable("id") int id, @RequestBody Blog blog,HttpSession session){
		if(blogDao.getAllBlogs()==null){
			blog =new Blog();
			blog.setErrorMessage("Blog does not exist with id : " +blog.getBlogId());
			return new ResponseEntity<Blog>(blog, HttpStatus.NO_CONTENT);
		}
		blog.setBlogDate(new Date(System.currentTimeMillis()));
		blog.setBlogStatus("Pending");
		blog.setBlogCountLike(0);
		blog.setBlogCommentCount(0);
		blog.setUserId(user.getUserId());
		blog.setUserName(user.getUserName());
		blogDao.updateBlog(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	
	

}
