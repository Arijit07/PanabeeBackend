package com.niit.PanabeeBackend.dao;

import java.util.List;

import com.niit.PanabeeBackend.model.Users;



public interface UserDao {

	public boolean saveUser(Users user);
	public boolean updateUser(Users user);
	public boolean deleteUser(Users user);
	public List<Users> getAllUser();
	public Users UserAuthentication(String userId, String userPassword);
	public Users getUserByUserId(String id);

}
