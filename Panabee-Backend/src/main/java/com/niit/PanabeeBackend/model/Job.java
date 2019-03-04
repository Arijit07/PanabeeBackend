package com.niit.PanabeeBackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Job extends BaseDomain implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_AUTO_JOB_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private int jobId;
	
	private String jobProfile;
	
	private String jobDescription;
	
	private String jobQualification;
	
	private String jobStatus;
	
	private Date jobPostDate;
	
	/**
	 *  getters/setters for all the fields taken... 
	 */
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobProfile() {
		return jobProfile;
	}
	public void setJobProfile(String jobProfile) {
		this.jobProfile = jobProfile;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getJobQualification() {
		return jobQualification;
	}
	public void setJobQualification(String jobQualification) {
		this.jobQualification = jobQualification;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public Date getJobPostDate() {
		return jobPostDate;
	}
	public void setJobPostDate(Date jobPostDate) {
		this.jobPostDate = jobPostDate;
	}
	
}
