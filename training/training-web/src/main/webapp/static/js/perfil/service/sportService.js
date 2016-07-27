'use strict';
 
trainingApp.factory('SportService', ['$http', '$q', function($http, $q){
    return {

            getSports: function(){
                    return $http.get($contextPath+'sport/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting sports');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);