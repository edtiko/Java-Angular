trainingApp.controller('OptionController', ['$scope', 'OptionService', '$window',function ($scope, OptionService,
            $window) {
        var self = this;
        $scope.option = {optionId: null, name: '', url: '', description: '', masterOptionId: '', stateId: ''};
        $scope.options = [];
        $scope.count = 0;
        $scope.masterOptions = [];
        
        $scope.selected = [];

        $scope.query = {
          order: 'name',
          limit: 5,
          page: 1
        };

        function success(response) {
            if(response.data.status == 'fail') {
                $scope.showMessage(response.data.output);
            } else {
                return response.data.output;
            }
            
            return null;
        }

        $scope.getOptions = function () {
          $scope.promise = OptionService.getPaginate($scope.query, function(response) {
            $scope.options = success(response);
            
            if($scope.options.length > 0) {
                $scope.count = $scope.options[0].count;
            }
        }).$promise;
        };
        
        $scope.getMasterOptions = function () {
          OptionService.getMasterOptions(function(response) {
            $scope.masterOptions = success(response);
        });
        };
        $scope.getMasterOptions();
        $scope.getOptions();
    }]);