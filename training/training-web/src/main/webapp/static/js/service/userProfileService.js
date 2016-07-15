'use strict';
 
trainingApp.factory('UserProfileService', ['$http', '$q', function($http, $q){
var $contextPath = "http://localhost:8085/training-web/";
    return {
            
            createProfile: function(userProfile) {
                    return $http.post($contextPath+'userProfile/create',userProfile)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            mergeProfile: function(userProfile, id){
                    return $http.post($contextPath+'userProfile/merge/'+id, userProfile)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while merging userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            getProfile: function(userProfile){
                    return $http.post($contextPath+'userProfile/get/by/id/'+userProfile)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error(userProfile);
                                        console.error('Error while getting userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
            
         

         
        };
 
}]);