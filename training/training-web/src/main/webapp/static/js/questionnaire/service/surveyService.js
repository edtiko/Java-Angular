    'use strict';
    trainingApp.service('surveyService', ['$http','$q', function($http, $q) {
        return {
             getAllQuestionnaireQuestion: function(userId) {

              return  $http.post($contextPath + '/questionnaireQuestion/get/questionnaire/user/'+userId)
                    .then(
                    function (response) {
                       return response;
                    }
                        );

            },
            create: function (questionnaireQuestionList) {
                      
                return $http.post($contextPath + '/questionnaireQuestion/create/', questionnaireQuestionList)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while saving survey');
                                            return $q.reject(errResponse);
                                        }
                                );
                    }
        };
    }]);