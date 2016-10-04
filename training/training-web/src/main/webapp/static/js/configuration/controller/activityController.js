trainingApp.controller('ActivityController', ['$scope', 'ActivityService','ModalityService','ObjectiveService', 'PhysiologicalCapacityService',
    'SportService','DisciplineService','$window',
function ($scope, ActivityService, ModalityService,ObjectiveService,PhysiologicalCapacityService,SportService,DisciplineService,
        $window) {
    $scope.activity = {activityId: null,
        physiologicalCapacityId: {physiologicalCapacityId: null,name: ''},
        modalityId: {modalityId: null, modalityName: '',disciplineId:''},
        objectiveId: {objectiveId: null, objectiveName: ''},
        name: '',
        description: '',
        sportId: {sportId: null, sportName: ''},
        discipline:'',
        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: '',stateId:''};

    $scope.activityList = [];
    $scope.physiologicalCapacityList = [];
    $scope.modalityList = [];
    $scope.objectiveList = [];
    $scope.sportList = [];
    $scope.disciplineList = [];
    $scope.count = 0;

    $scope.selected = [];

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };

    function success(response) {
        if (response.data.status == 'fail') {
            $scope.showMessage(response.data.output);
        } else {
            return response.data.output;
        }

        return null;
    }

    $scope.getActivityPaginate = function () {
        $scope.promise = ActivityService.getPaginate($scope.query, function (response) {
            $scope.activityList = success(response);

            if ($scope.activityList.length > 0) {
                $scope.count = $scope.activityList[0].count;
            }
        }).$promise;
    };
    
    $scope.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplineList = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };

    $scope.getPhysiologicalCapacityList = function () {
        PhysiologicalCapacityService.getPhysiologicalCapacity(function (response) {
            $scope.physiologicalCapacityList = success(response);
        });
    };

    $scope.getModalitiesByDisciplineId = function (id, scope) {
        ModalityService.getModalitiesByDisciplineId(id).then(
                function (d) {
                    $scope.modalityList = d;
                    if(scope != null) {                        
                        $scope.activity = scope;
                    }
                },
                function (errResponse) {
                    console.error('Error while modalities');
                    console.error(errResponse);
                }
        );
    };
    $scope.getObjectives = function () {
        ObjectiveService.getObjectives().then(
                function (d) {
                    $scope.objectiveList = d;
                },
                function (errResponse) {
                    console.error('Error while objectives');
                    console.error(errResponse);
                }
        );
    };

    $scope.getSportList = function () {
        SportService.getSportDisciplines(function (response) {
            $scope.sportList = success(response);
        });
    };


    $scope.createActivity = function (activity) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            activity.userCreate = (user.userId);
        }
        ActivityService.createActivity(activity)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetActivity();
                                $scope.showMessage(d.output);
                                $scope.getActivityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Activity.');
                        }
                );
    };

    $scope.updateActivity = function (activity) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            activity.userUpdate = (user.userId);
        }

        ActivityService.mergeActivity(activity)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetActivity();
                                $scope.showMessage(d.output);
                                $scope.getActivityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Activity.');
                        }
                );
    };

    $scope.deleteActivity = function (activity) {
        ActivityService.deleteActivity(activity)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetActivity();
                                $scope.showMessage(d.output);
                                $scope.getActivityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while deleting Activity.');
                        }
                );
    };

    $scope.submitActivity = function (form) {
        if ($scope.validateFields(form)) {
            if ($scope.activity.activityId === null) {
                $scope.createActivity($scope.activity);
            } else {
                $scope.updateActivity($scope.activity);
            }
        } 
    };

    $scope.editActivity = function (id) {
        for (var i = 0; i < $scope.activityList.length; i++) {
            if ($scope.activityList[i].activityId === id) {
                $scope.activity = angular.copy($scope.activityList[i]);
                $scope.activity.discipline = $scope.activityList[i].modalityId.disciplineId.disciplineId;
                $scope.getModalitiesByDisciplineId($scope.activity.discipline,$scope.activity);
                break;
            }
        }
    };

    $scope.inactivateActivity = function (id) {
        for (var i = 0; i < $scope.activityList.length; i++) {
            if ($scope.activityList[i].activityId === id) {
                $scope.activity = angular.copy($scope.activityList[i]);
                break;
            }
        }
        $scope.activity.stateId = 0;
        $scope.updateActivity($scope.activity);
        $scope.resetActivity();
    };

    $scope.activateActivity = function (id) {
        for (var i = 0; i < $scope.activityList.length; i++) {
            if ($scope.activityList[i].activityId === id) {
                $scope.activity = angular.copy($scope.activityList[i]);
                break;
            }
        }
        $scope.activity.stateId = 1;
        $scope.updateActivity($scope.activity);
    };

    $scope.removeActivity = function (id) {
        if ($scope.activity.activityId === id) {
            $scope.resetActivity();
        }
        $scope.deleteActivity(id);
    };

    $scope.resetActivity = function () {
        $scope.activity = {activityId: null, physiologicalCapacityId: {physiologicalCapacityId: null}, physiologicalCapacityName: '',
            modalityId: {modalityId: null}, modalityName: '',
            objectiveId: {objectiveId: null}, objectiveName: '',
            name: '',
            description: '',
            sportId: {sportId: null}, sportName: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
            $scope.formActivity.$setPristine();
    };
    
    $scope.validateFields = function (form) {
            var valid = true;
            if($scope.activity.objectiveId == '' || $scope.activity.objectiveId == null) {
                form.objective.$setTouched(); 
                valid = false;
            }
            if($scope.activity.physiologicalCapacityId == '' || $scope.activity.physiologicalCapacityId == null) {
                form.physiologicalCapacityId.$setTouched(); 
                valid = false;
            }
            if($scope.activity.discipline == '' || $scope.activity.discipline == null) {
                form.discipline.$setTouched();  
                valid = false;
            }
            if($scope.activity.modalityId == '' || $scope.activity.modalityId == null) {
                form.modality.$setTouched();  
                valid = false;
            }
            if($scope.activity.name == '' || $scope.activity.name == null) {
                form.name.$setTouched();  
                valid = false;
            }
            if($scope.activity.description == '' || $scope.activity.description == null) {
                form.weather.$setTouched();  
                valid = false;
            }
            if($scope.activity.sportId == '' || $scope.activity.sportId == null) {
                form.weather.$setTouched();  
                valid = false;
            }
            
            return valid;
        };

    $scope.getActivityPaginate();
    $scope.getPhysiologicalCapacityList();
    $scope.getObjectives();
    $scope.getSportDisciplines();
    $scope.getSportList();

}]);