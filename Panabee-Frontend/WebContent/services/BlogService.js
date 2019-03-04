'use strict';
app.factory
('BlogService', 
['$http', '$q', '$rootScope',
function($http, $q, $rootScope) 
{
console.log("BlogService...");
var BASE_URL='http://localhost:9000/Panabee-Backend'
return {
	
						/*      All Blog List    */
	
           listBlogs : function() 
           {
				console.log("-->BlogService : calling 'listBlogs' method.");
				return $http.get(BASE_URL+'/blogs').then
				(function(response) 
						{
								return response.data;
						},
						function(errResponse)
						{
								console.error('Error while getting Blog list...');
								return $q.reject(errResponse);
						}
				);
			},
			
			/*       Update Blog Comment Details                   */	
							
							updateBlogComment : function(blogComment,id) {
								console.log("--> BlogCommentService : calling 'updateBlogComment' method with id : "+id);
								console.log(blogComment);
								return $http
									.put(BASE_URL+'/updateBlogComment/'+id,blogComment)
									.then(function(response) {
									return response.data;
										},
									function(errResponse) {
									console.error('Error while updating Blog Comment');						
									return $q.reject(errResponse);
											});
								},
								
								
			
			
			
			
			
			
				
							/*      All Approved Blog Details       */	
			
			fetchAllApprovedBlogs : function() {
							console.log("--> BlogService : calling 'fetchAllApprovedBlogs' method.");
							return $http
							.get(BASE_URL + '/approvedBlog')
							.then(function(response) 
							{
							return response.data;
							}, 
							function(errResponse) 
							{
							console.error('Error while fetching Uss');
							return $q.reject(errResponse);
							});
							},
			
							
							
			listBlogLikes: function(id) 
	           {
					console.log("-->BlogService : calling 'listBlogLikes' method.");
					return $http.get(BASE_URL+'/likeBlog/'+id).then
					(function(response) 
							{
						$rootScope.selectedBlogLikes = response.data;
						return response.data;
							},
							function(errResponse)
							{
									console.error('Error while getting Blog list...');
									return $q.reject(errResponse);
							}
					);
				},
				
			createBlog : function(blog) 
			{
				console.log("-->BlogService : calling 'createBlog' method.");
				return $http.post(BASE_URL+'/blog/', blog).then
							(function(response) 
							{
								return response.data;
							},
							function(errResponse)
							{
								console.error('Error while posting new Blog...');
								return $q.reject(errResponse);
							}
							);
			},
			
			/*       Update Blog Details                   */	
							
							updateBlog : function(blog,id) {
								console.log("--> BlogService : calling 'updateBlog' method with id : "+id);
								console.log(blog);
								return $http
									.put(BASE_URL+'/blog/'+id,blog)
									.then(function(response) {
									return response.data;
										},
									function(errResponse) {
									console.error('Error while updating Blog');						
									return $q.reject(errResponse);
											});
								},
								

			getSelectedBlog : function(id) 
			{
				console.log("-->BlogService : calling getSelectedBlog() method with id : " + id);
				return $http.get(BASE_URL+'/blog/'+ id).then
				(function(response) 
						{
								$rootScope.selectedBlog = response.data;
								return response.data;
						},
						function(errResponse) 
						{
								console.error('Error while Fetching Blog.');
								return $q.reject(errResponse);
						}
				);
			},
			
			approveBlog : function(blog, id)
			{
				console.log("-->BlogService : calling approveBlog() method : getting blog with id : " + id);
				return $http.put(BASE_URL+'/approveBlog/'+ id, blog).then
							(function(response) 
							{
								return response.data;
							},
							function(errResponse) 
							{
								console.log("Error while approving Blog");
								return $q.reject(errResponse);
							}
							);
			},
			
			rejectBlog : function(blog, id) 
			{
				console.log("-->BlogService : calling rejectBlog() method : getting blog with id : " + id);
				return $http.put(BASE_URL+'/rejectBlog/'+ id, blog).then
							(function(response)
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while rejecting Blog");
								return $q.reject(errResponse);
							}
						    );
			},
			
			likeBlog : function(blog, id) 
			{
				console.log("-->BlogService : calling likeBlog() method : getting blog with id : " + id);
				return $http.put(BASE_URL+'/likeBlog/'+id, blog).then
							(function(response) 
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while liking Blog.");
								return $q.reject(errResponse);
							}
							);
			},
			
			fetchAllBlogComments : function(id)
			{
				console.log("-->BlogService : calling 'fetchAllBlogComments' method for id : " + id);
				return $http.get(BASE_URL + '/blogCommentByBlogId/'+id).then
				(function(response) 
						{
								$rootScope.selectedBlogComments = response.data;
								return response.data;
						}, 
							function(errResponse) {
								console.error('Error while fetching BlogComments');
								return $q.reject(errResponse);
							});
			},
			
			fetchAllBlogLikes : function(id)
			{
				console.log("-->BlogService : calling 'fetchAllBlogLikes' method for id : " + id);
				return $http.get(BASE_URL + '/likeBlog/'+id).then
				(function(response) 
						{
								$rootScope.selectedBlogLikes = response.data;
								return response.data;
						}, 
							function(errResponse) {
								console.error('Error while fetching BlogLikes');
								return $q.reject(errResponse);
							});
			},
			
			createBlogComment : function(blogComment)
			{
				console.log("-->BlogService : calling 'createBlogComment' method.");
				return $http.post(BASE_URL + '/blogComment/', blogComment).then
				(function(response)
						{
								return response.data;
						}, 
						function(errResponse) 
						{
								console.error('Error while creating blogComment');
								return $q.reject(errResponse);
						}
				);
			},
			
			 /*        BlogComments Details by blog comment id     */	
				
				
		getSelectedBlogCommentById : function(id) {
					console.log("-->BlogCommentService : calling getSelectedBlogCommentById() method with blog comment id : " + id);
					return $http
						.get(BASE_URL+'/blogCommentById/'+ id)
						.then(function(response) {
						$rootScope.selectedBlogComment = response.data;
						return response.data;
							},
						function(errResponse) {
						console.error('Error while Fetching Blog Comment.');
						return $q.reject(errResponse);
									});
						},	
						
						 /*        BlogComments Details by user id     */	
				
				
		getSelectedBlogCommentByUserId : function(id) {
					console.log("-->BlogCommentService : calling getSelectedBlogCommentByUserId() method with user id : " + id);
					return $http
						.get(BASE_URL+'/blogCommentByUserId/'+ id)
						.then(function(response) {
						$rootScope.selectedBlogComment = response.data;
						return response.data;
							},
						function(errResponse) {
						console.error('Error while Fetching Blog Comment.');
						return $q.reject(errResponse);
									});
						}
			
		};

}]);