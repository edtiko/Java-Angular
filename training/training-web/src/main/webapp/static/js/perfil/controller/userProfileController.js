'use strict';

trainingApp.controller('UserProfileController', ['$scope', 'UserProfileService', 'DisciplineService', 'SportService','SportEquipmentService',
    'ObjectiveService','ModalityService',
    function ($scope, UserProfileService,DisciplineService,SportService,SportEquipmentService, ObjectiveService,ModalityService) {
        
        $scope.userProfile = {
            userProfileId: null,
            indPulsometer: '',
            indPower: '',
            ageSport: '',
            ppm: '',
            power: '',
            sportsAchievements: '',
            aboutMe: '',
            userId: '',
            indMetricSys:'-1',
            discipline:'',
            sport:'',
            shoes:'',
            bikes:'',
            potentiometer:'',
            pulsometer:'',
            objective:'',
            modality:'',
            availability:  [
                {day:'Lunes',checked:false},
                {day:'Martes',checked:false},
                {day:'Miercoles',checked:false},
                {day:'Jueves',checked:false},
                {day:'Viernes',checked:false},
                {day:'Sabado',checked:false},
                {day:'Domingo',checked:false}
            ] 
        };
        
        $scope.disciplines =[];
        $scope.sports = [];
        $scope.shoes = [];
        $scope.bikes = [];
        $scope.pulsometers = [];
        $scope.potentiometers = [];
        $scope.objectives = [];
        $scope.modalities = [];
        $scope.indBike = '';
        $scope.metricSystems = [{id:1,name:'Metrico Decimal'},{id:'0',name:"Anglosajón"}];

        this.createOrMergeUserProfile = function (userProfile) {
            if (userProfile.userProfileId == null) {
                UserProfileService.createProfile(userProfile).then(
                        function (d) {
                            $scope.userProfile = d;
                        },
                        function (errResponse) {
                            console.error('Error while creating the profile');
                            console.error(errResponse);
                        }
                );

            } else {
                UserProfileService.mergeProfile(userProfile).then(
                        function (d) {
                            $scope.userProfile = d;
                        },
                        function (errResponse) {
                            console.error('Error while merging the profile');
                            console.error(errResponse);
                        }
                );
            }
        };
        
        this.generatePlan = function (userProfile) {
            UserProfileService.generatePlan(userProfile).then(
                    function (d) {
//                        $scope.userProfile = d;
//                        this.findById(userProfile);
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
                        console.error(errResponse);
                    }
            );
        };
        
        this.findById = function (userProfile) {
            UserProfileService.getProfile(userProfile).then(
                    function (d) {
                        $scope.userProfile = d;
                        if($scope.userProfile.bikes != null && $scope.userProfile.bikes != -1) {
                            $scope.indBike = 1;
                        }
                        var disc = $scope.userProfile.discipline;
                        $scope.getModalitiesByDisciplineId(disc);
                    },
                    function (errResponse) {
                        console.error('Error while fetching the profile');
                        console.error(errResponse);
                    }                        
            );
        };
        
        this.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplines = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };
        this.getSportDisciplines();
        
        this.getSports = function () {
            SportService.getSports().then(
                    function (d) {
                        $scope.sports = d;
                        $scope.sports.unshift({sportId:-1,name:'Seleccione'});
                        $scope.userProfile.sport=-1;
                    },
                    function (errResponse) {
                        console.error('Error while sports');
                        console.error(errResponse);
                    }
            );
        };
        this.getSports();
        
        this.getRunningShoes = function () {
            SportEquipmentService.getRunningShoes().then(
                    function (d) {
                        $scope.shoes = d;
                        $scope.shoes.unshift({sportEquipmentId:-1,name:'Seleccione',brand:'Seleccione'});
                        $scope.userProfile.shoes=-1;
                    },
                    function (errResponse) {
                        console.error('Error while running shoes ');
                        console.error(errResponse);
                    }
            );
        };
        this.getRunningShoes();
        
        this.getBikes = function () {
            SportEquipmentService.getBikes().then(
                    function (d) {
                        $scope.bikes = d;
                        $scope.bikes.unshift({sportEquipmentId:'',name:'Seleccione',brand:'Seleccione'});
                    },
                    function (errResponse) {
                        console.error('Error while bikes');
                        console.error(errResponse);
                    }
            );
        };
        this.getBikes();
        
        this.getPulsometers = function () {
            SportEquipmentService.getPulsometers().then(
                    function (d) {
                        $scope.pulsometers = d;
                        $scope.pulsometers.unshift({sportEquipmentId:-1,name:'Seleccione',brand:'Seleccione'});
                    },
                    function (errResponse) {
                        console.error('Error while pulsometers');
                        console.error(errResponse);
                    }
            );
        };
        this.getPulsometers();
        
        this.getPotentiometers = function () {
            SportEquipmentService.getPotentiometers().then(
                    function (d) {
                        $scope.potentiometers = d;
                        $scope.potentiometers.unshift({sportEquipmentId:-1,name:'Seleccione',brand:'Seleccione'});
                    },
                    function (errResponse) {
                        console.error('Error while potentiometers');
                        console.error(errResponse);
                    }
            );
        };
        this.getPotentiometers();
        
        this.getObjectives = function () {
            ObjectiveService.getObjectives().then(
                    function (d) {
                        $scope.objectives = d;
                        $scope.objectives.unshift({objectiveId:-1,name:'Seleccione',level:''});
                    },
                    function (errResponse) {
                        console.error('Error while objectives');
                        console.error(errResponse);
                    }
            );
        };
        this.getObjectives();
        
        $scope.getModalitiesByDisciplineId = function (id) {
            ModalityService.getModalitiesByDisciplineId(id).then(
                    function (d) {
                        $scope.modalities = d;
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };
        
        this.getModalities = function () {
            ModalityService.getAll().then(
                    function (d) {
                        $scope.modalities = d;
//                        $scope.objectives.unshift({objectiveId:-1,name:'Seleccione',level:'',maxSessions:'',minSessions:''});
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };
        this.getModalities();
        
    }]);
