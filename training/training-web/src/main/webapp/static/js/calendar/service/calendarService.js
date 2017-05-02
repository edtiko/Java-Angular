'use strict';
trainingApp.service('CalendarService', ['$http', '$q', function ($http, $q) {
        return {
            getActivityByDisciplineUser: function (userId) {
                return $http.get($contextPath + '/activity/by/discipline/user/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching users');
                                    return $q.reject(errResponse);
                                }
                        );
            },
           createManualActivity: function (manualActivity) {
                return $http.post($contextPath + 'create/manual/activity', manualActivity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating manual activity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
           getManualActivityList: function (userId) {
                return $http.get($contextPath + 'get/manual/activity/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching manual activities');
                                    return $q.reject(errResponse);
                                }
                        );
            },
           deleteManualActivity: function(manualActivityId){
              return $http.get($contextPath + 'delete/manual/activity/' + manualActivityId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting manual activity');
                                    return $q.reject(errResponse);
                                }
                        ); 
           },
           getManualActivity: function(manualActivityId){
              return $http.get($contextPath + 'get/manual/activity/id/' + manualActivityId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get manual activity');
                                    return $q.reject(errResponse);
                                }
                        );  
           },
           getActivity: function(trainingPlanWorkoutId){
              return $http.get($contextPath + 'get/activity/id/' + trainingPlanWorkoutId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get activity');
                                    return $q.reject(errResponse);
                                }
                        );  
           },
           
           getActivityPpm: function(trainingPlanWorkoutId, ppm){
              return $http.get($contextPath + 'get/activity/id/' + trainingPlanWorkoutId + '/'+ppm)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get activity');
                                    return $q.reject(errResponse);
                                }
                        );  
           }, 
           getSerie: function(sesion, week, userId){
              return $http.get($contextPath + 'trainingPlanWorkout/get/serie/'+sesion+'/'+week+'/'+userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get activity');
                                    return $q.reject(errResponse);
                                }
                        );  
           },
           
           getActivityReplace: function(trainingPlanWorkoutId){
              return $http.get($contextPath + 'get/activity/replace/id/' + trainingPlanWorkoutId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get activity');
                                    return $q.reject(errResponse);
                                }
                        );  
           },
           
           updateWorkout: function(workout){
                     return $http.post($contextPath + 'trainingPlanWorkout/update/workout', workout)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while update activity');
                                    return $q.reject(errResponse);
                                }
                        );
           }
        };
    }]);