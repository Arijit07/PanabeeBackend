'use strict';
app.controller
('ForumController', 
['$scope','ForumService','$location','$rootScope','$cookieStore',
function($scope, ForumService, $location, $rootScope,$cookieStore) 
{console.log("ForumController...")
			var self = this;
			self.forum = 
			{
					forumId : '',
					forumName : '',
					forumDescription : '',
					userId : '',
					forumCreationDate : '',
					forumStatus : '',
					userName:'',
					forumCommentCount:'',
				    errorCode : '',
				    errorMessage : ''	
			}
			self.forums = [];
			
			self.forumComment = 
			{
					forumCommentId : '',
					forumId : '',
					forumComment : '',
					forumCommentDate : '',
					userId : '',
					userName:'',
				    errorCode : '',
				    errorMessage : ''
			}			
			self.forumComments = [];
			
			self.approvedForums = [];

			
			
			
			self.getSelectedForum = function(id)
		    {
				console.log("-->ForumController : calling getSelectedForum method : getting forum with id : " + id);
				ForumService.getSelectedForum(id).then
				(function(d)
						{
							self.forum = d;
							$location.path('/ViewForumsById');
						}, 
						function(errResponse)
						{
							console.error('Error while fetching Forum...');
						}
				);
			};
		    
			self.listforums = function() 
			{
				console.log("-->ForumController : calling 'listForums' method.");
				ForumService.listForums().then
				(function(d) 
						{
								self.forums = d;	
						},
						function(errResponse) 
						{
								console.error("Error while getting forum list.")
						}
				);
			};
			
			self.listforums();
			
			self.createForum = function(forum) 
			{
				console.log("-->ForumController : calling 'createForum' method.");
				ForumService.createForum(forum).then
				(function(d)
						{
								self.forum = d;
								console.log(self.forum);
								alert('Post Forum')
								$location.path('/list_forums');
						},
						function(errResponse)
						{
								console.error('Error while posting new Forum...');
						}
				);
			};
			
			self.fetchAllApprovedForums = function() {
				console.log("--> ForumController : calling fetchAllAprovedForums method.");
		        ForumService.fetchAllApprovedForums().then(
		        function(d) {
		         self.approvedForums = d;
						}, function(errResponse) {
							console.error('Error while fetching Forums...');
						});
			       };
			
			self.fetchAllApprovedForums();
			
			self.approveForum = function(forum, id)
			{
				console.log("-->ForumController : calling approveForum() method : Forum id is : " + id);
				console.log("-->ForumController",self.forum);
				ForumService.approveForum(forum, id).then
				(
						function(d)
						{
						alert('Accept Forum?'),
						self.forum=d,
						self.listforums(),
						$location.path('/ViewForums');
						},
						function(errResponse) 
						{
							console.error("Error while approving forum...")
						}
				);
			};
			
			self.updateForumComment= function(forumComment,id) {
				console.log("-->ForumController : calling updateForum method.");
				ForumService.updateForumComment(forumComment,id).then(
		         function(d) {
						self.forumComment = d;
						alert('ForumComment Created Successfully...')
						console.log(self.forumComment);
						},
				function(errResponse) {
				console.error('Error while updating forumComment...')
					});
				};
			
			self.updateForum = function(forum,id) {
				console.log("-->ForumController : calling updateForum method.");
				ForumService.updateForum(forum,id).then(
		         function(d) {
						self.forum = d;
						alert('Forum Created Successfully...')
						console.log(self.forum);
						},
				function(errResponse) {
				console.error('Error while updating forum...')
					});
				};

			self.rejectForum = function(forum, id) 
			{
				console.log("-->ForumController : calling rejectForum() method : Forum id is : " + id);
				console.log("-->ForumController",self.forum);
				ForumService.rejectForum(forum, id).then
				(
						function(d)
						{
						alert('Reject Forum?'),
						self.forum=d,
						self.listforums,
						$location.path('/ViewForums');
						},
						function(errResponse) 
						{
							console.error("Error while rejecting forum...")
						}
				);
			};
			
			
			self.fetchAllForumComments = function(id)
			{
				console.log("-->ForumController : calling fetchAllForumComments method with id : "+ id);
				ForumService.fetchAllForumComments(id).then
				(function(d) 
				{
					self.forumComments = d;
					self.getSelectedForum(id);		//calling getSelectedForum(id) method ...
					$location.path('/view_forum');
				},
				function(errResponse) 
				{
					console.error('Error while fetching ForumComments...');
				}
				);
			};
			
			self.createForumComment = function(forumComment, id) {
				console.log("-->ForumController : calling 'createForumComment' method.", self.forum);
				forumComment.forumId = id;
				console.log("-->ForumController ForumId :" +forumComment.forumId);
				ForumService.createForumComment(forumComment).then
							(function(d) 
							{
								console.log('Current User :',$rootScope.currentUser.userId)
								self.forumComment = d;
								console.log('-->ForumController :', self.forumComment)
								self.fetchAllForumComments(id);
								self.resetComment();
							},
							function(errResponse) {
								console.error('Error while creating forumComment...');
							}
							);
			};

	/*****************************************************************************/

			self.submit = function() 
			{
				{
					console.log("-->ForumController : calling 'submit()' method.", self.forum);
					self.createForum(self.forum);
					console.log('Saving new Forum', self.forum);
				}
				self.reset();
			};
			self.reset = function() 
			{
				console.log('submit a new Forum', self.forum);
				self.forum =
				{
						forumId : '',
						forumName : '',
						forumDescription : '',
						userId : '',
						forumCreationDate : '',
						forumStatus : '',
						userName:'',
						forumCommentCount:'',
					    errorCode : '',
					    errorMessage : ''
				};
				$scope.myForm.$setPristine(); // reset form...
			};
			
			self.resetComment = function() 
			{
				console.log('submit a new ForumComment', self.forumComment);
				self.forumComment = 
				{
						forumCommentId : '',
						forumId : '',
						forumComment : '',
						forumCommentDate : '',
						userId : '',
						userName:'',
					    errorCode : '',
					    errorMessage : ''
					};
				$scope.myForm.$setPristine(); // reset form...
			};
		} 
]
);