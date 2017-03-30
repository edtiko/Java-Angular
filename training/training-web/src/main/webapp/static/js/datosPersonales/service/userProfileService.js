'use strict';
 
trainingApp.factory('UserProfileService', ['$http', '$q', function($http, $q){
    return {
            
            createProfile: function(userProfile) {
                    return $http.post($contextPath+'userProfile/create',userProfile)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            mergeProfile: function(userProfile){
                var date = userProfile.endDate.split("/");
                var dateFormat = new Date(date[2], date[1] - 1, date[0]);
                var dto = userProfile;
                dto.endDate = ("0" + (dateFormat.getDate() + 1)).slice(-2) + "/" + ("0" + (dateFormat.getMonth() + 1)).slice(-2) + "/" + dateFormat.getFullYear();
                    return $http.post($contextPath+'userProfile/merge', dto)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while merging userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            getProfile: function(userId){
                
                    return $http.get($contextPath+'userProfile/get/by/'+userId)
                            .then(
                                    function(response){
                                        return response.data.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
         
            generatePlan: function(userProfile){
                    return $http.post($contextPath+'trainingPlanWorkout/generate/planWorkout/for/user',userProfile)
                            .then(
                                    function(response){
                                        return response;
                                    }, 
                                    function(errResponse){
                                        console.error(userProfile);
                                        console.error('Error while getting plan');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            validatePlan: function(userId){
                    return $http.get($contextPath+'trainingPlanWorkout/validate/planWorkout/'+userId)
                            .then(
                                    function(response){
                                        return response.data.output;
                                    }, 
                                    function(errResponse){
                                        console.error(userId);
                                        console.error('Error while validate plan');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
 
}]);