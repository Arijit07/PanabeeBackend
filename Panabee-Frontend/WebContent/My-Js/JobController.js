'use strict'

app.controller('JobController', ['JobService', '$scope', '$location', '$rootScope','$cookieStore','$localStorage',
	function(JobService, $scope, $location, $rootScope,$cookieStore,$localStorage) {
		console.log('JobController...');

		var self = this;
		self.job = {
				jobId : '', 
				jobProfile : '', 
				jobDescription : '', 
				jobQualification : '',
				jobStatus : '', 
				jobPostDate : '',
					errorCode: '',
					errorMessage: '',
		};

		self.jobs = [];
		
		self.vacantJob = {
				jobId : '', 
				jobProfile : '', 
				jobDescription : '', 
				jobQualification : '',
				jobStatus : '', 
				jobPostDate : '',
					errorCode: '',
					errorMessage: '',
		};

		self.VacantJobs = [];
		
		self.jobApplication = {
				
				jobApplicationId : '',
				userId : '',
				jobId : '',
				jobApplicationStatus : '',
				errorCode: '',
				errorMessage: ''
		};
		
		self.jobApplications = [];	
		
		self.myJobApplication = {
				
				jobApplicationId : '',
				userId : '',
				jobId : '',
				jobApplicationStatus : '',
				errorCode: '',
				errorMessage: ''
		};
		
		self.myJobApplications = [];	

		self.listJobs = function() {
			console.log("-->JobController : calling 'listJobs' method.");
			JobService
						.listJobs()
						.then(function(d) {
							self.jobs = d;
						},
						function(errResponse) {
							console.error("Error while getting job list.")
						});
		};		
		self.listJobs();
		
		
		
		self.listOpenJobs = function() {
			console.log("-->JobController : calling 'listOpenJobs' method.");
			JobService
						.listOpenJobs()
						.then(function(d) {
							self.vacantJobs = d;
						},
						function(errResponse) {
							console.error("Error while getting job list.")
						});
		};		
		self.listOpenJobs();
		

		self.getMyAppliedJobs = function() {
			console.log('calling the method getMyAppliedJobs...');
			JobService
						.getMyAppliedJobs()
						.then(function(d) {
							self.jobApplications = d;
							$location.path('/list_alljobapplied');
						},
						function(errResponse) {
							console.error('Error while fetching all applied jobs...');
						});
		};
		
		
		
		
		
		
		self.fetchAllJobApplications = function(id)
		{
			console.log("-->JobController : calling fetchAllJobApplications method with id : "+ id);
			JobService.fetchAllJobApplications(id).then
			(function(d) 
			{
				self.myJobApplications = d;
				$rootScope.myJobApplications=self.myJobApplications;
				console.log($rootScope.myJobApplications);
				$location.path('/list_alljobapplications');
			},
			function(errResponse) 
			{
				console.error('Error while fetching BlogApplications...');
			}
			);
		};
		
		self.updateJobAsExpired = function(job,id) {
			console.log("-->JobController : calling updateJob method.");
			JobService.updateJobAsExpired (job,id).then(
	         function(d) {
					self.job = d;
					alert('Job Created Successfully...')
					console.log(self.job);
					},
			function(errResponse) {
			console.error('Error while updating job...')
				});
			};
			
			self.updateJobAsActive = function(job,id) {
				console.log("-->JobController : calling updateJob method.");
				JobService.updateJobAsActive (job,id).then(
		         function(d) {
						self.job = d;
						alert('Job Created Successfully...')
						console.log(self.job);
						},
				function(errResponse) {
				console.error('Error while updating job...')
					});
				};
		
		self.createJob = function(job) {
			console.log("-->JobController : calling 'createJob' method.");
			JobService
						.createJob(job)
						.then(function(d) {
							self.job = d;
							alert('Post job?')
							$location.path('/list_jobs');
						},
						function(errResponse) {
							console.error('Error while posting new Job...');
						});
		};
		
		self.getJob = function(id) {
			console.log("-->JobController : calling 'getJob' method with jobId : "+id);
			JobService
						.getJob(id)
						.then(function(d) {
							self.job = d;
							$rootScope.job= self.job;
							$location.path('/view_job');
						},
						function(errResponse) {
							console.error('Error while fetching job details...')
						});
		};
		
		self.rejectJobApplication = function(jobApplication, id) 
		{
			console.log("-->JobController : calling rejectJobApplication() method : JobApplication jobApplicationId is : " + id);
			console.log("-->JobController",self.jobApplication);
			JobService.rejectJobApplication(jobApplication, id).then
			(
					function(d)
					{
					alert('Reject Job Application?'),
					self.jobApplication=d,
					self.fetchAllJobApplications(id);
					/*$location.path('/list_rejectedjobapplications');*/
					},
					function(errResponse) 
					{
						console.error("Error while rejecting job application...")
					}
			);
		};
		
		self.approveJobApplication = function(jobApplication, id)
		{
			console.log("-->JobController : calling approveJobApplication() method : job application with job application id is : " + id);
			console.log("-->JobController",self.jobApplication);
			JobService.approveJobApplication(jobApplication, id).then
			(
					function(d)
					{
					alert('Accept Job Application?'),
					self.jobApplication=d,
					self.fetchAllJobApplications(id);
					/*$location.path('/list_approvedjobapplications');*/
					},
					function(errResponse) 
					{
						console.error("Error while approving job application...")
					}
			);
		};
		
		
		self.applyForJob = function(job,jobId) {
			console.log("-->JobController : calling 'applyForJob' method with jobId:"+jobId);
			JobService
						.applyForJob(job,jobId)
						.then(function(d) {
							self.jobApplication = d;
							alert("You have successfully applied for the job...");
							self.listJobs();
							console.log("-->JobController : ", self.jobApplication);
							console.log("-->JobController : ", self.job);
							$location.path('/list_alljobapplied');
						},
						function(errResponse) {
							console.error('Error while applying for job...')
						});

		};
		
		self.apply = function() {
			console.log("-->JobController : calling 'apply()' method.", self.job);
			self.applyForJob(job);
			console.log('Job applied successfully...', job);
		};
		
		self.submit = function() {
			{
				console.log("-->JobController : calling 'submit()' method.", self.job);
				self.createJob(self.job);
				console.log('Saving new Job', self.job);
			}
			self.reset();
		};
		self.reset = function() {
			console.log('submit a new job', self.job);
			self.job = {
					
					jobId : '', 
					jobProfile : '', 
					jobDescription : '', 
					jobQualification : '',
					jobStatus : '', 
					jobPostDate : '',
					errorCode: '',
					errorMessage: ''
			};
			$scope.myForm.$setPristine();	//reset form...
		};

	}]);