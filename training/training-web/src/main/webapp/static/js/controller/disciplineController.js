'use strict';

trainingApp.controller('DisciplineController', ['$scope', 'DisciplineService', function ($scope, DisciplineService) {

        $scope.disciplines = [];
 
        this.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        this.disciplines = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching the sport disciplines');
                        console.error(errResponse);
                    }
            );
        };

    }]);
