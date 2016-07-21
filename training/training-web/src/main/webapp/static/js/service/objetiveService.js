'use strict';
 
trainingApp.factory('ObjetiveService', ['$http', '$q', function($http, $q){
var $contextPath = "http://localhost:8085/training-web/";
    return {

            getObjetives: function(){
                    return $http.get($contextPath+'objetive/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting objetives');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);