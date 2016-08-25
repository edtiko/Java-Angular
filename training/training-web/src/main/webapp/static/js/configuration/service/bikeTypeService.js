'use strict';
 
trainingApp.factory('BikeTypeService', ['$http', '$q', function($http, $q){
    return {

            getBikeTypes: function(){
                    return $http.get($contextPath+'bikeType/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting bike types');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);