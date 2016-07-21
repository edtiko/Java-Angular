'use strict';

trainingApp.controller('UserProfileController', ['$scope', 'UserProfileService', 'DisciplineService', 'SportService','SportEquipmentService',
    'ObjetiveService','ModalityService',
    function ($scope, UserProfileService,DisciplineService,SportService,SportEquipmentService, ObjetiveService,ModalityService) {
        
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
            bikes:[],
            potentiometer:'',
            pulsometer:'',
            objetive:'',
            modality:'',
            availability: [] 
        };
        
        $scope.disciplines =[];
        $scope.sports = [];
        $scope.shoes = [];
        $scope.bikes = [];
        $scope.pulsometers = [];
        $scope.potentiometers = [];
        $scope.objetives = [];
        $scope.modalities = [];
        $scope.days = [
                {day:'Lunes',checked:false},
                {day:'Martes',checked:false},
                {day:'Miercoles',checked:false},
                {day:'Jueves',checked:false},
                {day:'Viernes',checked:false},
                {day:'Sabado',checked:false},
                {day:'Domingo',checked:false}
            ] 
        $scope.metricSystems = [{id:1,name:'Metrico Decimal'},{id:'2',name:"Anglosajón"}];

        this.mergeUserProfile = function (userProfile) {
            UserProfileService.mergeProfile(userProfile).then(
                    function (d) {
                        $scope.userProfile = d;
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
//                        this.getModalitiesByDisciplineId($scope.userProfile.discipline);
                    },
                    function (errResponse) {
                        console.error('Error while fetching the profile');
                        console.error(errResponse);
                    }                        
            );
//            ModalityService.getModalitiesByDisciplineId(userProfile.discipline).then(
//                    function (d) {
//                        $scope.modalities = d;
//                    },
//                    function (errResponse) {
//                        console.error('Error while modalities');
//                        console.error(errResponse);
//                    }
//            );
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
        
        this.getObjetives = function () {
            ObjetiveService.getObjetives().then(
                    function (d) {
                        $scope.objetives = d;
                        $scope.objetives.unshift({objetiveId:-1,name:'Seleccione',level:'',maxSessions:'',minSessions:''});
                    },
                    function (errResponse) {
                        console.error('Error while objetives');
                        console.error(errResponse);
                    }
            );
        };
        this.getObjetives();
        
        this.getModalitiesByDisciplineId = function (id) {
            ModalityService.getModalitiesByDisciplineId(id).then(
                    function (d) {
                        $scope.modalities = d;
//                        $scope.objetives.unshift({objetiveId:-1,name:'Seleccione',level:'',maxSessions:'',minSessions:''});
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
//                        $scope.objetives.unshift({objetiveId:-1,name:'Seleccione',level:'',maxSessions:'',minSessions:''});
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };
        this.getModalities();
        
    }]);
