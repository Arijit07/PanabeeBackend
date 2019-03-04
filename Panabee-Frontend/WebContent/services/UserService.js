'use strict'
app.factory
('UserService', 
	['$http', '$q', '$rootScope',
	function($http, $q, $rootScope) 
	{
		console.log('UserService...');
		var BASE_URL = 'http://localhost:9000/Panabee-Backend'
		return {
			
			fetchAllUsers : function() {
				console.log("--> UserService : calling 'fetchAllUsers' method.");
				return $http.get(BASE_URL+'/users').then
				(function(response) 
						{
								return response.data;
						},
						function(errResponse) 
						{
								console.error('Error while fetching UserDetails...');
								return $q.reject(errResponse);
						}
				);
			},
			
			getSelectedUser : function(id) 
			{
				console.log("-->UserService : calling getSelectedUser() method with id : " + id);
				return $http.get(BASE_URL+'/user/'+ id).then
				(function(response) 
						{
								$rootScope.selectedUser = response.data;
								return response.data;
						},
						function(errResponse) 
						{
								console.error('Error while Fetching User.');
								return $q.reject(errResponse);
						}
				);
			},
			
			sendFriendRequest : function(friendId) {
				alert(friendId)
				return $http
						.post(
								BASE_URL + '/addFriend/'
										+ friendId)
						.then(
								function(response) {
									return response.data;
								},
								function(errResponse) {
									console
											.error("-->updateFriendRequest : Error while creating friend.")
									return $q
											.reject(errResponse);
								});
			},
			
			searchForFriends : function() 
			{
				console.log("--> UserService : calling 'fetchAllUsers' method.");
				return $http.get(BASE_URL+'/searchForFriends').then
				(function(response) 
						{
								return response.data;
						},
						function(errResponse)
						{
								console.error('Error while fetching UserDetails...');
								return $q.reject(errResponse);
						}
				);
			},

			createUser : function(user) 
			{
				console.log("-->JobService : calling 'createUser' method.");
				return $http.post(BASE_URL+'/user/', user).then
				(function(response) 
						{
								return response.data;
						},
						function(errResponse) 
						{
								console.error('Error while creating new user...');
								return $q.reject(errResponse);
						}
				);
			},

			updateUser : function(user, id) {
				console.log("--> UserService : calling 'updateUser' method.");
				return $http
							.put(BASE_URL+'/user/'+id, user)
							.then(function(response) {
								return response.data;
							},
							function(errResponse) {
								console.error('Error while updating User...');
								return $q.reject(errResponse);
							});
			},

			authenticate : function(user) 
			{
				console.log("--> UserService : calling 'authenticate' method.");
				return $http.post(BASE_URL+'/user/authenticate/', user).then
				(function(response) 
						{
								if (response.data.errorMessage!="") 
								{
									$rootScope.currentUser = 
									{
											userId: response.data.userId,
											userPassword: response.data.userPassword,
											userName: response.data.userName,
											userRole: response.data.userRole,
											userGender: response.data.userGender,
											userDob: response.data.userDob,
											userEmail: response.data.userEmail,
											userContactNumber: response.data.userContactNumber,
											userAddress: response.data.userAddress,
											userStatus: response.data.userStatus,
											userIsOnline: response.data.userIsOnline,
											userImage: response.data.userImage,
											userDescription: response.data.userDescription
									};
								}
								return response.data;
						},
							function(errResponse) 
							{
								console.error('Error while authenticate User...');
								return $q.reject(errResponse);
							}
				);
			},
			
			activateUser : function(user, id) 
			{
				console.log("-->UserService : calling activateUser() method : getting user with id : " + id);
				return $http.put(BASE_URL+'/activateUser/'+ id, user).then
							(function(response)
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while activating User");
								return $q.reject(errResponse);
							}
						    );
			},
			
			deactivateUser : function(user, id) 
			{
				console.log("-->UserService : calling deactivateUser() method : getting user with id : " + id);
				return $http.put(BASE_URL+'/deactivateUser/'+ id, user).then
							(function(response)
							{
								return response.data;
							},
							function(errResponse)
							{
								console.log("Error while deactivating User");
								return $q.reject(errResponse);
							}
						    );
			},
			
			
			
			
			getNotificationsNotViewed : function(){
				return $http.get(BASE_URL + "/getnotificationsnotviewed");
			},
			
			getNotification :function(notificationId){
				return $http.get(BASE_URL + "/getnotification/"+notificationId);
			},
			
			updateNotification :function(notificationId){
				return $http.put(BASE_URL + "/updatenotification/"+notificationId);
			},
			
			
			logout: function(user, id) 
			{
				console.log("--> UserService : calling 'logout' method.");
				alert(user.userId+id);
				return $http.put(BASE_URL+'/user/logout/'+id,user).then
				(function(response) 
						{
								return response.data;
						},
						function(errResponse) 
						{
								console.error('Error while logging out.');
								return $q.reject(errResponse);
						}
				);
			}
		};

	}
	]
);