'use strict';
app.controller('ImageController', ['ImageService','$scope', '$rootScope', function(ImageService ,$scope,  $rootScope){
	
            $scope.uploadFile = function(){
               var file = $scope.myFile;
               
               console.log('file is ' );
               console.dir(file);
               
               var uploadUrl = "http://localhost:9000/Panabee-Backend/ImageUpload/"+$rootScope.currentUser.userId;
               console.log(uploadUrl);
               ImageService.uploadFileToUrl(file, uploadUrl);
            };
            console.log('user is '+$rootScope.currentUser.userId);
         }]);