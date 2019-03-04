'use strict';

app.controller('FriendController', [ 'FriendService', 'UserService', '$scope',
		'$location', '$rootScope',
		function(FriendService, UserService, $scope, $location, $rootScope) {
			console.log("FriendController...");

			var self = this;
			self.friend = {
				id : '',
				userId : '',
				friendId : '',
				status : '' ,
				isOnline : ''
				
			};
			self.friends = [];
			self.newFriendRequests = [];
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
				};
				
			self.users = [];
			
		
			
			self.sendFriendRequest = function sendFriendRequest(friendId) {
				console.log("--> sendFriendRequest : "+friendId);
				FriendService.sendFriendRequest(friendId).then(
				function(d) {
				self.friend = d;
				alert('Friend request sent...')
				},
				function(errResponse) {
				console.error('Error while friends...');
				});
				
						
					
			};
			
			
			self.getSelectedUser = function(id) {
				console.log("-->UserController : calling getSelectedUser method : getting User with id : " + id);
				UserService.getSelectedUser(id).then(
						function(d) {
						self.user = d;
						console.log('id '+ self.user.userId);
						$rootScope.user= self.user;
						console.log(' r id '+ $rootScope.user.userId);
						$location.path('/viewUserById');
							}, 
						function(errResponse) {
						console.error('Error while fetching User...');
							});
								};
			
			
			self.rejectFriend = function(friend, id) {
				console.log("--> FriendController : calling 'rejectFriend' method with id : "+id);
				console.log("--> FriendController",self.friend);
				FriendService
								.rejectFriend(friend, id)
								.then(function(d) {
									self.friend = d;
									alert('Friend request rejected successfully...');
									self.getNewFriendRequests();
								},
								function(errResponse) {
									console.error("Error while updating friend.");
								});
			};
			
			self.getSelectedFriend = function(id) {
				console.log("-->FriendController : calling getSelectedFriend method : getting friend with id : " + id);
				FriendService.getSelectedFriend(id).then(
						function(d) {
						self.friend = d;
						self.user = d;
						console.log('id '+ self.friend.friendId);
						$rootScope.friend= self.friend;
						$rootScope.user= self.user;
						console.log(' r id '+ $rootScope.friend.friendId);
						$location.path('/viewFriendProfile');
							}, 
						function(errResponse) {
						console.error('Error while fetching Friend...');
							});
								};
								
			
			
			self.getMyFriends = function() {
				console.log("--> getMyFriends");
				var currentUser = $rootScope.currentUser
				if (typeof currentUser == 'undefined') {
					alert("Please Sign in to check Friend List...")
					console.log('User not logged in , to check Friend List...');
					$location.path('/login');
				};
				FriendService.getMyFriends().then(
						function(d) {
							self.friends = d;
							console.log("Got the Friendlist.");
						},
						function(errResponse) {
							console.error("Error while fetching Friends.");
						}
					);
			};
			self.getNewFriendRequests = function() {
				console.log("--> getMyFriendRequests");
				var currentUser = $rootScope.currentUser
				if (typeof currentUser == 'undefined') {
					alert("Please Sign in to check Friend List...")
					console.log('User not logged in , to check Friend List...');
					$location.path('/login');
				};
				FriendService.getNewFriendRequests().then(
						function(d) {
							self.newFriendRequests = d;
							$rootScope.newFriendRequests=self.newFriendRequests;
							console.log("Got the Friendlist.");
						},
						function(errResponse) {
							console.error("Error while fetching Friends.");
						}
					);
			};
			
			self.updateFriendRequest = function(friend, id) {
				console.log("--> updateFriendRequest");
				FriendService.updateFriendRequest(friend.id).then(self.fetchAllFriends,
						function(errResponse) {
							console.error("Error while updating friend.");
						}
					);
			};
			
			
			self.acceptFriend = function(f, id) {
				console.log("--> FriendController : calling nnnn  'acceptFriend' method with id : "+id);
				console.log("--> FriendController",f);
				FriendService
								.acceptFriend(f, id)
								.then(function(d) {
									self.friend = d;
									self.getMyFriends();
									alert('Friend request accepted successfully... nnnn');
									$location.path('/index');
									
								},
								function(errResponse) {
									console.error("Error while updating friend.");
								});
			};
			
			self.unFriend = function(friend, id) {
				console.log("--> FriendController : calling 'unFriend' method with id : "+id);
				console.log("--> FriendController",self.friend);
				FriendService
								.unFriend(friend, id)
								.then(function(d) {
									self.friend = d;
									self.getMyFriends();
									$location.path('/index');
								},
								function(errResponse) {
									console.error("Error while updating friend.");
								});
			};			
			self.fetchAllUsers = function(d) {
				console.log("--> fetchAllUsers");
				UserService.fetchAllUsers().then(
						function() {
							self.users = d;
						},
						function(errResponse) {
							console.error("Error while fetching users.");
						}
					);
			};
			
			
			self.fetchAllUsers();
			self.getMyFriends();
			self.getNewFriendRequests();
			
			
		} ]);