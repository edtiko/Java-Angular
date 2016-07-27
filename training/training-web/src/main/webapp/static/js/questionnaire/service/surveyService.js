    'use strict';
    trainingApp.service('surveyService', ['$http','$q', function($http, $q) {
        return {
             getAllQuestionnaireQuestion: function(paginator) {

              return  $http.post('http://localhost:8080/training/questionnaireQuestion/get/all/',paginator)
                    .then(
                    function (response) {
                       return response;
                    }
                        );

            }
        };
    }]);