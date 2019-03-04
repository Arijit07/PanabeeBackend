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

import com.niit.PanabeeBackend.dao.ForumCommentDao;
import com.niit.PanabeeBackend.dao.ForumDao;
import com.niit.PanabeeBackend.model.Forum;
import com.niit.PanabeeBackend.model.ForumComment;
import com.niit.PanabeeBackend.model.Users;




@RestController
public class ForumController {
	
	@Autowired
	ForumDao forumDao;
	@Autowired
	Forum forum;
	@Autowired
	ForumCommentDao forumCommentDao;
	@Autowired
	ForumComment forumComment;
	@Autowired
	Users user;
	
	
	
	

	//***********************************All Forum**********************************************//

	@RequestMapping(value="/forums", method=RequestMethod.GET)
		@ResponseBody
		
		public ResponseEntity<List<Forum>> getAllForums(){
			List<Forum> forums=forumDao.getAllForums();
			if(forums.isEmpty()){
				return new ResponseEntity<List<Forum>>(forums,HttpStatus.NO_CONTENT);
			}
			System.out.println(forums.size());
			System.out.println("kkkkkk");
			return new ResponseEntity<List<Forum>>(forums,HttpStatus.OK);
		}

	//*****************************Create Forum*********************************************//
		
		@RequestMapping(value="/forum/", method=RequestMethod.POST)
		public ResponseEntity<Forum> createForum(@RequestBody Forum forum,HttpSession session){
			try
			{
			Users user=(Users) session.getAttribute("loggedInUser");
			
			if(((user==null)||
				(forumDao.getForumByForumId(forum.getForumId())!=null)))
			{
				
				forum.setErrorCode("404");
				forum.setErrorMessage("Forum Not Created");
				return new ResponseEntity<Forum>(forum,HttpStatus.OK);
			}
			forum.setForumCreationDate(new Date(System.currentTimeMillis()));
			forum.setForumStatus("Pending");
			forum.setForumCommentCount(0);
			forum.setUserId(user.getUserId());
			forum.setUserName(user.getUserName());
			forumDao.saveForum(forum);
			System.out.println(forum.getForumId());
			return new ResponseEntity<Forum>(forum,HttpStatus.OK);
			}
			catch(NullPointerException e)
			{
				forum.setErrorCode("404");
				forum.setErrorMessage("User Not logged in");
				return new ResponseEntity<Forum>(forum,HttpStatus.OK);
				
			}
			}

	//**************************************approve forum**********************************//
		
		@RequestMapping(value="/approveForum/{forumId}", method=RequestMethod.PUT)
		ResponseEntity<Forum> approveForum(@PathVariable("forumId") int forumId,HttpSession session)
		{	
			try
			{
				Forum forum=forumDao.getForumByForumId(forumId);
				if(((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN")&&
						(forum!=null))
				{
					forum.setForumStatus("Approved");
					forumDao.updateForum(forum);
							return new ResponseEntity<Forum>(forum,HttpStatus.OK);
						}
				else
				{
							forum=new Forum();
							forum.setErrorCode("404");
							forum.setErrorMessage("forum not approved");
							return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
						}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				Forum forum=new Forum();
				forum.setErrorCode("404");
				forum.setErrorMessage("admin not logged in");
				return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
			}
			
			
		}
		
	     //**************************************reject forum**********************************//
		
		@RequestMapping(value="/rejectForum/{forumId}", method=RequestMethod.PUT)
		ResponseEntity<Forum> rejecteForum(@PathVariable("forumId") int forumId,HttpSession session)
		{	
			try
			{
				Forum forum=forumDao.getForumByForumId(forumId);
				if(((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN")&&
						(forum!=null)){
					forum.setForumStatus("Reject");
					forumDao.updateForum(forum);
							return new ResponseEntity<Forum>(forum,HttpStatus.OK);
						}
				else
				{
							forum=new Forum();
							forum.setErrorCode("404");
							forum.setErrorMessage("forum not rejected");
							return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
						}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				Forum forum=new Forum();
				forum.setErrorCode("404");
				forum.setErrorMessage("admin not logged in");
				return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
			}
		}		

		
		//*********************************List by Forum ID*****************************************//
		
		@RequestMapping(value= "/forum/{id}",method=RequestMethod.GET)
		public ResponseEntity<Forum>getAllForumsByForumId(@PathVariable("id")int id){
			Forum forum = forumDao.getForumByForumId(id);
			if (forum == null){
				forum = new Forum();
				forum.setErrorMessage("Forum does not exist with id : " + id);
					return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		
		//*********************************List forum comment by Forum ID*****************************************//
		
		
		@RequestMapping(value= "/forumComments/{forumId}", method=RequestMethod.GET)
			public ResponseEntity<List<ForumComment>> getForumComments(@PathVariable("forumId") int forumId,HttpSession session)
			{
				
				Users user=(Users) session.getAttribute("loggedInUser");
				
				
				
				List<ForumComment> forumComments= forumCommentDao.getForumCommentsByForumId(forumId);
				if((forumComments==null)||(user==null))
				{
					return new ResponseEntity<List<ForumComment>>(forumComments,HttpStatus.NO_CONTENT);
				}
				return new ResponseEntity<List<ForumComment>>(forumComments, HttpStatus.OK);
				
			}
			
		//*********************************Create forum comment*****************************************//
		
		
		@RequestMapping(value="/saveForumComment/", method=RequestMethod.POST)
		public ResponseEntity<ForumComment> creatForumComment(@RequestBody ForumComment forumComment,HttpSession session){
				Users user=(Users) session.getAttribute("loggedInUser");
				//checking if the user does not exist or the forum does not exist
					Forum forum=forumDao.getForumByForumId(forumComment.getForumId());
					if((user==null)||
						(forum==null))	
							{
								forumComment.setErrorCode("404");
								forumComment.setErrorMessage("forumcomment Not Created");
								return new ResponseEntity<ForumComment>(forumComment,HttpStatus.OK);
					}
				Date forumCommentDate=new Date(System.currentTimeMillis());
				
				forumComment.setForumCommentDate(forumCommentDate);
				forumComment.setUserId(user.getUserId());
				forumComment.setUserName(user.getUserName());
				forumCommentDao.save(forumComment);
				forum.setForumCommentCount(forum.getForumCommentCount()+1);
				System.out.println("at forum comment count**");
				forumDao.updateForum(forum);
				return new ResponseEntity<ForumComment>(forumComment,HttpStatus.OK);
				
			}	
			
		//*********************************Update forum comment*****************************************//		

			@RequestMapping(value="/updateForumComment/{id}", method=RequestMethod.PUT)
		public ResponseEntity<ForumComment> updateForumComment(@PathVariable("id") int id, @RequestBody ForumComment forumComment,HttpSession session){
			Users user=(Users) session.getAttribute("loggedInUser");
			if(((Users)session.getAttribute("loggedInUser")).getUserId()!=forumComment.getUserId()||(forumCommentDao.getByForumCommentId(forumComment.getForumCommentId()) == null)){
				forumComment =new ForumComment();
				forumComment.setErrorMessage("Forum does not exist with id : " +id);
				return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NO_CONTENT);
			}
			Date forumCommentDate=new Date(System.currentTimeMillis());
				
				forumComment.setForumCommentDate(forumCommentDate);
				forumComment.setUserId(user.getUserId());
				forumComment.setUserName(user.getUserName());
			forumCommentDao.update(forumComment);
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}	
		
		//*********************************Delete forum comment*****************************************//	
		
		@RequestMapping(value="/deleteForumComment/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<ForumComment>deleteForumComment(@PathVariable("id")int id,HttpSession session){
			ForumComment forumComment=forumCommentDao.getByForumCommentId(id);
			if(((Users)session.getAttribute("loggedInUser")).getUserId()!= forumComment.getUserId()||(forumComment == null)){
				forumComment = new ForumComment();
				forumComment.setErrorMessage("ForumComment does not exist with forumComment id : " + id);
				return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
				
			}
			forumCommentDao.delete(forumComment);
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		
			//*********************************Delete forum *****************************************//	
			
			@RequestMapping(value="/forum/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Forum>deleteForum(@PathVariable("id")int id,HttpSession session){
			Forum forum=forumDao.getForumByForumId(id);
			if(((Users)session.getAttribute("loggedInUser")).getUserId()!= forum.getUserId()||(forum == null)){
				forum = new Forum();
				forum.setErrorMessage("Forum does not exist with id : " + id);
				return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
				
			}
			forumDao.deleteForum(forum);
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		
		
		//******************************List Approved Forum Comment********************************************//

		@RequestMapping(value= "/approvedForum",method=RequestMethod.GET)
		public ResponseEntity<List<Forum>>getEveryApprovedForums(){
			List<Forum> forums=forumDao.getAllApprovedForums();
			if(forums.isEmpty()){
				return new ResponseEntity<List<Forum>>(forums,HttpStatus.NO_CONTENT);
			}
			System.out.println(forums.size());
			System.out.println("kkkkkk");
			return new ResponseEntity<List<Forum>>(forums,HttpStatus.OK);
		}

		//******************************Update Forum********************************************//	
		
		@RequestMapping(value="/forum/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Forum> updateForum(@PathVariable("id") int id, @RequestBody Forum forum,HttpSession session){
			if( ((Users)session.getAttribute("loggedInUser")).getUserId()!= forum.getUserId()||(forumDao.getForumByForumId(forum.getForumId())==null)){
				forum =new Forum();
				forum.setErrorMessage("Forum does not exist with id : " +forum.getForumId());
				return new ResponseEntity<Forum>(forum, HttpStatus.NO_CONTENT);
			}
			forum.setForumCreationDate(new Date(System.currentTimeMillis()));
			forum.setForumStatus("Pending");
			forum.setForumCommentCount(0);
			forum.setUserId(user.getUserId());
			forum.setUserName(user.getUserName());
			forumDao.updateForum(forum);
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		
		//******************************Forum Comment By User ID********************************************//	
		
		@RequestMapping(value= "/forumComment/{id}", method=RequestMethod.GET)
		public ResponseEntity<List<ForumComment>> getAllForumCommentsByUserId(@PathVariable("id")String id){
			List<ForumComment> forumComments=forumCommentDao.listByUserId(id);
			if(forumComments.isEmpty()){
				forumComment.setErrorMessage("Forum does not exist with id : " +id);
				return new ResponseEntity<List<ForumComment>>(forumComments,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<ForumComment>>(forumComments,HttpStatus.OK);
		}
		
		//******************************All Forum Comment********************************************// 
		
		@RequestMapping(value="/forumComments", method=RequestMethod.GET)
		@ResponseBody
		
		public ResponseEntity<List<ForumComment>> getAllForumComment(){
			List<ForumComment> forums=forumCommentDao.getAllForumComments();
			if(forums.isEmpty()){
				forumComment.setErrorMessage("ForumComment does not exist at all" );
				return new ResponseEntity<List<ForumComment>>(forums,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<ForumComment>>(forums,HttpStatus.OK);
		}
		
		//******************************All By Forum Comment ID********************************************// 
		
		@RequestMapping(value= "/forumCommentById/{id}",method=RequestMethod.GET)
		public ResponseEntity<ForumComment>getAllForumsByForumCommentId(@PathVariable("id")int id){
			ForumComment forumComment = forumCommentDao.getByForumCommentId(id);
			if (forumComment == null){
				forumComment = new ForumComment();
				forumComment.setErrorMessage("ForumComment does not exist with id : " + id);
					return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
	
	
}
