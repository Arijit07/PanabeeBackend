'use strict';

app.factory('JobService', ['$http', '$q', '$rootScope',
    function($http, $q, $rootScope) {
	console.log("JobService...");

		var BASE_URL='http://localhost:9000/Panabee-Backend'
			return {

			listJobs : function() {
				console.log("-->JobService : calling 'listJobs' method.");
				return $http
							.get(BASE_URL+'/jobs')
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while getting Job list...');
								return $q.reject(errResponse);
							});
			},
			
			listOpenJobs : function() {
				console.log("-->JobService : calling 'listJobs' method.");
				return $http
							.get(BASE_URL+'/jobsVacant')
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while getting Job list...');
								return $q.reject(errResponse);
							});
			},
			
			updateJobAsExpired: function(job,id) {
				console.log("--> JobService : calling 'updateJob' method with id : "+id);
				console.log(job);
				return $http
					.put(BASE_URL+'/jobAsExpired/'+id,job)
					.then(function(response) {
					return response.data;
						},
					function(errResponse) {
					console.error('Error while updating job');						
					return $q.reject(errResponse);
							});
				},
				
				updateJobAsActive: function(job,id) {
					console.log("--> JobService : calling 'updateJob' method with id : "+id);
					console.log(job);
					return $http
						.put(BASE_URL+'/jobAsActive/'+id,job)
						.then(function(response) {
						return response.data;
							},
						function(errResponse) {
						console.error('Error while updating job');						
						return $q.reject(errResponse);
								});
					},
			
			createJob : function(job) {
				console.log("-->JobService : calling 'createJob' method.");
				return $http
							.post(BASE_URL+'/job/', job)
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while posting new Job...');
								return $q.reject(errResponse);
							});
			},
			
			updateJob : function(job, id) {
				console.log("-->JobService : calling 'updateJob' method.");
				return $http
							.put(BASE_URL+'/job/'+id)
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error("Error while updating job.");
								return $q.reject(errResponse);
							});
			},

			getJob : function(id) {
				console.log("-->JobService : calling 'getJob' method with jobId : "+id);
				return $http
							.get(BASE_URL+'/jobs/'+id)
							.then(function(response) {
								$rootScope.selectedJob = response.data;
								return response.data;
							},
							function(errResponse) {
								console.error('Error while getting job details');
								return $q.reject(errResponse);
							});
			},
			
			listJobApplications : function(id) {
				console.log("-->JobService : calling 'listJobApplications' method");
				return $http
							.get(BASE_URL+'/allApplications/'+id)
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while getting JobApplication list...');
								return $q.reject(errResponse);
							});
			},

			getMyAppliedJobs : function() {
				return $http
							.get(BASE_URL+'/appliedJobs')
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while getting all applied jobs...');
								return $q.reject(errResponse);
							});
			},
			
			fetchAllJobApplications : function(id)
			{
				console.log("-->JobService : calling 'fetchAllJobApplications' method for id : " + id);
				return $http.get(BASE_URL + '/allApplications/'+id).then
				(function(response) 
						{
								$rootScope.selectedJobApplications = response.data;
								return response.data;
						}, 
							function(errResponse) {
								console.error('Error while fetching JobApplications');
								return $q.reject(errResponse);
							});
			},

			rejectJobApplication : function(jobApplication, id) 
			{
				console.log("-->JobService : calling rejectJobApplication() method : getting job application with job application id : " + id);
				return $http.put(BASE_URL+'/rejectJobApplication/'+ id, jobApplication).then
							(function(response)
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while rejecting job application");
								return $q.reject(errResponse);
							}
						    );
			},
			
			approveJobApplication : function(jobApplication, id) 
			{
				console.log("-->JobService : calling approveJobApplication() method : getting job application with job application id : " + id);
				return $http.put(BASE_URL+'/approveJobApplication/'+ id, jobApplication).then
							(function(response)
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while approving job application");
								return $q.reject(errResponse);
							}
						    );
			},

			
			
			

			applyForJob : function(job,jobId) {
				console.log("-->JobService : calling 'applyForJob' method with jobId:"+jobId);
				return $http
							.post(BASE_URL+'/jobApplication/'+jobId,job)
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while applying for Job...');
								return $q.reject(errResponse);
							});
			}
		};
}]);