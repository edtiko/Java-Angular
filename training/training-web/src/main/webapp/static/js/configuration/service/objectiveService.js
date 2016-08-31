'use strict';
 
trainingApp.factory('ObjectiveService', ['$http', '$q', function($http, $q){
    return {

            getObjectives: function(){
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
            },
            getObjectivesByDiscipline: function(disciplineId){
                    return $http.get($contextPath+'objective/get/by/discipline/'+disciplineId)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting objectives by discipline');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);