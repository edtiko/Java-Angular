trainingApp.controller('SurveyController', ['$scope', 'surveyService', function ($scope, surveyService)  {

    var self = this;
        $scope.maxRow = 5;
        $scope.survey = {};
    self.sePaginator = { initialRow: "1", maxRow: "10"};

        self.getAllQuestionnaireQuestion = function (paginator) {
            surveyService.getAllQuestionnaireQuestion(paginator).then(
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
    self.getAllQuestionnaireQuestion(self.sePaginator);    
}]);
