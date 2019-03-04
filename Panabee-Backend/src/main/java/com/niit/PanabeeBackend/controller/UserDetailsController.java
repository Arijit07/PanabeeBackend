package com.niit.PanabeeBackend.controller;

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

import com.niit.PanabeeBackend.dao.UserDao;
import com.niit.PanabeeBackend.model.Users;

@RestController
public class UserDetailsController {
	
	@Autowired
	UserDao userDao;
	
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test(){
		return "test";
	}
	
	

	@RequestMapping(value="/users", method=RequestMethod.GET)
	@ResponseBody
	
	
	
	public ResponseEntity<List<Users>> getAllUser(){
		List<Users> users=userDao.getAllUser();
		if(users.isEmpty()){
			return new ResponseEntity<List<Users>>(users,HttpStatus.NO_CONTENT);
		}
		System.out.println(users.size());
		System.out.println("kkkkkk");
		return new ResponseEntity<List<Users>>(users,HttpStatus.OK);
	}

	@RequestMapping(value="/user/", method=RequestMethod.POST)
	public ResponseEntity<Users> creatUser(@RequestBody Users users){
		if(userDao.getUserByUserId(users.getUserId())==null){
			users.setUserRole("USER");
			users.setUserStatus("Active");
			users.setUserIsOnline("No");
			userDao.saveUser(users);
			return new ResponseEntity<Users>(users,HttpStatus.OK);
		}
		users.setErrorMessage("User already exist with id : "+users.getUserId());
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Users> updateuser(@PathVariable("id") String id, @RequestBody Users users){
		if(userDao.getUserByUserId(id)==null){
			users =new Users();
			users.setErrorMessage("User does not exist with id : " +users.getUserId());
			return new ResponseEntity<Users>(users, HttpStatus.NO_CONTENT);
		}
		users.setUserRole("USER");
		users.setUserStatus("Active");
		users.setUserIsOnline("No");
		userDao.updateUser(users);
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Users>deleteUser(@PathVariable("id")String id){
		Users users=userDao.getUserByUserId(id);
		if(users == null){
			users = new Users();
			users.setErrorMessage("User does not exist with id : " + id);
			return new ResponseEntity<Users>(users, HttpStatus.NOT_FOUND);
			
		}
		userDao.deleteUser(users);
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/user/{id}",method=RequestMethod.GET)
	public ResponseEntity<Users>getAllUserByUserId(@PathVariable("id")String id){
		Users users = userDao.getUserByUserId(id);
		if (users == null){
			users = new Users();
			users.setErrorMessage("User does not exist with id : " + id);
				return new ResponseEntity<Users>(users, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/user/logout/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Users>logout(@PathVariable("id") String id, @RequestBody Users users,HttpSession session){
		System.out.println("lllll "+users.getUserName());	
		users.setUserIsOnline("No");
			userDao.updateUser(users);	
			session.invalidate();
			 
			
				return new ResponseEntity<Users>(new Users(), HttpStatus.OK);
				
		}
	
	@RequestMapping(value = "/user/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<Users> UserAuthentication(@RequestBody Users users, HttpSession session){
		users = userDao.UserAuthentication(users.getUserId(), users.getUserPassword());
		if(users == null){
			users = new Users();
			users.setErrorMessage("Invalid userId or password...");
		}
		else {
			session.setAttribute("loggedInUser", users);
			System.out.println("logged session set ");
			session.setAttribute("loggedInUserID", users.getUserId());
			users.setUserIsOnline("Yes");
			userDao.updateUser(users);
		}
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/activateUser/{userId}", method=RequestMethod.PUT)
	public ResponseEntity<Users> activateUser(@PathVariable("userId") String userId,HttpSession session)
	{
		try
		{
				Users user=userDao.getUserByUserId(userId);
				if((((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN"))||
					(user!=null)){
					user.setUserStatus("Active");
					userDao.updateUser(user);
					return new  ResponseEntity<Users>(user,HttpStatus.OK);
				}
				else
				{
					user=new Users();
					user.setErrorCode("404");
					user.setErrorMessage("failed to activate user");
					return new ResponseEntity<Users>(user,HttpStatus.OK);
				}
			}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Users user=new Users();
			user.setErrorCode("404");
			user.setErrorMessage("Admin not logged in");
			return new ResponseEntity<Users>(user,HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value="/deactivateUser/{userId}", method=RequestMethod.PUT)
	public ResponseEntity<Users> deactivateUser(@PathVariable("userId") String userId,HttpSession session)
	{
		try
		{
				Users user=userDao.getUserByUserId(userId);
				if((((Users)session.getAttribute("loggedInUser")).getUserRole().equals("ADMIN"))||
					(user!=null)){
					userDao.deleteUser(user);
					return new  ResponseEntity<Users>(user,HttpStatus.OK);
				}
				else
				{
					user=new Users();
					user.setErrorCode("404");
					user.setErrorMessage("failed to deactivate user");
					return new ResponseEntity<Users>(user,HttpStatus.OK);
				}
			}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Users user=new Users();
			user.setErrorCode("404");
			user.setErrorMessage("Admin not logged in");
			return new ResponseEntity<Users>(user,HttpStatus.OK);
		}
	}
	
}
