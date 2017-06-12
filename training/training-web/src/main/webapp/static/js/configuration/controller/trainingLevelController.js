trainingApp.controller('TrainingLevelController', ['$scope', 'ObjectiveService', 'ModalityService', 'DisciplineService', '$mdDialog',
    function ($scope, ObjectiveService, ModalityService, DisciplineService, $mdDialog) {

        var self = this;
        self.rowData = [];
        var columnDefs = [
            {headerName: "Nivel", field: "name", width: 150, filter: 'text'},
            {headerName: "Modalidad", field: "modality", width: 150, filter: 'text'},
            {headerName: "Sesiones Minimas", field: "minSesion", width: 150, filter: 'number'},
            {headerName: "Sesiones Maximas", field: "maxSesion", width: 150, filter: 'number'},
            {headerName: "Horas Semanales Min.", field: "minHourWeek", width: 175, filter: 'number'},
            {headerName: "Horas Semanales Max.", field: "maxHourWeek", width: 177, filter: 'number'},
            {headerName: "Semanas Minimas", field: "minWeekPlan", width: 150, filter: 'number'},
            {headerName: "Semanas Maximas", field: "maxWeekPlan", width: 150, filter: 'number'}
        ];

        /* var rowData = [
         {make: "Toyota", model: "Celica", price: 35000},
         {make: "Ford", model: "Mondeo", price: 32000},
         {make: "Porsche", model: "Boxter", price: 72000}
         ];*/

        $scope.gridOptions = {
            columnDefs: columnDefs,
            rowData: null,
            pagination: true,
            paginationPageSize: 10,
            enableColResize: true,
            enableSorting: true,
            enableFilter: true,
            //floatingFilter : true,
            domLayout: 'autoHeight'
                    /* defaultColDef: { 
                     editable: true,
                     enableRowGroup: true,
                     enablePivot: true,
                     enableValue: true
                     }*/

        };

        self.filters = {
            name: ['Principiante', 'Intermedio', 'Avanzado', 'Elite']
        };

        $scope.getTrainingLevel = function () {
            ObjectiveService.getObjectives().then(
                    function (data) {
                        self.rowData = data;

                        $scope.gridOptions.api.setRowData(data);

                    },
                    function (error) {
                        console.error(error);
                    }
            );
        };


        $scope.showCreateLevel = function (ev) {

            $mdDialog.show({
                controller: ConfigurationPlanController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-training-level.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.

            });
        };
        function ConfigurationPlanController($scope, $mdDialog) {

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplines = d;
                    },
                    function (error) {
                        console.error(error);
                    }
            );

            ObjectiveService.getLevelTypes().then(
                    function (data) {
                        $scope.levelTypes = data;

                    },
                    function (error) {
                        console.error(error);
                    }
            );

            $scope.getModalitiesByDisciplineId = function (id, change) {
                if (change) {
                    $scope.trainingLevel.modalityId = '';
                }
                ModalityService.getModalitiesByDisciplineId(id).then(
                        function (d) {
                            $scope.modalities = d;
                        },
                        function (error) {
                            console.error(error);
                        }
                );
            };

            $scope.saveTrainingLevel = function (trainingLevel) {
                ObjectiveService.createTrainingLevel(trainingLevel).then(
                        function (data) {
                             $scope.showMessage("Nivel Registrado exitosamente.");
                             $scope.getTrainingLevel();
                             $mdDialog.hide();
                        },
                        function (error) {
                            console.log(error);
                        }
                );
            };


        }

        $scope.getTrainingLevel();

    }]);