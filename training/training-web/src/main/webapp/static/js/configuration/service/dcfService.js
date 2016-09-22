'use strict';
trainingApp.service('DcfService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'dcf/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getDcfByModalityIdAndObjectiveId: function (modalityId, objectiveId) {
                return $http.get($contextPath + '/dcf/by/modality/objective/' + modalityId+'/'+objectiveId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching dcfs');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getDcfById: function (id) {
                return $http.get($contextPath + '/get/dcf/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching dcfs');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createDcf: function (dcf) {
                return $http.post($contextPath + '/dcf/create', dcf)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating dcf');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeDcf: function (dcf) {
                return $http.post($contextPath + '/dcf/update', dcf)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating dcf');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteDcf: function (dcf) {
                return $http.post($contextPath + '/dcf/delete',dcf)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting dcf');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);