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

import com.niit.PanabeeBackend.dao.JobApplicationDao;
import com.niit.PanabeeBackend.dao.JobDao;
import com.niit.PanabeeBackend.model.Job;
import com.niit.PanabeeBackend.model.JobApplication;
import com.niit.PanabeeBackend.model.Users;






@RestController
public class JobController {
	
	@Autowired
	JobDao jobDao;
	@Autowired
	Job job;
	@Autowired
	JobApplication jobApplication;
	@Autowired
	JobApplicationDao jobApplicationDao;
	
	
	
	


	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Job>> listJobs(HttpSession session)
	{
		try
		{
		Users user=(Users) session.getAttribute("loggedInUser");
		if(user==null)  return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		List<Job> jobs = jobDao.getAllJobs();
		if (jobs.isEmpty())
		{
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
		}
		catch(NullPointerException e)
		{
			System.out.println("user not logged in");
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
	@RequestMapping(value="/jobs/{jobId}", method=RequestMethod.GET)
	
	public ResponseEntity<Job> getJobByJobId(@PathVariable("jobId") int jobId,HttpSession session)
	{
		try
		{
			
				Users user=(Users) session.getAttribute("loggedInUser");
				System.out.println(user.getUserId()+" at getJobByJobId");
				Job job=jobDao.getJobByJobId(jobId);
				if(job==null)
				{
					job.setErrorCode("404");
					job.setErrorMessage("job not found");
					return new ResponseEntity<Job>(job,HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<Job>(job,HttpStatus.OK);
					}
			
			
		}
		catch(NullPointerException e)
		{
			Job job=new Job();
			job.setErrorCode("404");
			job.setErrorMessage("user not logged in");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	}
	
	
	
	
	@RequestMapping(value="/job/", method=RequestMethod.POST)
	
	public ResponseEntity<Job> saveJob(@RequestBody Job job,HttpSession session)
	{
		try
		{
		Users user=(Users) session.getAttribute("loggedInUser");
		
		if(!(user.getUserRole().equals("ADMIN")))
		{
			
			job.setErrorCode("404");
			job.setErrorMessage("Forum Not Created");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
				
		}
		}
		catch(NullPointerException e)
		{
			job.setErrorCode("404");
			job.setErrorMessage("Admin Not logged in");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
			
		}
		job.setJobPostDate(new Date(System.currentTimeMillis()));
		job.setJobStatus("Active");
		jobDao.saveJob(job);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	
	
	@RequestMapping(value="/jobAsExpired/{jobId}", method=RequestMethod.PUT)
	public ResponseEntity<Job>updateJobAsExpired(@PathVariable("jobId") int jobId,HttpSession session)
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			Job job=jobDao.getJobByJobId(jobId);
			if((user.getUserRole().equals("ADMIN"))&&(job!=null)){
				job.setJobStatus("Expired");
				job.setJobId(job.getJobId());
				job.setJobDescription(job.getJobDescription());
				job.setJobProfile(job.getJobProfile());
				job.setJobQualification(job.getJobQualification());
				jobDao.update(job);
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}
			else
			{
				 jobApplication=new JobApplication();
				job.setErrorCode("404");
				job.setErrorMessage("not found");
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Job job=new Job();
			job.setErrorCode("404");
			job.setErrorMessage("admin not logged in");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/jobAsActive/{jobId}", method=RequestMethod.PUT)
	public ResponseEntity<Job>updateJobAsActive(@PathVariable("jobId") int jobId,HttpSession session)
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			Job job=jobDao.getJobByJobId(jobId);
			if((user.getUserRole().equals("ADMIN"))&&(job!=null)){
				job.setJobStatus("Active");
				job.setJobId(job.getJobId());
				job.setJobDescription(job.getJobDescription());
				job.setJobProfile(job.getJobProfile());
				job.setJobQualification(job.getJobQualification());
				jobDao.update(job);
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}
			else
			{
				 jobApplication=new JobApplication();
				job.setErrorCode("404");
				job.setErrorMessage("not found");
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Job job=new Job();
			job.setErrorCode("404");
			job.setErrorMessage("admin not logged in");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	}

	//for job application
	
	
	
	@RequestMapping(value="/jobApplication/{jobId}", method=RequestMethod.POST)
	public ResponseEntity<JobApplication> saveJobApplication(@PathVariable("jobId")int jobId, HttpSession session)
	{
		try
		{
			System.out.println("at apply to job");
		Users user=(Users) session.getAttribute("loggedInUser");
		System.out.println("at get userId");
		String userId=user.getUserId();
			if(jobApplicationDao.isJobExist(userId, jobId))
			{
				JobApplication jobApplication=new JobApplication();
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("JobApplication Not Created");
				return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
			}
			
			JobApplication jobApplication=new JobApplication();
			jobApplication.setJobId(jobId);
			jobApplication.setUserId(userId);
			jobApplication.setJobApplicationStatus("Applied");
			jobApplicationDao.saveJobApplication(jobApplication);
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		
		
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			JobApplication jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("User Not loggedin");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
		
		
	}
	
	
	@RequestMapping(value="/approveJobApplication/{jobApplicationId}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplication>approveJobApplication(@PathVariable("jobApplicationId") int jobApplicationId,HttpSession session)
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			JobApplication jobApplication=jobApplicationDao.getJobApplicationByJobApplicationId(jobApplicationId);
			if((user.getUserRole().equals("ADMIN"))&&(jobApplication!=null))
			{
				jobApplication.setJobApplicationStatus("Selected");
				jobApplicationDao.updateJobApplication(jobApplication);
				return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
			}
			else
			{
				 jobApplication=new JobApplication();
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("not found");
				return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			JobApplication jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("admin not logged in");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value="/rejectJobApplication/{jobApplicationId}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplication>rejectJobApplication(@PathVariable("jobApplicationId") int jobApplicationId,HttpSession session)
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			JobApplication jobApplication=jobApplicationDao.getJobApplicationByJobApplicationId(jobApplicationId);
			if((user.getUserRole().equals("ADMIN"))&&(jobApplication!=null)){
				jobApplication.setJobApplicationStatus("Rejected");
				jobApplicationDao.updateJobApplication(jobApplication);
				return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
			}
			else
			{
				 jobApplication=new JobApplication();
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("not found");
				return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			JobApplication jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("admin not logged in");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
	}
	
	
	
	@RequestMapping(value= "/appliedJobs",method=RequestMethod.GET)
	public ResponseEntity<List<JobApplication>> getAppliedJobs(HttpSession session)
	{
		
		try
		{
		
			Users user=(Users) session.getAttribute("loggedInUser");
			return new ResponseEntity<List<JobApplication>>(jobApplicationDao.jobApplicationsByUserId(user.getUserId()),HttpStatus.OK);
		}
		catch(NullPointerException e)
		{ 
			e.printStackTrace(); 
			System.out.println("user not logged in");
			return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
		}
		
		
	}
	
	
	@RequestMapping(value= "/allApplications/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<List<JobApplication>> getJobApplications(@PathVariable("jobId")int jobId, HttpSession session)
	{
		try
		{
			Users user=(Users) session.getAttribute("loggedInUser");
			if((!user.getUserRole().equals("ADMIN"))||
				(jobDao.getJobByJobId(jobId)==null)){
				return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<JobApplication>>(jobApplicationDao.jobApplicationsByJobId(jobId),HttpStatus.OK);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			System.out.println("user not logged in");
			return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
		}
		
	}

		@RequestMapping(value="/jobsVacant", method=RequestMethod.GET)
	
	public ResponseEntity<List<Job>> listJobVacant(){
		List<Job> jobVacantList=jobDao.listVacantJobs();
		if(jobVacantList.isEmpty()){
			return new ResponseEntity<List<Job>>(jobVacantList,HttpStatus.NO_CONTENT);
		}
		System.out.println(jobVacantList.size());
		System.out.println("kkkkkk");
		return new ResponseEntity<List<Job>>(jobVacantList,HttpStatus.OK);
	}


}
