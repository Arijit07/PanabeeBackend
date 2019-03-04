package com.niit.PanabeeBackend.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.PanabeeBackend.dao.JobDao;
import com.niit.PanabeeBackend.model.Blog;
import com.niit.PanabeeBackend.model.Job;
import com.niit.PanabeeBackend.model.JobApplication;
import com.niit.PanabeeBackend.model.Users;



@Repository("jobDao")
@Transactional
public class JobDaoImpl implements JobDao {
	
private SessionFactory sessionFactory;
	
	public JobDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public boolean saveJob(Job job) {
		try
		{
			Session session = getSession();

			session.save(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean update(Job job) {
		try
		{
			Session session = getSession();

			session.update(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean delete(Job job) {
		try
		{
			Session session = getSession();

			session.delete(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public Job getJobByJobId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM Job s where s.jobId=:jobId");
		query.setParameter("jobId", id);
		Job s=(Job)query.uniqueResult();
		session.close();
		return s;
	}

	public List<Job> getAllJobs() {
		Session session = getSession();
		Query query=session.createQuery("from Job");
		List<Job> joblist=query.list();
		session.close();
		return joblist;
		
	}
	
	public List<Job> listVacantJobs() {
		Session session = getSession();
		Query query=session.createQuery("from Job where jobStatus=:jobStatus");
		query.setParameter("jobStatus", "Active" );
		List<Job> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}

	

}