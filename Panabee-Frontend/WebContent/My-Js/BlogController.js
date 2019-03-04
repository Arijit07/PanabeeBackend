'use strict'
app.controller
('BlogController', 
['BlogService', '$scope', '$location', '$rootScope','$cookieStore',
function(BlogService, $scope, $location, $rootScope,$cookieStore) 

{
		console.log('BlogController...');
		var self = this;
		
	self.blog =
	{
			blogId : '',  
			blogContent : '', 
			blogTitle : '',
			blogDate : '', 
			userId : '', 
			blogStatus : '',
			blogCountLike : '',
			userName:'',
			blogCommentCount:'',
			blogImage:'',
		errorCode : '',
		errorMessage : ''
	};

    self.blogs = [];
    self.approvedBlogs = [];
    
    self.blogComment = 
    {
    		blogCommentId : '',
    		blogId : '',
    		userId : '',
    		userName : '',
    		blogComment : '',
    		blogCommentDate:'',
			errorCode : '',
			errorMessage : ''
	}		
    
    self.blogComments = [];
    
    self.blogLike=
    	{
    		blogLikeId:'',
    		userId:'',
    		blogId:'',
    		userName:'',
    		blogLikeDate:'',
    		errorCode : '',
    		errorMessage : ''
    	}
    
    self.blogLikes=[];
    
    self.getSelectedBlog = function(id)
    {
		console.log("-->BlogController : calling getSelectedBlog method : getting blog with id : " + id);
		BlogService.getSelectedBlog(id).then
		(function(d)
				{
					self.blog = d;
					console.log(self.blog);
					$rootScope.blog= self.blog;
					console.log(' r id '+ $rootScope.blog.blogId);
					self.fetchAllBlogComments(self.blog.blogId);
					$location.path('/ViewBlogsById');
				}, 
				function(errResponse)
				{
					console.error('Error while fetching Blog...');
				}
		);
	};
    
	self.listblogs = function() 
	{
		console.log("-->BlogController : calling 'listBlogs' method.");
		BlogService.listBlogs().then
		(function(d) 
				{
						self.blogs = d;	
				},
				function(errResponse) 
				{
						console.error("Error while getting blog list.")
				}
		);
	};
	
	self.listblogs();
	
	/*  To fetch all blogs which are approved   */  	
	
		self.fetchAllApprovedBlogs = function() {
		console.log("--> BlogController : calling fetchAllAprovedBlogs method.");
		BlogService.fetchAllApprovedBlogs().then(
		function(d) {
		self.approvedBlogs = d;
				}, 
		function(errResponse) {
		console.error('Error while fetching Blogs...');
				});
	};
	
	self.fetchAllApprovedBlogs();
		
		 /*  To update blogComment details     */	
        
		self.updateBlogComment = function(blogComment,id) {
			console.log("-->BlogController : calling updateBlog method.");
			BlogService.updateBlogComment(blogComment,id).then(
	         function(d) {
					self.blogComment = d;
					alert('BlogComment Created Successfully...')
					console.log(self.blogComment);
					},
			function(errResponse) {
			console.error('Error while updating blogComment...')
				});
			};
	
	self.updateBlog = function(blog,id) {
		console.log("-->BlogController : calling updateBlog method.");
		BlogService.updateBlog(blog,id).then(
         function(d) {
				self.blog = d;
				alert('Blog Created Successfully...')
				console.log(self.blog);
				$location.path('/ViewApprovedBlogs');
				},
		function(errResponse) {
		console.error('Error while updating blog...')
			});
		};
		
		
	
	self.createBlog = function(blog) 
	{
		console.log("-->BlogController : calling 'createBlog' method.");
		BlogService.createBlog(blog).then
		(function(d)
				{
						self.blog = d;
						console.log(self.blog);
						alert('Post Blog')
						
				},
				function(errResponse)
				{
						console.error('Error while posting new Blog...');
				}
		);
	};
	
	self.approveBlog = function(blog, id)
	{
		console.log("-->BlogController : calling approveBlog() method : Blog id is : " + id);
		console.log("-->BlogController",self.blog);
		BlogService.approveBlog(blog, id).then
		(
				function(d)
				{
				alert('Accept Blog?'),
				self.blog=d,
				self.listblogs();
				$location.path('/ViewBlogs');
				},
				function(errResponse) 
				{
					console.error("Error while approving blog...")
				}
		);
	};

	self.rejectBlog = function(blog, id) 
	{
		console.log("-->BlogController : calling rejectBlog() method : Blog id is : " + id);
		console.log("-->BlogController",self.blog);
		BlogService.rejectBlog(blog, id).then
		(
				function(d)
				{
				alert('Reject Blog?'),
				self.blog=d,
				self.listblogs();
				$location.path('/ViewBlogs');
				},
				function(errResponse) 
				{
					console.error("Error while rejecting blog...")
				}
		);
	};
	
	self.likeBlog = function(blog, id)
	{
		console.log("-->BlogController : calling likeBlog() method : Blog id is : "+id);
		console.log("-->BlogController", self.blog);
		BlogService.likeBlog(blog, id).then
		( function() 
			{
			self.getSelectedBlog(id);
			self.listblogs;
			self.fetchAllBlogLikes(id);
			$location.path('/ViewBlogsById');
			} ,
			function(errResponse)
			{
				console.error("Error while liking the blog...");
			}
		);
		
	};

	self.fetchAllBlogComments = function(id)
	{
		console.log("-->BlogController : calling fetchAllBlogComments method with id : "+ id);
		BlogService.fetchAllBlogComments(id).then
		(function(d) 
		{
			self.blogComments = d;	
			$rootScope.blogcomment= self.blogComments;
			console.log(' r id '+ $rootScope.blogcomment.blogCommentId);
		},
		function(errResponse) 
		{
			console.error('Error while fetching BlogComments...');
		}
		);
	};
	
	
	
	self.fetchAllBlogLikes = function(id)
	{
		console.log("-->BlogController : calling fetchAllBlogLikes method with id : "+ id);
		BlogService.fetchAllBlogLikes(id).then
		(function(d) 
		{
			self.blogLikes = d;
		},
		function(errResponse) 
		{
			console.error('Error while fetching BlogLikes...');
		}
		);
	};
	
	self.createBlogComment = function(blogComment) {
		blogComment.blogId= $rootScope.blog.blogId ;
		console.log("-->BlogController : calling 'createBlogComment' method.", blogComment);
		console.log("-->BlogController BlogId :" +blogComment.blogId);
		BlogService.createBlogComment(blogComment).then
					(function(d) 
					{
						console.log('Current User :',$rootScope.currentUser.userId)
						self.blogComment = d;
						console.log(self.blogComment)
						
					},
					function(errResponse) {
						console.error('Error while creating blogComment...');
					}
					);
	};

	/*******************************************************************************************/
	
	
	self.submit = function() 
	{
		{
			console.log("-->BlogController : calling 'submit()' method.", self.blog);
			self.createBlog(self.blog);
			console.log('Saving new Blog', self.blog);
		}
		self.reset();
	};
	
	self.reset = function() 
	{
		console.log('submit a new blog', self.blog);
		self.blog = 
		{
				blogId : '', 
				blogReason : '', 
				blogContent : '', 
				blogTitle : '',
				blogDate : '', 
				userId : '', 
				blogStatus : '',
				blogCountLike : '',
				userName:'',
				blogCommentCount:'',
			errorCode : '',
			errorMessage : ''
		};
		$scope.myForm.$setPristine();	//reset blog form...
		};
		
		
		self.resetComment = function() 
		{
			console.log('submit a new BlogComment', self.blogComment);
			self.blogComment = {
					blogCommentId : '',
		    		blogId : '',
		    		userId : '',
		    		userName : '',
		    		blogComment : '',
		    		blogCommentDate:'',
					errorCode : '',
					errorMessage : ''
				};
			$scope.myForm.$setPristine(); // reset blogComment form...
		};
		
		/*  To fetch blog comment by id       */		
		
		self.getSelectedBlogCommentById = function(id) {
			console.log("-->BlogCommentController : calling getSelectedBlogCommentById method : getting blog Comment with  id : " + id);
			BlogService.getSelectedBlogCommentById(id).then(
					function(d) {
					self.blogComment = d;
					console.log('id '+ self.blogComment.blogCommentId);
					$rootScope.blogComment= self.blogComment;
					console.log(' r id '+ $rootScope.blogComment.blogCommentId);
					$location.path('/ViewBlogCommentById');
						}, 
					function(errResponse) {
					console.error('Error while fetching BlogComment...');
						});
							};
							
							/*  To fetch blog comment by UserId       */		
		
		self.getSelectedBlogCommentByUserId = function(id) {
			console.log("-->BlogCommentController : calling getSelectedBlogCommentByUserId method : getting blog Comment with UserId : " + id);
			BlogService.getSelectedBlogCommentByUserId(id).then(
					function(d) {
					self.blogComments = d;
					$rootScope.blogComments= self.blogComments;
					$location.path('/ViewBlogCommentByUserId');
						}, 
					function(errResponse) {
					console.error('Error while fetching BlogComment...');
						});
							};
		
	
} 
]
);
