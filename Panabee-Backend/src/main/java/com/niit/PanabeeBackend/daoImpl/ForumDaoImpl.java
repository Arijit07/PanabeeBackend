package com.niit.PanabeeBackend.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.PanabeeBackend.dao.ForumDao;
import com.niit.PanabeeBackend.model.Forum;



@Repository("forumDao")
@Transactional
public class ForumDaoImpl implements ForumDao {

private SessionFactory sessionFactory;
	
	public ForumDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}
	
	public boolean saveForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.save(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean deleteForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.delete(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean updateForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.update(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public Forum getForumByForumId(int forumId) {
		Session session = getSession();
		Query query=session.createQuery("FROM Forum s where forumId=:forumId");
		query.setParameter("forumId", forumId);
		Forum  s=(Forum)query.uniqueResult();
		session.close();
		return s;
		
	}

	public List<Forum> getAllForums() {
		Session session = getSession();
		Query query=session.createQuery("from Forum");
		List<Forum> forumlist=query.list();
		session.close();
		return forumlist;
		
	}

	public List<Forum> getAllApprovedForums() {
	Session session = getSession();
	Query query=session.createQuery("FROM Forum s where forumStatus=:forumStatus");
	query.setParameter("forumStatus", "Approved");
	List<Forum> approvedForumList=query.list();
	session.close();
	return approvedForumList;
	
	}


}
