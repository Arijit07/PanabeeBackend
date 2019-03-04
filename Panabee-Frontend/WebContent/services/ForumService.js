'use strict';
app.factory
('ForumService',
['$http', '$q', '$rootScope',
function($http, $q, $rootScope)
{
			console.log("ForumService...")
			var BASE_URL = 'http://localhost:9000/Panabee-Backend'
			return {
				
				
				
				
				listForums : function() 
		           {
						console.log("-->ForumService : calling 'listForums' method.");
						return $http.get(BASE_URL+'/forums').then
						(function(response) 
								{
										return response.data;
								},
								function(errResponse)
								{
										console.error('Error while getting Forum list...');
										return $q.reject(errResponse);
								}
						);
					},
					
					createForum : function(forum) 
					{
						console.log("-->ForumService : calling 'createForum' method." + forum);
						return $http.post(BASE_URL+'/forum/', forum).then
									(function(response) 
									{
										return response.data;
									},
									function(errResponse)
									{
										console.error('Error while posting new Forum...');
										return $q.reject(errResponse);
									}
									);
					},
					

					getSelectedForum : function(id) 
					{
						console.log("-->ForumService : calling getSelectedForum() method with id : " + id);
						return $http.get(BASE_URL+'/forum/'+ id).then
						(function(response) 
								{
										$rootScope.selectedForum = response.data;
										return response.data;
								},
								function(errResponse) 
								{
										console.error('Error while Fetching Forum.');
										return $q.reject(errResponse);
								}
						);
					},
					
						
					approveForum : function(forum, id)
					{
						console.log("-->ForumService : calling approveForum() method : getting forum with id : " + id);
						return $http.put(BASE_URL+'/approveForum/'+ id, forum).then
									(function(response) 
									{
										return response.data;
									},
									function(errResponse) 
									{
										console.log("Error while approving Forum");
										return $q.reject(errResponse);
									}
									);
					},
					
					rejectForum : function(forum, id) 
					{
						console.log("-->ForumService : calling rejectForum() method : getting forum with id : " + id);
						return $http.put(BASE_URL+'/rejectForum/'+ id, forum).then
									(function(response)
									{
										return response.data;
									},
									function(errResponse)
									{
										console.log("Error while rejecting Forum");
										return $q.reject(errResponse);
									}
								    );
					},
					
					fetchAllForumComments : function(id)
					{
						console.log("-->ForumService : calling 'fetchAllForumComments' method for id : " + id);
						return $http.get(BASE_URL + '/forumComments/'+id).then
						(function(response) 
								{
										$rootScope.selectedForumComments = response.data;
										return response.data;
								}, 
									function(errResponse) {
										console.error('Error while fetching ForumComments');
										return $q.reject(errResponse);
									});
					},
					
					updateForumComment: function(forumComment,id) {
								console.log("--> ForumService : calling 'updateForumComment' method with id : "+id);
								console.log(forumComment);
								return $http
									.put(BASE_URL+'/updateForumComment/'+id,forum)
									.then(function(response) {
									return response.data;
										},
									function(errResponse) {
									console.error('Error while updating forum');						
									return $q.reject(errResponse);
											});
								},
					updateForum: function(forum,id) {
								console.log("--> ForumService : calling 'updateForum' method with id : "+id);
								console.log(forum);
								return $http
									.put(BASE_URL+'/forum/'+id,forum)
									.then(function(response) {
									return response.data;
										},
									function(errResponse) {
									console.error('Error while updating forum');						
									return $q.reject(errResponse);
											});
								},
								
					
								
					
					fetchAllApprovedForums : function() {
							console.log("--> ForumService : calling 'fetchAllApprovedForums' method.");
							return $http
							.get(BASE_URL + '/approvedForum')
							.then(function(response) {
							return response.data;
							}, 
							function(errResponse) {
							console.error('Error while fetching Uss');
							return $q.reject(errResponse);
							});
							},
					
					createForumComment : function(forumComment)
					{
						console.log("-->ForumService : calling 'createForumComment' method.");
						return $http.post(BASE_URL + '/forumComment/', forumComment).then
						(function(response)
								{
										return response.data;
								}, 
								function(errResponse) 
								{
										console.error('Error while creating forumComment');
										return $q.reject(errResponse);
								}
						);
					}
					
				};

		}
		]
		);