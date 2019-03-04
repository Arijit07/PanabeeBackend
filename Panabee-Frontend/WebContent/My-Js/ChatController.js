
  app.controller("ChatController", ['$location','$scope', 'ChatService','$rootScope',function($location,$scope, ChatService,$rootScope) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 150;
    
    
    
    $scope.addMessage = function() {
    	ChatService.send($scope.message,$rootScope.currentUser);
    	$scope.message = "";
    };
    
    ChatService.receive().then(null, null, function(message) {
    	$scope.messages.push(message);
    });
  }]);
