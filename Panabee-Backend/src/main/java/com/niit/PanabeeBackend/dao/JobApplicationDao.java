package com.niit.PanabeeBackend.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.PanabeeBackend.model.JobApplication;

@Repository
public interface JobApplicationDao {
	
	public boolean saveJobApplication(JobApplication jobApplication);
	public boolean updateJobApplication(JobApplication jobApplication);
	public JobApplication getJobApplicationByJobApplicationId(int jobApplicationId);
	public List<JobApplication> jobApplicationsByJobId(int jobId);
	public List<JobApplication> listJobApplications();
	public List<JobApplication> jobApplicationsByUserId(String userId);
	public boolean isJobExist(String userId, int jobId);

}
