'use strict';
trainingApp.service('ModalityService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'modality/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getModalityById: function (id) {
                return $http.get($contextPath + '/get/modality/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching modalitys');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createModality: function (modality) {
                return $http.post($contextPath + '/modality/create', modality)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating modality');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeModality: function (modality) {
                return $http.post($contextPath + '/modality/update', modality)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating modality');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteModality: function (modality) {
                return $http.post($contextPath + '/modality/delete',modality)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting modality');
                                    return $q.reject(errResponse);
                                }
                        );
            },
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
            },
            getModalitiesByObjectiveId: function(id){
                    return $http.get($contextPath+'modality/get/by/objectiveId/'+id)
                            .then(
                                    function(response){
                                        return response.data.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting modalities');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             getModalitiesByDisciplineUserId: function(userId){
                    return $http.get($contextPath+'modality/get/by/userId/'+userId)
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
