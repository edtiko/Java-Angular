'use strict';
trainingApp.service('BrandService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'brand/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getBrandById: function (id) {
                return $http.get($contextPath + '/get/brand/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching brands');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createBrand: function (brand) {
                return $http.post($contextPath + '/brand/create', brand)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating brand');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeBrand: function (brand) {
                return $http.post($contextPath + '/brand/update', brand)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating brand');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteBrand: function (brand) {
                return $http.post($contextPath + '/brand/delete',brand)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting brand');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);