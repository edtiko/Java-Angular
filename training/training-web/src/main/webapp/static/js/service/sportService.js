'use strict';
 
trainingApp.factory('SportService', ['$http', '$q', function($http, $q){
var $contextPath = "http://localhost:8085/training-web/";
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