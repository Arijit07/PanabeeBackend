package com.niit.PanabeeBackend.dao;

import java.util.List;

import com.niit.PanabeeBackend.model.Job;
import com.niit.PanabeeBackend.model.JobApplication;



public interface JobDao {
	   public boolean saveJob(Job job);
		
		public boolean update(Job job);
		
		/*public boolean saveOrUpdate(Job job);*/
		
		public boolean delete(Job job);
		
		public Job getJobByJobId(int id);
		
		public List<Job> getAllJobs();
		
		public List<Job> listVacantJobs();
		
		
	}
