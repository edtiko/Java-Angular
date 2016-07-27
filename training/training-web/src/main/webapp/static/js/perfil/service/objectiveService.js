'use strict';
 
trainingApp.factory('ObjectiveService', ['$http', '$q', function($http, $q){
    return {

            getObjetives: function(){
                    return $http.get($contextPath+'objective/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting objectives');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);