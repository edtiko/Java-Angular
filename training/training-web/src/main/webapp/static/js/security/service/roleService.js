'use strict';
 
trainingApp.factory('RoleService', ['$http', '$q', function($http, $q){
    return {

            getRoles: function(){
                    return $http.get($contextPath+'role/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting roles');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);