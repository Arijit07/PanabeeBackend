'use strict';
app.controller('UserController', [
		'$http',
		'$cookieStore',
		'$scope',
		'UserService',
		'$location',
		'$rootScope',
		'$window',
		'$localStorage',
		'$routeParams',
		function($http,$cookieStore, $scope, UserService, $location, $rootScope, $window, $localStorage, $routeParams )  {
			console.log("UserController...")

		var self = this;
		self.user = {
		    errorCode : '',
			errorMessage : '',
			userId : '',
			userPassword : '',
			userName : '',
			userRole : '',
			userGender : '',
			userDob : '',
			userEmail : '',
			userContactNumber : '',
			userAddress : '',
			userStatus : '',
			userIsOnline : '',
			userImage : '',
			userDescription : ''
		}
		
								/*  To fetch all user        */
		
		self.users = [];
		self.fetchAllUsers = function() 
				{
				console.log("--> UserController : calling fetchAllUsers method.");
				UserService.fetchAllUsers().then
				(function(d) 
					{
					self.users = d;
					}, 
					function(errResponse)
					{
					console.error('Error while fetching Users...');
					}
				);
			};
			
			
			

								/*  To fetch user by userid       */		
			
			self.getSelectedUser = function(id) {
				console.log("-->UserController : calling getSelectedUser method : getting user with id : " + id);
				UserService.getSelectedUser(id).then(
						function(d) {
						self.user = d;
						console.log('id '+ self.user.userId);
						$rootScope.user= self.user;
						console.log(' r id '+ $rootScope.user.userId);
						$location.path('/ViewUsersById');
							}, 
						function(errResponse) {
						console.error('Error while fetching User...');
							});
								};
								
								/*  To create new user      */		
								
			self.createUser = function(user) {
			console.log("--> UserController : calling createUser method.");
			UserService.createUser(user).then(
			function(d) {
			self.user = d;
			alert('User Registered Successfully...please log in ...')
			},
			function(errResponse) {
			console.error('Error while creating user...');
			});
			};
							     /*  To update user details     */	
			                  
			self.updateUser = function(user,id) {
				console.log("-->UserController : calling updateUser method.");
				UserService.updateUser(user,id).then(
		         function(d) {
						self.user = d;
						alert('User updated Successfully...')
						console.log(self.user);
						},
				function(errResponse) {
				console.error('Error while updating user...')
					});
				};
			                     /*  To authenticate user details     */				
					
				self.authenticate = function(user)
			{
				console.log("--> UserController : calling authenticate method.");
				UserService.authenticate(user).then
				(function(d)
				{
					self.user = d;
					$rootScope.currentUser = self.user;
					console.log (self.user.userId);
					console.log ($rootScope.currentUser);
					if(self.user.errorCode != null)
					{
						alert("Invalid Credentials. Please try again.")
						
						self.user.userId = "";
						self.user.userPassword = "";
					} 
					else
					{
						$rootScope.currentUser = self.user;
						console.log("Valid Credentials. Navigating to home page.");
						console.log("user"+$rootScope.currentUser.userId);
						$http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser;
						$cookieStore.put('currentUser', $rootScope.currentUser);
						
						//$localStorage.currentUser=$rootScope.currentUser;
						console.log($cookieStore);
						getNotificationsNotViewed();
						$location.path('/index');
						
					}
				}, 
				function(errResponse) 
				{
					console.error('Error while authenticate User...');
				}
				);
			};
				

				
                                  /*  To activate User     */
						
						
			self.activateUser = function(user, id) 
			{
				console.log("-->UserController : calling activateUser() method : User id is : " + id);
				console.log("-->UserController",self.user);
				UserService.activateUser(user, id).then
				(
						function(d)
						{
						alert('Activate User?');
						self.user=d;
						$location.path('/ViewUsersById');
						},
						function(errResponse) 
						{
							console.error("Error while activating user...")
						}
				);
			};
			
							/*  To de-activate User     */
							
			self.deactivateUser = function(user, id) 
			{
				console.log("-->UserController : calling deactivateUser() method : User id is : " + id);
				console.log("-->UserController",self.user);
				UserService.deactivateUser(user, id).then
				(
						function(d)
						{
						alert('Deactivate User?');
						self.user=d;
						$location.path('/ViewUsersById');
						},
						function(errResponse) 
						{
							console.error("Error while deactivating user...")
						}
				);
			};
			
						/*  To Logout User     */
						
						
			self.logout = function(user,id) 
			{
				console.log("--> UserController : calling logout method.");
				alert(user.userId+id);
				UserService.logout(user,id);
				$rootScope.currentUser = {};
				//$localStorage.currentUser.remove('currentUser');
			    $cookieStore.remove('currentUser');
				
				console.log("-->UserController : User Logged out.");
				$window.location.reload();
				$location.path('/index');
			}
			
			self.fetchAllUsers();
			
			
			self.login = function() 
			{
				{
					console.log('login validation ??????????', self.user);
					self.authenticate(self.user);
				}
			};
			
						/*  To Register new User     */

			self.register = function() 
			{
				{
					console.log("--> UserController : calling register() method.", self.user);
					self.createUser(self.user);
					console.log('Saving new user...');
				}
				$location.path('/login');
				
			};
			
			self.sendFriendRequest = function sendFriendRequest(friendId) {
				console.log("--> sendFriendRequest : "+friendId);
				UserService.sendFriendRequest(friendId).then(
				function(d) {
				self.friend = d;
				alert('Friend request sent...')
				},
				function(errResponse) {
				console.error('Error while friends...');
				});
				
						
					
			};
			
						/*  To clear form     */
						
			self.reset = function()
			{
				self.user = 
				{
						errorCode: '',
						errorMessage: '',
						userId: '',
						userPassword: '',
						userName: '',
						userRole: '',
						userGender: '',
						userDob: '',
						userEmail: '',
						userContactNumber: '',
						userAddress: '',
						userStatus: '',
						userIsOnline: '',
						userImage: '',
						userDescription:''
				};
				$scope.myForm.$setPristine();
			};
			
		///////////////////////////////
			
			
			function getNotificationsNotViewed(){
				UserService.getNotificationsNotViewed().then(
						function(response){//in index.html , response.data (Array of notifications)
							$rootScope.notifications=response.data
							$rootScope.notificationCount=$rootScope.notifications.length
							console.log("--> notificationCount "+$rootScope.notificationCount);
						},
						function(response){
							if(response.status==401)
								$location.path('/login')
						})
				}
				//function call -STATEMENT to get Array of notification not viewed by the logged in user
				console.log("1111--"+$routeParams.notificationId)
				
			    if($routeParams.notificationId!=undefined){
			    	
			    	
			    	UserService.getNotification($routeParams.notificationId).then(
			    			function(response){
			    				$scope.notification=response.data//notificationdetails.html
			    				
			    			},
			    			function(response){
			    				if(response.status==401)
			    					$location.path('/login')
			    			})
			    			
			    	//Update notification set viewed=true where notificationId=?
			    	UserService.updateNotification($routeParams.notificationId).then(
			    			function(response){
			    				
			    				getNotificationsNotViewed()
			    				
			    			},
			    			function(response){
			    				if(response.status==401)
			    					$location.path('/login')
			    			})
			    }
			
			
			/////////////////////////////////
			
			
			

		} ]);