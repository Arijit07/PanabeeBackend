var app = angular.module('myApp', ['ngRoute', 'ngCookies', 'ngStorage']);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
		.when('/ViewUsers', {
			templateUrl: 'User/ViewUsers.html',
			controller: 'UserController as m'
		})
		
		.when('/ViewUsersById', {
			templateUrl: 'User/ViewUsersById.html',
			controller: 'UserController as m'
		})
		
		.when('/getnotification/:notificationId',{
			controller:'NotificationController as m',
			templateUrl:'Notification/NotificationinDetail.html'
				})
		
		.when('/EditUsers', {
			templateUrl: 'User/EditUsers.html',
			controller: 'UserController as m'
		})
		
		.when('/RegisterUsers', {
			templateUrl: 'User/RegisterUsers.html',
			controller: 'UserController as m'
		})
		
		.when('/login', {
			templateUrl: 'User/login.html',
			controller: 'UserController as m'
		})
		
		.when('/ViewBlogs', {
			templateUrl: 'Blog/ViewBlogs.html',
			controller: 'BlogController as m'
		})
		
		.when('/ViewBlogsById', {
			templateUrl: 'Blog/ViewBlogsById.html',
			controller: 'BlogController as m'
		})
		
		.when('/AddBlogs', {
			templateUrl: 'Blog/AddBlogs.html',
			controller: 'BlogController as m'
		})
		
		.when('/EditBlogs', {
			templateUrl: 'Blog/EditBlogs.html',
			controller: 'BlogController as m'
		})
		
		
		.when('/ViewApprovedBlogs', {
			templateUrl: 'Blog/ViewApprovedBlogs.html',
			controller: 'BlogController as m'
		})
		
		.when('/ViewsBlogComments', {
			templateUrl: 'Blog/ViewsBlogComments.html',
			controller: 'BlogController as m'
		})
		
		.when('/ViewBlogCommentById', {
			templateUrl: 'Blog/ViewBlogCommentById.html',
			controller: 'BlogController as m'
		})
		
		.when('/EditBlogComments', {
			templateUrl: 'Blog/EditBlogComments.html',
			controller: 'BlogController as m'
		})
		
		.when('/ViewBlogCommentByBlogId', {
			templateUrl: 'Blog/ViewBlogCommentByBlogId.html',
			controller: 'BlogController as m'
		})
		
		.when('/ViewBlogCommentByUserId', {
			templateUrl: 'Blog/ViewBlogCommentByUserId.html',
			controller: 'BlogController as m'
		})
		.when('/ViewForums', {
			templateUrl: 'Forum/ViewForums.html',
			controller: 'ForumController as m'
		})
		
		.when('/ViewForumsById', {
			templateUrl: 'Forum/ViewForumsById.html',
			controller: 'ForumController as m'
		})
		
		.when('/ViewApprovedForum', {
			templateUrl: 'Forum/ViewApprovedForum.html',
			controller: 'ForumController as m'
		})
		.when('/AddForum', {
			templateUrl: 'Forum/AddForum.html',
			controller: 'ForumController as m'
		})
		.when('/EditForum', {
			templateUrl: 'ForumComment/EditForum.html',
			controller: 'ForumController as m'
		})
		.when('/EditForumComment', {
			templateUrl: 'ForumComment/EditForumComment.html',
			controller: 'ForumCommentController as m'
		})
		.when('/AddForumComment', {
			templateUrl: 'ForumComment/AddForumComment.html',
			controller: 'ForumCommentController as m'
		})
		.when('/ViewForumComment', {
			templateUrl: 'ForumComment/ViewForumComment.html',
			controller: 'ForumCommentController as m'
		})
		
		.when('/ViewForumCommentById', {
			templateUrl: 'ForumComment/ViewForumCommentById.html',
			controller: 'ForumCommentController as m'
		})
		
		.when('/PostJob', {
			templateUrl: 'Job/PostJob.html',
			controller: 'JobController as m'
		})
		
		
		.when('/list_jobs', {
			templateUrl: 'Job/list_jobs.html',
			controller: 'JobController as m'
		})
		
		.when('/view_job', {
			templateUrl: 'Job/view_job.html',
			controller: 'JobController as m'
		})
		
		.when('/list_alljobapplications', {
			templateUrl: 'Job/list_alljobapplications.html',
			controller: 'JobController as m'
		})
		
		.when('/list_alljobapplied', {
			templateUrl: 'Job/list_alljobapplied.html',
			controller: 'JobController as m'
		})
		
		.when('/viewFriend', {
			templateUrl: 'Friend/viewFriend.html',
			controller: 'FriendController as m'
		})
		.when('/viewFriendProfile', {
			templateUrl: 'Friend/viewFriendProfile.html',
			controller: 'FriendController as m'
		})
		.when('/friend', {
			templateUrl: 'Friend/friend.html',
			controller: 'FriendController as m'
		})
		.when('/FriendRequest', {
			templateUrl: 'Friend/FriendRequest.html',
			controller: 'FriendController as m'
		})
		
		
		.when('/chat', {
		templateUrl : 'Chat/chat.html',
		controller : 'ChatController as ctrl'
	})
	.when('/ImageUpload', {
		templateUrl : 'Image/ImageUpload.html',
		controller : 'ImageController as ctrl'
	})
	
	.when('/getnotification/:notificationId', {
			templateUrl: 'Notification/NotificationinDetail.html',
			controller: 'UserController as m'
		})
		
	
		
		.otherwise({
			redirectTo: '/index'
		});
}]);

app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);
