trainingApp.controller('SurveyController', ['$scope', 'surveyService', function ($scope, surveyService)  {

    var self = this;
        $scope.maxRow = 5;
        $scope.survey = [];
        $scope.questionnaireResponseList = {};
        self.questionnaireResponse = {
            questionnaireResponseId: '',
            response: '',
            questionOptionId: '',
            userId : '',
            checked: true,
            questionnaireQuestionId:'',
            reponseTypeId: '1'
            
        };
        $scope.showSuccessAlert = false;
        $scope.successTextAlert = "";
        
        $scope.switchBool = function (value) {
            $scope[value] = !$scope[value];
        };
        
        $scope.setValue = function ($index) {
            var response = $scope.survey[$index].questionnaireResponseList[0];
            if (response != null && response.response !== 'undefined' || response.questionOptionId !== "") {
                response.userId = 2; //UserId Quemado- Cambiar con el usuario logueado
                response.questionnaireQuestionId = $scope.survey[$index].questionnaireQuestionId;
                response.reponseTypeId = 1;
            } else {
                response = angular.copy(self.questionnaireResponse);
                response.userId = 2; //UserId Quemado- Cambiar con el usuario logueado
                response.questionnaireQuestionId = $scope.survey[$index].questionnaireQuestionId;
                response.reponseTypeId = 1;
                $scope.survey[$index].questionnaireResponseList.push(response);
            }

        };

        $scope.setResponse = function ($parentIndex, $index, value) {
            var response = $scope.survey[$parentIndex].questionnaireResponseList;
            var idx = $scope.getIdx($parentIndex, value);
            if (idx !== "" && response[idx].checked) {
                response[idx].checked = false;
                response.splice(idx,1);
            } else if (idx !== "" && !response[idx].checked) {
                response[idx].checked = true;
            } else if (idx !== "") {
                response.questionOptionId = value;
            } else {
                response = angular.copy(self.questionnaireResponse);
                response.userId = 2; //UserId Quemado- Cambiar con el usuario logueado
                response.questionnaireQuestionId= $scope.survey[$parentIndex].questionnaireQuestionId;
                response.questionOptionId = value;
                response.reponseTypeId = 1;
                $scope.survey[$parentIndex].questionnaireResponseList.push(response);
            }
        };

        $scope.getIdx = function ($parentIndex, value) {
            var response = $scope.survey[$parentIndex].questionnaireResponseList;
            var res = '';
            angular.forEach(response, function (v, key) {
                if (v.questionOptionId == value) {
                    res = key;
                }
            });

            return res;
        };
        
          $scope.getResponse= function ($parentIndex, value) {
            var response = $scope.survey[$parentIndex].questionnaireResponseList;
            var res = false;
            angular.forEach(response, function (v, key) {
                if (v.questionOptionId == value) {
                    res= true;
                }
            });

             return res;
        };
        
    self.userId = 2;

        self.getAllQuestionnaireQuestion = function (userId) {
            surveyService.getAllQuestionnaireQuestion(userId).then(
                    function (response) {
                        angular.forEach(response.data.entity.output, function (value, key) {
                            $scope.survey[key] = value;
                        });
                    console.log($scope.survey);
                    },
                    function (errResponse) {
                        console.error('Error while fetching Currencies');
                    }
            );
        };
        
        self.create = function (survey) {
            surveyService.create(survey)
                    .then(
                            function(message){
                                $scope.showSuccessAlert = true;
                                $scope.successTextAlert = "Respuestas registradas satisfactoriamente.";
                                self.getAllQuestionnaireQuestion(self.userId);
                            },
                            function (errResponse) {
                                //console.error('Error while creating Survey.');
                            }
                    );
        };

        
         self.reset = function () {
            $scope.survey = {};
            $scope.myForm.$setPristine(); //reset Form
        };
        
         self.submit = function () {
            self.create($scope.survey);
            //console.log($scope.survey);
            //self.reset();
        };
    self.getAllQuestionnaireQuestion(self.userId);    
}]);
