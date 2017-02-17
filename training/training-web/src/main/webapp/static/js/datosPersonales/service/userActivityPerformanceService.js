'use strict';
trainingApp.service('UserActivityPerformanceService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'userActivityPerformance/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getByRangeDateAndUser: function (userId, fromDate, toDate) {
                return $http.get($contextPath + '/userActivityPerformance/get/by/userId/rangeDate/' + userId+'/'+fromDate + '/' +toDate)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching by range date and user');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getActivitiesByWeekAndUser: function (userId, fromDate, toDate) {
                return $http.get($contextPath + '/userActivityPerformance/get/activities/by/userId/rangeDate/' + userId+'/'+fromDate + '/' +toDate)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching  activities by user');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getByRangeDateAndUserAndVariable: function (userId, fromDate, toDate,variable) {
                return $http.get($contextPath + '/userActivityPerformance/get/by/userId/rangeDate/metafield/' + userId+'/'+fromDate + '/' +toDate+'/'+variable)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching by range date and user');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getByRangeDateAndUserAndVariableAndDaysWeekly: function (userId, fromDate, toDate,variable,days,weekly) {
                return $http.get($contextPath + '/userActivityPerformance/get/by/userId/rangeDate/metafield/days/weekly/' 
                        + userId+'/'+fromDate + '/' +toDate+'/'+variable+'/'+days+'/'+weekly)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching by range date and user');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getUserActivityPerformanceById: function (id) {
                return $http.get($contextPath + '/get/userActivityPerformance/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching userActivityPerformances');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createUserActivityPerformance: function (userActivityPerformance) {
                return $http.post($contextPath + '/userActivityPerformance/create', userActivityPerformance)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating userActivityPerformance');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeUserActivityPerformance: function (userActivityPerformance) {
                return $http.post($contextPath + '/userActivityPerformance/update', userActivityPerformance)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating userActivityPerformance');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteUserActivityPerformance: function (userActivityPerformance) {
                return $http.post($contextPath + '/userActivityPerformance/delete',userActivityPerformance)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting userActivityPerformance');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getProgressReport: function(date, activity,userId){
                 return $http.get($contextPath + '/get/userActivityPerformance/progress/'+date+'/'+activity+'/'+userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching userActivityPerformances');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);