'use strict'
app.service('ImageService', ['$http', function ($http) {
            this.uploadFileToUrl = function(file, uploadUrl){
            	console.log(uploadUrl);
            	console.log(file);
               var fd = new FormData();
               fd.append('file', file);
               $http.post(uploadUrl, fd, {
                  transformRequest: angular.identity,
                  headers: {'Content-Type': undefined}
               
               })
            
               .success(function(){
            	   console.log('success');
            	   
               })
            
               .error(function(){
            	   console.log('error while uploading');
               });
               //return response.data;
            }
         }]);