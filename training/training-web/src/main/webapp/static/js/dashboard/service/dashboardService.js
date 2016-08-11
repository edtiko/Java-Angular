'use strict';
 
trainingApp.factory('DashboardService', ['$http', '$q', function($http, $q){
    return {
            
            getDashboard: function(userProfile){
                
                    return $http.post($contextPath+'dashboard/get/by/id',userProfile)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error(userProfile);
                                        console.error('Error while getting dashboard');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
 
}]);