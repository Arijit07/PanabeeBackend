package com.niit.PanabeeBackend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.niit.PanabeeBackend.model.Blog;
import com.niit.PanabeeBackend.model.Users;
import com.niit.PanabeeBackend.dao.UserDao;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class UploadController {
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/Upload", method = RequestMethod.POST)
	//@Produces(MediaType.APPLICATION_JSON) 
	public ResponseEntity<String> continueFileUpload(HttpServletRequest request, HttpServletResponse response){
        MultipartHttpServletRequest mRequest;
	String filename = "upload.xlsx";
	try {
	   mRequest = (MultipartHttpServletRequest) request;
	   mRequest.getParameterMap();

	   Iterator itr = mRequest.getFileNames();
	   while (itr.hasNext()) {
	        MultipartFile mFile = mRequest.getFile((String)itr.next());
	        String fileName = mFile.getOriginalFilename();
	        System.out.println(fileName);
	              
	        java.nio.file.Path path = Paths.get("C://Users//acer123//workspace//Panabee-Frontend//WebContent//images" + filename);
	        Files.deleteIfExists(path);
	        InputStream in = mFile.getInputStream();
	        Files.copy(in, path);
		 }
	   } catch (Exception e) {
	        e.printStackTrace();
	   }
	return null;
	}

	
	
	 @RequestMapping(value = "/ImageUpload/{userId}", method = RequestMethod.POST)
	 public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file ,@PathVariable("userId") String userId ){
	  
	  String originalFilename = file.getOriginalFilename();
	     File destinationFile = new File("C://Users//acer123//workspace//Panabee-Frontend//WebContent//images//"+userId+".jpg");
	     try {
	                 
	      file.transferTo(destinationFile);
	      Users users= userDao.getUserByUserId(userId);
	     users.setUserImage("/Panabee-Frontend/images/"+userId+".jpg"); 
	     userDao.updateUser(users);
	     
	   System.out.println("File path "+destinationFile.getPath());
	   System.out.println("File size "+file.getSize());
	  } catch (Exception e) {
	   
	   e.printStackTrace();
	  }
	  
	  
	  return new ResponseEntity<String>("mvnvghhgf",HttpStatus.OK);
	 }
	
	
	

}
