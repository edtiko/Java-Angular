'use strict';
 
trainingApp.factory('ModalityService', ['$http', '$q', function($http, $q){
var $contextPath = "http://localhost:8085/training-web/";
    return {
            
            getAll: function(id){
                    return $http.get($contextPath+'modality/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting modalities');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getModalitiesByDisciplineId: function(id){
                    return $http.get($contextPath+'modality/get/by/disciplineId/'+id)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting modalities');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);