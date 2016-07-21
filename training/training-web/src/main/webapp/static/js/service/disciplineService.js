'use strict';
 
trainingApp.factory('DisciplineService', ['$http', '$q', function($http, $q){
var $contextPath = "http://localhost:8085/training-web/";
    return {

            getSportDisciplines: function(){
                    return $http.get($contextPath+'sportDiscipline/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting disciplines');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);