'use strict';
 
trainingApp.factory('UserProfileService', ['$http', '$q', function($http, $q){
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
             
            mergeProfile: function(userProfile){
                    return $http.post($contextPath+'userProfile/merge', userProfile)
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
             
            getProfile: function(userProfile){
                    return $http.post($contextPath+'userProfile/get/by/id',userProfile)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error(userProfile);
                                        console.error('Error while getting userProfile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
         
            generatePlan: function(userProfile){
                    return $http.post($contextPath+'trainingPlanWorkout/generate/planWorkout/for/user',userProfile)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error(userProfile);
                                        console.error('Error while getting plan');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
 
}]);