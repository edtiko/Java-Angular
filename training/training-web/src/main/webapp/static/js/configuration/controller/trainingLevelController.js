trainingApp.controller('TrainingLevelController', ['$scope', 'ObjectiveService', 'ModalityService', 'DisciplineService', '$mdDialog',
    function ($scope, ObjectiveService, ModalityService, DisciplineService, $mdDialog) {

        var self = this;
        self.rowData = [];
        $scope.edit = true;
        var columnDefs = [
            {headerName: "Editar", field: "edit",suppressFilter: true, width: 100, cellRenderer: editCellRendererFunc},
            {headerName: "Nivel", field: "name", width: 150, filter: 'set'},
            {headerName: "Modalidad", field: "modality", width: 150, filter: 'set'},
            {headerName: "Sesiones Minimas", field: "minSesion", width: 150, editable: true, filter: 'number'},
            {headerName: "Sesiones Maximas", field: "maxSesion", width: 150, editable: true, filter: 'number'},
            {headerName: "Horas Semanales Min.", field: "minHourWeek", width: 175, editable: true, filter: 'number'},
            {headerName: "Horas Semanales Max.", field: "maxHourWeek", width: 177, editable: true, filter: 'number'},
            {headerName: "Semanas Minimas", field: "minWeekPlan", width: 150, editable: true, filter: 'number'},
            {headerName: "Semanas Maximas", field: "maxWeekPlan", width: 150, editable: true, filter: 'number'}
        ];

        $scope.onSelectionChanged = function () {
            var selectedRows = $scope.gridOptions.api.getSelectedRows();
            $scope.trainingLevel = selectedRows[0];
            $scope.edit = true;
        };
        
         function editCellRendererFunc(params){  
            return '<div align="center"><a href="javascript:void(0)" title="Editar" ng-click="showCreateLevel()"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></div>'; 
        };

        $scope.gridOptions = {
            columnDefs: columnDefs,
            rowData: null,
            pagination: true,
            paginationPageSize: 10,
            enableColResize: true,
            enableSorting: true,
            enableFilter: true, 
            domLayout: 'autoHeight',
            rowSelection: 'single',
            angularCompileRows: true,
            onSelectionChanged: $scope.onSelectionChanged,
            onCellValueChanged: function (event) {
                var data = event.data;
                self.editTrainingLevel(data);
                //console.log('onCellValueChanged: ' + event.colDef.field + ' = ' + event.newValue);
            },
            onRowValueChanged: function (event) {
                var data = event.data;
                console.log('onRowValueChanged: (' + data.id + ', ' + data.minSesion + ', ' + data.maxSesion + ')');
                self.editTrainingLevel(data);
            },
            onGridReady: function (event) {
                event.api.sizeColumnsToFit();
            }
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

        self.editTrainingLevel = function (trainingLevel) {
            ObjectiveService.mergeObjective(trainingLevel).then(
                    function (data) {
                        console.log("Editado exitosamente.");
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        $scope.getLevelTypes = function () {
            ObjectiveService.getLevelTypes().then(
                    function (data) {
                        $scope.levelTypes = data;

                    },
                    function (error) {
                        console.error(error);
                    }
            );
        };


        $scope.showCreateLevel = function (create) {
            if(create){
                $scope.trainingLevel = {};
            }
            $mdDialog.show({
                controller: ConfigurationPlanController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-training-level.html',
                parent: angular.element(document.body),
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

            //$scope.getLevelTypes();
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
            if ($scope.trainingLevel.disciplineId != null) {
                $scope.getModalitiesByDisciplineId($scope.trainingLevel.disciplineId, false);
            }

            $scope.saveTrainingLevel = function (trainingLevel) {
                if (trainingLevel.id == null) {
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
                } else {
                    ObjectiveService.mergeObjective(trainingLevel).then(
                            function (data) {
                                $scope.showMessage("Nivel Actualizado exitosamente.");
                                $scope.getTrainingLevel();
                                $mdDialog.hide();
                            },
                            function (error) {
                                console.log(error);
                            }
                    );
                }
            };

        }

        $scope.getTrainingLevel();
        $scope.getLevelTypes();

    }]);