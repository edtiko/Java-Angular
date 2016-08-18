
trainingApp.controller('UserController', ['$scope', 'UserService', '$window', 'UserProfileService', 'DisciplineService', 'SportService', 'SportEquipmentService',
    'ObjectiveService', 'ModalityService', 'surveyService','VisibleFieldsUserService', function ($scope, UserService,
            $window, UserProfileService, DisciplineService, SportService, SportEquipmentService, ObjectiveService, ModalityService, surveyService,VisibleFieldsUserService) {
        var self = this;
        $scope.user = {userId: null, firstName: '', secondName: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', instagramPage: '', twitterPage: '', webPage: '', countryId: '', profilePhoto: '', age: ''};
        $scope.users = [];
        $scope.countries = [];
        $scope.states = [];
        $scope.cities = [];
        $scope.dateAsString = null;
        $scope.birthdateDt = null;
        $scope.dataImage = "static/img/profile-default.png";
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.isImage = false;
        self.fetchAllCountries = function () {
            UserService.fetchAllCountries()
                    .then(
                            function (response) {
                                $scope.countries = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );
        };
        $scope.getStatesByCountry = function (countryId) {
            if (countryId != null) {
                UserService.getStatesByCountry(countryId)
                        .then(
                                function (response) {
                                    $scope.states = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };
        $scope.getCitiesByState = function (stateId) {
            if (stateId != null) {
                UserService.getCitiesByState(stateId)
                        .then(
                                function (response) {
                                    $scope.cities = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };
        $scope.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.dataImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.dataImage = "static/img/profile-default.png";
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                }
                        );
            }
        };

        $scope.resetProfile = function () {
            self.getUserById();
        };
        $scope.showAge = function(date){
            $scope.birthdateDt = date;
            $scope.user.age = $scope.calculateAge(date);  
        };

        self.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                UserService.getUserById(user.userId)
                        .then(
                                function (d) {

                                    $scope.user = d;
                                    $scope.getStatesByCountry($scope.user.countryId);
                                    $scope.getCitiesByState($scope.user.federalStateId);
                                    $scope.getImageProfile($scope.user.userId);

                                    if ($scope.user.birthDate != null) {
                                        var date = $scope.user.birthDate.split("/");
                                        $scope.birthdateDt = new Date(date[2], date[1] - 1, date[0]);
                                        $scope.user.age = $scope.calculateAge($scope.birthdateDt);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );


                UserProfileService.getProfile(user).then(
                        function (d) {
                            $scope.userProfile = d;
                            if ($scope.userProfile.bikes != null && $scope.userProfile.bikes != -1) {
                                $scope.indBike = 1;
                            }
                            var disc = $scope.userProfile.discipline;
                            $scope.getModalitiesByDisciplineId(disc);

                            if ($scope.userProfile.potentiometer != "" && $scope.userProfile.potentiometer != null) {
                                $scope.getModelsPotentiometer($scope.userProfile.potentiometer);
                            }
                            if ($scope.userProfile.pulsometer != "" && $scope.userProfile.pulsometer != null) {
                                $scope.getModelsPulsometer($scope.userProfile.pulsometer);
                            }
                            if($scope.userProfile.bikes != "" && $scope.userProfile.bikes != null){
                                $scope.getModelsBike($scope.userProfile.bikes);
                            }
                            $scope.calculateZone();
                            $scope.calculatePpm();

                        },
                        function (errResponse) {
                            console.error('Error while fetching the profile');
                            console.error(errResponse);
                        }
                );

            } else {
                $scope.showMessage("El usuario no se encuentra logueado.", "error");
            }
        };

        self.createUser = function (user) {
            user.birthDate = $scope.birthdateDt;
            UserService.createUser(user)
                    .then(
                            function (msg) {
                                $scope.getVisibleFieldsUserByUser();
                                $scope.showMessage("Usuario registrado correctamente.");
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
            VisibleFieldsUserService.createVisibleFieldsUser(user.userId,$scope.visibleFields).then(
                    function (msg) {
                        $scope.setUserSession();
                        console.log(msg);
                    },
                    function (errResponse) {
                        console.error('Error while creating visible fields.');
                        console.error(errResponse);
                    }
            );
        };

        self.updateUser = function (user, id) {
            user.birthDate = $scope.birthdateDt;
            UserService.updateUser(user, id)
                    .then(
                            function (msg) {
                                $scope.getVisibleFieldsUserByUser();
                                $scope.showMessage("Usuario registrado correctamente.");
                           
                            },
                            function (errResponse) {
                                console.error('Error while updating User.');
                            }
                    );
            VisibleFieldsUserService.createVisibleFieldsUser(user.userId,$scope.visibleFields).then(
                    function (msg) {
                    $scope.setUserSession();
                        console.log(msg);
                    },
                    function (errResponse) {
                        console.error('Error while creating visible fields.');
                        console.error(errResponse);
                    }
            );
        };

        self.authenticateUser = function (login, password) {
            UserService.authenticateUser(login, password)
                    .then(
                            function (errResponse) {
                                console.error('Error while authenticate User.');
                            }
                    );
        };

        self.deleteUser = function (id) {
            UserService.deleteUser(id)
                    .then(
                            self.fetchAllUsers,
                            function (errResponse) {
                                console.error('Error while deleting User.');
                            }
                    );
        };

        self.fetchAllCountries();
        self.getUserById();


        $scope.submitUser = function () {
            if ($scope.user.userId === null) {
                console.log('Saving New User', $scope.user);
                self.createUser($scope.user);
                $scope.user.age = $scope.calculateAge($scope.birthdateDt);
            } else {
                self.updateUser($scope.user, $scope.user.userId);
                console.log('User updated with id ', $scope.user.userId);
            }
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].userId === id) {
                    $scope.getStatesByCountry($scope.users[i].countryId);
                    $scope.getCitiesByState($scope.users[i].federalStateId);
                    $scope.getImageProfile(id);
                    var date = $scope.users[i].birthDate.split("/");
                    $scope.birthdateDt = new Date(date[2], date[1] - 1, date[0]);

                    $scope.user = angular.copy($scope.users[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if ($scope.user.userId === id) {//clean form if the user to be deleted is shown there.
                self.resetUser();
            }
            self.deleteUser(id);
        };


        $scope.resetUser = function () {
            $scope.user = {firstName: '', secondName: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', countryId: '', profilePhoto: '', age: ''};
            $scope.birthdateDt = null;
            $scope.myFormUser.$setPristine(); //reset Form
        };

        $scope.isImage = function (type) {
            if (type.indexOf("image") !== -1) {
                return false;
            }
            return true;
        };

        self.login = function () {

            console.log('Loging User', $scope.user);
            self.authenticateUser($scope.user.login, $scope.user.password);

        };

        $scope.uploadFile = function (file) {

            //var file = $scope.myFile;
            if(file !== undefined && $scope.isImage(file.type)){
                $scope.showMessage("Debe seleccionar una imagen valida.", "error"); 
                //$window.alert("Debe seleccionar una imagen valida.");
            } else if ($scope.user.userId != "" && file != null) {

                console.log('file is ');
                console.dir(file);
                UserService.uploadFileToUrl(file, $scope.user.userId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Imagen cargada correctamente.");
                                    $scope.getImageProfile($scope.user.userId);

                                },
                                function (errResponse) {
                                    console.error('Error while upload image user.');
                                }
                        );
            } else {
                $scope.showMessage("Debe seleccionar una imagen.", "error");
            }
        };

        // Controller User-Profile //
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
            indMetricSys: '-1',
            discipline: '',
            sport: '',
            shoes: '',
            bikes: '',
            modelBike: '',
            potentiometer: '',
            otherPotentiometer: '',
            modelPotentiometer:'',
            otherModelPotentiometer: '',
            pulsometer: '',
            otherPulsometer: '',
            modelPulsometer: '',
            otherModelPulsometer: '',
            objective: '',
            vo2Running: '',
            vo2Ciclismo: '',
            modality: '',
            environmentId: '',
            weatherId: '',
            availability: [
                {day: 'Lunes', checked: false},
                {day: 'Martes', checked: false},
                {day: 'Miercoles', checked: false},
                {day: 'Jueves', checked: false},
                {day: 'Viernes', checked: false},
                {day: 'Sabado', checked: false},
                {day: 'Domingo', checked: false}
            ]
        };

        $scope.disciplines = [];
        $scope.sports = [];
        $scope.shoes = [];
        $scope.bikes = [];
        $scope.pulsometers = [];
        $scope.potentiometers = [];
        $scope.modelsPotentiometer = [];
        $scope.modelsPulsometer = [];
        $scope.objectives = [];
        $scope.modalities = [];
        $scope.entornos = [];
        $scope.climas = [];
        $scope.days = [
            {day: 'Lunes', checked: false},
            {day: 'Martes', checked: false},
            {day: 'Miercoles', checked: false},
            {day: 'Jueves', checked: false},
            {day: 'Viernes', checked: false},
            {day: 'Sabado', checked: false},
            {day: 'Domingo', checked: false}
        ];
        $scope.indBike = '';
        $scope.metricSystems = [{id: 1, name: 'Metrico Decimal'}, {id: '0', name: "Anglosaj\u00f3n"}];

        $scope.createOrMergeUserProfile = function (userProfile) {
            if (userProfile.userProfileId == null) {
                UserProfileService.createProfile(userProfile).then(
                        function (d) {
                            $scope.userProfile = d;
                             self.getEquipments();
                            $scope.showMessage("Perfil Creado satisfactoriamente.");
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
                            self.getEquipments();
                            $scope.showMessage("Perfil editado satisfactoriamente.");
                       
                        },
                        function (errResponse) {
                            console.error('Error while merging the profile');
                            console.error(errResponse);
                        }
                );
            }
            VisibleFieldsUserService.createVisibleFieldsUser(userProfile.userId,$scope.visibleFields).then(
                    function (msg) {
                        $scope.setUserSession();
                        console.log(msg);
                    },
                    function (errResponse) {
                        console.error('Error while creating visible fields.');
                        console.error(errResponse);
                    }
            );
            
        };
        self.getEquipments = function () {
            this.getPotentiometers();
            this.getPulsometers();
            $scope.showAnotherPotentiometer = false;
            $scope.showAnotherPulsometer = false;
            $scope.showModelPotentiometer = true;
            if ($scope.userProfile.potentiometer != "" && $scope.userProfile.potentiometer != null) {
                $scope.getModelsPotentiometer($scope.userProfile.potentiometer);
            }
            if ($scope.userProfile.pulsometer != "" && $scope.userProfile.pulsometer != null) {
                $scope.getModelsPulsometer($scope.userProfile.pulsometer);
            }
            if ($scope.userProfile.bikes != "" && $scope.userProfile.bikes != null) {
                $scope.getModelsBike($scope.userProfile.bikes);
            }
        };
      
        $scope.generatePlan = function (userProfile) {
            UserProfileService.generatePlan(userProfile).then(
                    function (d) {
                        $scope.showMessage(d.data.output);
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
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
                        
                        if($scope.sports != "") {
                            $scope.sports.unshift({sportId: -1, name: 'Seleccione'});
                        }
                        $scope.userProfile.sport = -1;
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
                        $scope.shoes.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'});
                        $scope.userProfile.shoes = -1;
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
                        $scope.bikes.unshift({sportEquipmentId: '', name: 'Seleccione', brand: 'Seleccione'});
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
                        $scope.pulsometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'}, {sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
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
                        $scope.potentiometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'}, {sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
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
                        $scope.objectives.unshift({objectiveId: -1, name: 'Seleccione', level: ''});
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
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };
        this.getModalities();
        
          this.getEntornos = function () {
            SportService.getEntornos().then(
                    function (d) {
                        $scope.entornos = d;
                    },
                    function (errResponse) {
                        console.error('Error get entornos');
                        console.error(errResponse);
                    }
            );
        };
        this.getEntornos();

        this.getClimas = function () {
            SportService.getClimas().then(
                    function (d) {
                        $scope.climas = d;
                    },
                    function (errResponse) {
                        console.error('Error get entornos');
                        console.error(errResponse);
                    }
            );
        };
        this.getClimas();
        
        $scope.getAvailabilityIdx = function ($parentIndex, value) {
            var response = $scope.userProfile.availability;
            var res = '';
            angular.forEach(response, function (v, key) {
                if (v == value) {
                    res = key;
                }
            });

            return res;
        };

        $scope.getAvailabilityResponse = function ($parentIndex, value) {
            var response = $scope.userProfile.availability;
            var res = false;
            angular.forEach(response, function (v, key) {
                if (v == value) {
                    res = true;
                }
            });

            return res;
        };



        $scope.setAvailabilityResponse = function ($parentIndex, $index, value) {
            var response = $scope.userProfile.availability;
            var idx = $scope.getAvailabilityIdx($parentIndex, value);
            if (idx !== "" && response[idx].checked) {
                response[idx].checked = false;
            } else if (idx !== "" && !response[idx].checked) {
                response[idx].checked = true;
            } else if (idx !== "") {
                response = value;
            }
        };
        
        $scope.getModelsPotentiometer = function (sportEquipmentId) {
            if (sportEquipmentId === -2) { //another potentiometer
                $scope.showAnotherPotentiometer = true;
                $scope.showModelPotentiometer = false;
            } else {
                $scope.showAnotherPotentiometer = false;
                $scope.showModelPotentiometer = true;
                SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                        function (d) {
                            $scope.modelsPotentiometer = d;
                            $scope.modelsPotentiometer.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                        },
                        function (errResponse) {
                            console.error('Error while models potentiometer');
                            console.error(errResponse);
                        }
                );
            }
        };
        
         $scope.getModelsPulsometer = function (sportEquipmentId) {
             if(sportEquipmentId === -2){ //another pulsometer
                 $scope.showAnotherPulsometer = true;
                 $scope.showModelPulsometer = false;
             }else{
                  $scope.showAnotherPulsometer = false;
                  $scope.showModelPulsometer = true;
            SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                    function (d) {
                        $scope.modelsPulsometer = d;
                        $scope.modelsPulsometer.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                    },
                    function (errResponse) {
                        console.error('Error while models pulsometer');
                        console.error(errResponse);
                    }
            );
         }
        };
        
         $scope.getModelsBike = function (sportEquipmentId) {
                SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                        function (d) {
                            $scope.modelsBike = d;
                            $scope.modelsBike.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                        },
                        function (errResponse) {
                            console.error('Error while models bikes');
                            console.error(errResponse);
                        }
                );
            
        };
 
        $scope.visibleField = function (tableName, columnName) {
                for (var i = 0; i < $scope.fields.length; i++) {
                    if ($scope.fields[i].tableName == tableName && $scope.fields[i].columnName == columnName) {
                        return true;
                    }
                }
            return false;
        };

        $scope.setVisibleField = function (value, tableName, columnName) {
            if (value.target.checked == true) {
                $scope.visibleFields.push({tableName: tableName, columnName: columnName, userId: $scope.user.userId});
            } else {
                $scope.deleteFieldInArray({tableName: tableName, columnName: columnName, userId: $scope.user.userId}, $scope.visibleFields);
            }
        };

        $scope.deleteFieldInArray = function (field, array) {
            var length = array.length;
            for (var i = 0; i < length; i++) {
                if (array[i].tableName == field.tableName && array[i].columnName == field.columnName) {
                    $scope.visibleFields.splice(i, 1);
                    break;
                }
            }
        };
        
        $scope.calculateZone = function () {
            if ($scope.userProfile.power !== "") {
                $scope.ftp56 = ($scope.userProfile.power * 56) / 100;
                $scope.ftp75 = ($scope.userProfile.power * 75) / 100;
                $scope.ftp76 = ($scope.userProfile.power * 76) / 100;
                $scope.ftp90 = ($scope.userProfile.power * 90) / 100;
                $scope.ftp91 = ($scope.userProfile.power * 91) / 100;
                $scope.ftp105 = ($scope.userProfile.power * 105) / 100;
                $scope.ftp106 = ($scope.userProfile.power * 106) / 100;
                $scope.ftp120 = ($scope.userProfile.power * 120) / 100;
            }
        };
        $scope.calculatePpm = function () {
            if ($scope.userProfile.ppm !== "") {
                $scope.ppm81 = ($scope.userProfile.ppm * 81) / 100;
                $scope.ppm89 = ($scope.userProfile.ppm * 89) / 100;
                $scope.ppm90 = ($scope.userProfile.ppm * 90) / 100;
                $scope.ppm93 = ($scope.userProfile.ppm * 93) / 100;
                $scope.ppm94 = ($scope.userProfile.ppm * 94) / 100;
                $scope.ppm99 = ($scope.userProfile.ppm * 99) / 100;
                $scope.ppm100 = ($scope.userProfile.ppm * 100) / 100;
                $scope.ppm102 = ($scope.userProfile.ppm * 102) / 100;
                $scope.ppm103 = ($scope.userProfile.ppm * 103) / 100;
                $scope.ppm106 = ($scope.userProfile.ppm * 106) / 100;
            }
        };

        // Survey Controller //

        $scope.survey = [];
        $scope.questionnaireResponseList = {};
        $scope.user = JSON.parse($window.sessionStorage.getItem("userInfo"));
        self.questionnaireResponse = {
            questionnaireResponseId: '',
            response: '',
            questionOptionId: '',
            userId: '',
            checked: true,
            questionnaireQuestionId: '',
            reponseTypeId: '1'

        };
        $scope.categories = [];

        $scope.inCategories = function (id) {

            var i = 0, len = $scope.categories.length;
            for (; i < len; i++) {
                if (+$scope.categories[i].questionnaireCategoryId == +id) {
                    return true;
                }
            }
            return false;

        };

        $scope.setValue = function ($index) {
            var response = $scope.survey[$index].questionnaireResponseList[0];
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            if (response != null && response.response !== 'undefined' || response.questionOptionId !== "") {
                response.userId = $scope.user.userId;
                response.questionnaireQuestionId = $scope.survey[$index].questionnaireQuestionId;
                response.reponseTypeId = 1;
            } else {
                response = angular.copy(self.questionnaireResponse);
                response.userId = $scope.user.userId;
                response.questionnaireQuestionId = $scope.survey[$index].questionnaireQuestionId;
                response.reponseTypeId = user.userId;
                $scope.survey[$index].questionnaireResponseList.push(response);
            }

        };

        $scope.setResponse = function ($parentIndex, $index, value) {
            var response = $scope.survey[$parentIndex].questionnaireResponseList;
            var idx = $scope.getIdx($parentIndex, value);
            if (idx !== "" && response[idx].checked) {
                response[idx].checked = false;
                response.splice(idx, 1);
            } else if (idx !== "" && !response[idx].checked) {
                response[idx].checked = true;
            } else if (idx !== "") {
                response.questionOptionId = value;
            } else {
                response = angular.copy(self.questionnaireResponse);
                response.userId = $scope.user.userId;
                response.questionnaireQuestionId = $scope.survey[$parentIndex].questionnaireQuestionId;
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

        $scope.getResponse = function ($parentIndex, value) {
            var response = $scope.survey[$parentIndex].questionnaireResponseList;
            var res = false;
            angular.forEach(response, function (v, key) {
                if (v.questionOptionId == value) {
                    res = true;
                }
            });

            return res;
        };


        self.getAllQuestionnaireQuestion = function () {

            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            if ($scope.appReady) {
                surveyService.getAllQuestionnaireQuestion(user.userId).then(
                        function (response) {
                            angular.forEach(response.data.entity.output, function (value, key) {
                                $scope.survey[key] = value;
                                if (value.questionnaireCategoryId != undefined && value.questionnaireCategoryId.questionnaireCategoryId != undefined) {
                                    if (!$scope.inCategories(value.questionnaireCategoryId.questionnaireCategoryId)) {
                                        $scope.categories.push(value.questionnaireCategoryId);
                                    }
                                }
                            });
                            console.log($scope.survey);
                            console.log($scope.categories);
                        },
                        function (errResponse) {
                            console.error('Error while fetching Currencies');
                        }
                );
            } else {
                $window.alert("El usuario no se encuentra logueado.");
            }
        };

        self.create = function (survey) {
            surveyService.create(survey)
                    .then(
                            function (message) {

                                $scope.showMessage("Respuestas registradas satisfactoriamente.");
                                self.getAllQuestionnaireQuestion(self.userId);
                            },
                            function (errResponse) {
                                //console.error('Error while creating Survey.');
                            }
                    );
        };


        $scope.resetSurvey = function () {
            //self.getAllQuestionnaireQuestion();
            angular.forEach($scope.survey, function (value, key) {
                angular.forEach(value.questionnaireResponseList, function (v, k) {
                    v.questionOptionId = "";
                    v.response = "";


                });
            });
        };

        $scope.submitSurvey = function () {
            self.create($scope.survey);
            //console.log($scope.survey);
            //self.reset();
        };
        self.getAllQuestionnaireQuestion();

    }]);