package com.niit.PanabeeBackend.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.PanabeeBackend.dao.UserDao;
import com.niit.PanabeeBackend.model.Users;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory;
	
	public  UserDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}


	public boolean saveUser(Users user) {
		try
		{
			Session session = getSession();
			
			session.save(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean updateUser(Users user) {
		try
		{
			Session session = getSession();

			session.update(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean deleteUser(Users user) {
		try
		{
			Session session = getSession();
			
			user.setUserStatus("De-Active");

			session.update(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
		
	}

	public List<Users> getAllUser() {
		Session session = getSession();
		Query query=session.createQuery("from Users");
		List<Users> userlist=query.list();
		session.close();
		return userlist;
		
	}

	public Users UserAuthentication(String userId, String userPassword) {
		Session session = getSession();
		Query query=session.createQuery("FROM Users s where userId=:userId and userPassword=:userPassword");
		query.setParameter("userId", userId);
		query.setParameter("userPassword", userPassword);
		Users s=(Users)query.uniqueResult();
		if( s==null)
		{
		 return s;
				 
		}
		 else
		 {
			
		 return s;
		 }
		
	}

	public Users getUserByUserId(String id) {
	
	     Session session = getSession();
		Query query=session.createQuery("FROM Users s where userId=:userId");
		query.setParameter("userId", id);
		Users s=(Users)query.uniqueResult();
		session.close();
		return s;
		
	}

}

