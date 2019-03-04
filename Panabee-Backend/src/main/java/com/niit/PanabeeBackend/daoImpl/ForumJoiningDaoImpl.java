package com.niit.PanabeeBackend.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.PanabeeBackend.dao.ForumJoiningDao;
import com.niit.PanabeeBackend.model.ForumJoining;

@Repository("forumJoiningDao")
@Transactional
public class ForumJoiningDaoImpl implements ForumJoiningDao{

	@Autowired
	private SessionFactory sessionFactory;
	

	public ForumJoiningDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession(){
		return sessionFactory.openSession();
	}
		

	

	

	public boolean saveForumJoining(ForumJoining forumJoining) {
		// TODO Auto-generated method stub
		Session session=getSession();
		try{
			session.save(forumJoining);
			session.flush();
			session.close();
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean isExist(String userId, int forumId) {
		// TODO Auto-generated method stub
Session session=getSession();
		
		try{
			Query query=session.createQuery("from ForumJoining where userId = ? and forumId = ?");
			query.setString(0, userId);
			query.setInteger(1, forumId);
			@SuppressWarnings("unchecked")
			List<ForumJoining> forumJoinings=query.list();
			return !forumJoinings.isEmpty();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	public List<ForumJoining> getForumJoiningsByUserId(String userId) {
		// TODO Auto-generated method stub
		Session session=getSession();
		
		try{
			Query query=session.createQuery("from ForumJoining where userId = ?");
			query.setString(0, userId);
			@SuppressWarnings("unchecked")
			List<ForumJoining> forumJoinings=query.list();
			return forumJoinings;
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	public List<ForumJoining> getForumJoiningsByForumId(int forumId) {
		// TODO Auto-generated method stub
Session session=getSession();
		
		try{
			Query query=session.createQuery("from ForumJoining where forumId = ?");
			query.setInteger(0, forumId);
			@SuppressWarnings("unchecked")
			List<ForumJoining> forumJoinings=query.list();
			return forumJoinings;
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

}
