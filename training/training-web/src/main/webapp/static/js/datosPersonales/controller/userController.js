
trainingApp.controller('UserController', ['$scope', 'UserService', '$window', '$location', 'UserProfileService', 'DisciplineService', 'SportService', 'SportEquipmentService',
    'ObjectiveService', 'ModalityService', 'surveyService', 'VisibleFieldsUserService', 'BikeTypeService', '$location', '$mdDialog', 'DcfService', function ($scope, UserService,
            $window, $location, UserProfileService, DisciplineService, SportService, SportEquipmentService, ObjectiveService, ModalityService, surveyService,
            VisibleFieldsUserService, BikeTypeService, $location, $mdDialog, DcfService) {
        var self = this;
        $scope.currentNavItem = 0;
        $scope.steps = [
            {
                templateUrl: 'static/views/datosPersonales/step1.html',
                hasForm: true,
                title: 'DATOS PERSONALES'
            },
            {
                templateUrl: 'static/views/datosPersonales/step2.html',
                hasForm: true,
                title: 'DATOS DE CONTACTO'
            },
            {
                templateUrl: 'static/views/datosPersonales/step3.html',
                hasForm: true,
                title: 'DATOS DEPORTIVOS'
            },
            {
                templateUrl: 'static/views/datosPersonales/step4.html',
                hasForm: true,
                title: 'DATOS DEPORTIVOS'
            },
            {
                templateUrl: 'static/views/datosPersonales/step5.html',
                hasForm: true,
                title: 'OTROS DATOS'
            }
        ];
        $scope.onStepChange = function (index, event) {

            if (index == 3) {
                $scope.submitUser();
            }else if(index == 5){
                $scope.submitUserProfile(true, event);
            }
            $scope.currentNavItem = index - 1;
            console.log("el paso activo es:" + index);
        };
        $scope.userStravaAutorize = true;
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
        $scope.previousValue = "";
        $scope.maxDate = new Date();
        $scope.selected1 = [];
        $scope.selected = [];
        $scope.errorMessages = [];
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
        $scope.getStatesByCountry = function (countryId, change) {
            if (change) {
                $scope.user.cityId = '';
                $scope.user.federalStateId = '';
            }
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
        $scope.getCitiesByState = function (stateId, change) {
            if (change) {
                $scope.user.cityId = '';
            }
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
        $scope.showAge = function (d) {
            $scope.birthdateDt = d;

            var date = d.split("/");
            var obj = new Date(date[2], date[1], date[0]);
            $scope.user.age = $scope.calculateAge(obj);
        };

        $scope.redirectAutorizeStrava = function (ev) {
            if ($scope.userStravaAutorize) {
                window.location = 'https://www.strava.com/oauth/authorize?client_id=14512&response_type=code&redirect_uri=https://181.143.227.220:8088/training/strava/activities&scope=write&state=mystate&approval_prompt=force';
            } else {
                var confirm = $mdDialog.confirm()
                        .title('Confirmaci\u00f3n')
                        .textContent('\u00BFDesea desconectarse de strava?')
                        .ariaLabel('Lucky day')
                        .targetEvent(ev)
                        .ok('Aceptar')
                        .cancel('Cancelar');

                $mdDialog.show(confirm).then(function () {
                    var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                    UserService.updateStravaAutorizeUser(user.userId, '0')
                    .then(
                            function (msg) {
                                if(msg.status == 'success') {
                                    $scope.showMessage('La desconexi\u00f3n con strava fue exitosa');                                    
                                    $scope.userStravaAutorize = true;
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
                    
                }, function () {
                });
            }
        };

        self.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                UserService.getUserById(user.userId)
                        .then(
                                function (d) {

                                    $scope.user = d;

                                    if (d.indStrava == '1') {
                                        $scope.userStravaAutorize = false;
                                    }

                                    $scope.getStatesByCountry($scope.user.countryId);
                                    $scope.getCitiesByState($scope.user.federalStateId);
                                    $scope.getImageProfile($scope.user.userId);

                                    $scope.getVisibleFieldsUserByUser($scope.user);

                                    if ($scope.user.birthDate != null) {
                                        var date = $scope.user.birthDate.split("/");
                                        var obj = new Date(date[2], date[1], date[0]);
                                        $scope.birthdateDt = $scope.user.birthDate;
                                        $scope.user.age = $scope.calculateAge(obj);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );


                UserProfileService.getProfile(user).then(
                        function (data) {
                            if (data != "") {
                                $scope.userProfile = angular.copy(data);
                            }
                            if ($scope.userProfile.bikes != null && $scope.userProfile.bikes != -1) {
                                $scope.indBike = 1;
                            }

                            var disc = $scope.userProfile.discipline;
                            if (disc != undefined && disc != "") {
                                $scope.getObjectivesByDiscipline(disc, false);
                            }
                            if ($scope.userProfile.objective != undefined && $scope.userProfile.objective != "") {
                                $scope.getModalitiesByObjectiveId($scope.userProfile.objective);
                            }

                            if ($scope.userProfile.potentiometer != "" && $scope.userProfile.potentiometer != null) {
                                $scope.getModelsPotentiometer($scope.userProfile.potentiometer);
                            }
                            if ($scope.userProfile.pulsometer != "" && $scope.userProfile.pulsometer != null) {
                                $scope.getModelsPulsometer($scope.userProfile.pulsometer);
                            }
                            if ($scope.userProfile.bikes != "" && $scope.userProfile.bikes != null) {
                                $scope.getModelsBike($scope.userProfile.bikes);
                            }
                            if ($scope.userProfile.bikeType != "" && $scope.userProfile.bikeType != null) {
                                $scope.getBikes($scope.userProfile.bikeType);
                            }
                            if ($scope.userProfile.ftp105 <= 0) {
                                $scope.calculateZone();
                            }
                            if ($scope.userProfile.ppm100 <= 0) {
                                $scope.calculatePpm();
                            }
                            $scope.userProfile.height = parseFloat(Math.round($scope.userProfile.height * 100) / 100).toFixed(2);
                            $scope.userProfile.weight = parseFloat(Math.round($scope.userProfile.weight * 100) / 100).toFixed(2);
                            if ($scope.userProfile.weight != null && $scope.userProfile.height != null) {
                                $scope.calculateIMC();
                            }

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

        $scope.validateFieldsUser = function (form) {
            var valid = true;
            if ($scope.user.firstName == '' || $scope.user.firstName == null) {
                form.uname.$setTouched();
                valid = false;
            }
            if ($scope.user.lastName == '' || $scope.user.lastName == null) {
                form.lname.$setTouched();
                valid = false;
            }
            if ($scope.user.email == '' || $scope.user.email == null) {
                form.email.$setTouched();
                valid = false;
            }
            if ($scope.birthdateDt == '' || $scope.birthdateDt == null) {
                form.birthDate.$setTouched();
                valid = false;
            }
            if (form.postalCode.$error.maxlength) {
                $scope.errorMessages.push("C\u00f3digo Postal: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.address.$error.maxlength) {
                $scope.errorMessages.push("Direcci\u00f3n: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.phone.$error.maxlength) {
                $scope.errorMessages.push("Tel\u00e9fono: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.cellphone.$error.maxlength) {
                $scope.errorMessages.push("Celular: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.facebookPage.$error.maxlength) {
                $scope.errorMessages.push("Pagina de Facebook: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.twitterPage.$error.maxlength) {
                $scope.errorMessages.push("Pagina de Twitter: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.instagramPage.$error.maxlength) {
                $scope.errorMessages.push("Pagina de Instagram: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }
            if (form.webPage.$error.maxlength) {
                $scope.errorMessages.push("Pagina Web: Ha excedido el l\u00edmite de caracteres");
                //form.postalCode.$setTouched();
                valid = false;
            }


            return valid;
        };

        self.createUser = function (user, file) {
            user.birthDate = $scope.birthdateDt;
            UserService.createUser(user)
                    .then(
                            function (msg) {
                                $scope.getVisibleFieldsUserByUser();
                                if (file !== undefined && file != null) {
                                    $scope.uploadFile(file);
                                }
                                $scope.showMessage("Usuario registrado correctamente.");
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
            VisibleFieldsUserService.createVisibleFieldsUser(user.userId, $scope.visibleFields).then(
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

        self.updateUser = function (user, id, file) {
            user.birthDate = $scope.birthdateDt;
            var userUpdate = user;
            userUpdate.profilePhoto = '';
            userUpdate.profilePhotoBase64 = '';
            UserService.updateUser(user, id)
                    .then(
                            function (msg) {
                                $scope.getVisibleFieldsUserByUser();
                                if (file !== undefined && file != null) {
                                    $scope.uploadFile(file);
                                }
                                //$scope.showMessage("Usuario editado correctamente.");

                            },
                            function (errResponse) {
                                console.error('Error while updating User.');
                            }
                    );
            VisibleFieldsUserService.createVisibleFieldsUser(user.userId, $scope.visibleFields).then(
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

        $scope.getUserSession(function (res) {
            $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            $scope.userSession = user;
            if (user.typeUser === $scope.userSessionTypeUserAtleta && user.indLoginFirstTime == '1' &&
                    user.planActiveId != null && user.planActiveId != 0) {
                $scope.showMessage("Para poder generar tu plan, debes ingresar los datos deportivos y darle click en el bot\u00f3n generar plan");
            }
            self.fetchAllCountries();
            self.getUserById();
            self.getAllQuestionnaireQuestion();
        });

        $scope.submitUser = function (form, file) {
            //var user = $scope.user;
            //if (form.$valid) {
                if ($scope.user.userId === null) {
                    self.createUser($scope.user, file);
                } else {
                    self.updateUser($scope.user, $scope.user.userId, file);
                }
           /* } else {
                $scope.showMessage("Faltan campos por diligenciar", "Alerta");
                if ($scope.errorMessages.length > 0) {
                    $scope.showMessage($scope.errorMessages, "Alerta", true);
                }
                $scope.user = user;
                $scope.errorMessages = [];
                window.scrollTo(0, 200);
            }*/
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
            
            if (file.files[0] !== undefined) {
                var file = file.files[0];
            }
            if (file !== undefined && $scope.isImage(file.type)) {
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

        //----------------------------------------------------------- Controller User-Profile -------------------------------------------------------------------------------- //
        $scope.userProfile = {
            userProfileId: null,
            indPulsometer: '',
            indPower: '',
            ageSport: '',
            ppm: '',
            power: '',
            sportsAchievements: '',
            aboutMe: '',
            userId: $scope.user.userId,
            indMetricSys: '-1',
            discipline: '',
            sport: '',
            shoes: '',
            bikes: '',
            otherBike: '',
            otherModelBike: '',
            modelBike: '',
            potentiometer: '',
            otherPotentiometer: '',
            modelPotentiometer: '',
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
            injuryId: '',
            disease: '',
            availability: [
                {day: 'Lunes', checked: false},
                {day: 'Martes', checked: false},
                {day: 'Miercoles', checked: false},
                {day: 'Jueves', checked: false},
                {day: 'Viernes', checked: false},
                {day: 'Sabado', checked: false},
                {day: 'Domingo', checked: false}
            ],
            bikeType: '',
            ftp56: '',
            ftp75: '',
            ftp76: '',
            ftp90: '',
            ftp91: '',
            ftp105: '',
            ftp106: '',
            ftp120: '',
            ppm81: '',
            ppm89: '',
            ppm90: '',
            ppm93: '',
            ppm94: '',
            ppm99: '',
            ppm100: '',
            ppm102: '',
            ppm103: '',
            ppm106: '',
            weight: '',
            height: '',
            imc: ''
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
        $scope.lesiones = [];
        $scope.indBike = '';
        $scope.metricSystems = [{id: 1, name: 'Metrico Decimal'}, {id: '0', name: "Anglosaj\u00f3n"}];
        $scope.bikeTypes = [];
        $scope.weightmetric = '(Kg)';
        $scope.heightmetric = '(Mts)';

        $scope.submitUserProfile = function (generatePlan, ev) {
            if ($scope.validateFields()) {
                $scope.getSessions(ev, generatePlan);
            } else {
                if ($scope.errorMessages.length != 0) {
                    $scope.showMessage($scope.errorMessages, "Alerta", true);
                }
                $scope.errorMessages = [];
                window.scrollTo(0, 2400);
            }
        };

        $scope.calculateIMC = function () {
//            document.getElementById('height').onkeyup = oneDigitAndDecimals();
            if ($scope.userProfile.height != null && $scope.userProfile.height != "") {
                $scope.userProfile.height = convertToDecimal($scope.userProfile.height);
            }
            if ($scope.userProfile.weight != null && $scope.userProfile.height != null
                    && $scope.userProfile.weight != "" && $scope.userProfile.height != ""
                    && isNumeric($scope.userProfile.weight) && isNumeric($scope.userProfile.height)) {
                if ($scope.userProfile.indMetricSys == '0') { //Si es anglosajon pasamos a metros y kilogramos para calcular IMC
                    var weight = ($scope.userProfile.weight * 0.45);
                    var height = convertToDecimal($scope.userProfile.height * 30.0);
                    $scope.userProfile.imc = Math.round(weight / (height * height) * 10) / 10;
                } else {
                    $scope.userProfile.imc = Math.round($scope.userProfile.weight / ($scope.userProfile.height * $scope.userProfile.height) * 10) / 10;
                }
            } else if ($scope.userProfile.weight == undefined || $scope.userProfile.weight == "" || $scope.userProfile.height == undefined || $scope.userProfile.height == "") {
                $scope.userProfile.imc = null;
            }
        };

        function isNumeric(num) {
            return !isNaN(num);
        }

        function oneDigitAndDecimals() {
            if ($scope.previousValue == "") {
                $scope.previousValue = $scope.userProfile.height;
            }
            var pattern = /^\d{1}((\.|,)\d*)?$/;

//            function validateInput(event) {
//                event = event || window.event;
            var newValue = $scope.userProfile.height;

            if (newValue.match(pattern)) {
                // Valid input; update previousValue:
                $scope.previousValue = newValue;
            } else {
                // Invalid input; reset field value:
                $scope.userProfile.height = $scope.previousValue;
            }
//            }

//            document.getElementById('height').onkeyup = validateInput;
        }

        function convertToDecimal(num) {
            var sd = "";
            num = num + '';
            if (num.indexOf(".") == -1) {
                sd = num.splice(1, 0, ",");
                return sd;
            }
            return num;
        }

        $scope.setUnit = function (metricSystem) {
            if (metricSystem == '0') {
                $scope.weightmetric = '(Lb)';
                $scope.userProfile.weight = ($scope.userProfile.weight * 2.2);
                $scope.heightmetric = '(Ft)';
                $scope.userProfile.height = ($scope.userProfile.height * 3.3);
            } else {
                $scope.weightmetric = '(Kg)';
                $scope.userProfile.weight = Math.round($scope.userProfile.weight * 0.45);
                $scope.heightmetric = '(Mts)';
                $scope.userProfile.height = convertToDecimal($scope.userProfile.height / 100);
                $scope.userProfile.height = ($scope.userProfile.height * 30.0);
            }
        };


        $scope.confirmDialogGuardarUserProfile = function (ev, generatePlan, msg) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea guardar sus datos deportivos?' + msg)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                $scope.createOrMergeUserProfile($scope.userProfile, generatePlan);
            }, function () {
            });
        };

        $scope.getSessions = function (ev, generatePlan) {
            DcfService.getDcfByModalityIdAndObjectiveId($scope.userProfile.modality, $scope.userProfile.objective)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.dcf = (d.output);
                                    if ($scope.dcf[0] != null) {
                                        var weeklyDays = Math.floor($scope.dcf[0].sessions / 4);
                                        var days = 0;
                                        var length = $scope.userProfile.availability.length;
                                        for (var i = 0; i < length; i++) {
                                            if ($scope.userProfile.availability[i].checked) {
                                                days++;
                                            }
                                        }
                                    }
                                    var msg = '';

                                    if (generatePlan) {
                                        if (days < weeklyDays) {
                                            msg = "Tenga en cuenta que los dias requeridos para generar el plan son " + weeklyDays + " dias, pulse Aceptar si desea que el sistema los genere aleatoriamente o Cancelar si desea hacerlo manualmente";
                                        }
                                        if ($scope.userSession.planActiveId == '0') {
                                            $scope.showMessageConfirmation(
                                                    "Para generar tu plan debes adquirir un plan de entrenamiento. <br> Recuerda que debes seleccionar tu disciplina y tu estrella favorita. Consiguelo y disfruta de tu rutina de ejercicios.<br> Deseas adquirirlo? "
                                                    , 'Informaci\u00f3n', urlCompraPlanEntrenamiento);
                                            return;
                                        }
                                        $scope.confirmDialogGeneratePlan(ev, generatePlan, msg);
                                    } else {
                                        $scope.confirmDialogGuardarUserProfile(ev, generatePlan, msg);
                                    }
                                } else {
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Dcf.');
                            }
                    );
        };


        $scope.createOrMergeUserProfile = function (userProfile, generatePlan) {
            if (userProfile.userProfileId == null) {
                userProfile.userId = $scope.user.userId;
                UserProfileService.createProfile(userProfile).then(
                        function (d) {
                            $scope.userProfile = d;
                            self.getEquipments();
                            $scope.calculateIMC();
                            if (generatePlan) {
                                $scope.validatePlan($scope.userProfile);
                            } else {
                                $scope.showMessage("Datos Deportivos creados exitosamente.");
                            }
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
                            $scope.calculateIMC();
                            if (generatePlan) {
                                 $scope.validatePlan($scope.userProfile);
                            } else {
                                $scope.showMessage("Datos Deportivos editados exitosamente.");
                            }

                        },
                        function (errResponse) {
                            console.error('Error while merging the profile');
                            console.error(errResponse);
                        }
                );
            }

            VisibleFieldsUserService.createVisibleFieldsUser(userProfile.userId, $scope.visibleFields).then(
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
            $scope.showAnotherBike = false;
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
                        if (d.data.detail == null) {
                            $location.path("/calendar");
                            $window.location = ("#calendar");
                        } else {
//                            $scope.showMessage("Error al generar el Plan de Entrenamiento. Comunicate con el Administrador ");
                            $scope.showMessage(d.data.detail);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while generating the training plan');
                        console.error(errResponse);
                    }
            );       
        };
        
        $scope.testValidatePlan = function(){
              UserProfileService.validatePlan($scope.user.userId).then(
                    function (d) {
                        if (d != "") {
                              var confirm = $mdDialog.confirm()
                                    .title('Confirmaci\u00f3n')
                                    .textContent('\u00BFDesea generar su Plan de Entrenamiento?' + d)
                                    .ariaLabel('Lucky day')
                                    .ok('Aceptar')
                                    .cancel('Cancelar');

                            $mdDialog.show(confirm).then(function () {
                                console.log("confirmado");
                            }, function () {
                            });
                        } 
                    },
                    function (errResponse) {
                        console.error(errResponse);
                    }  
                    );
        }
        
        $scope.validatePlan = function (userProfile) {
            UserProfileService.validatePlan(userProfile.userId).then(
                    function (d) {
                        if (d != "") {
                            var confirm = $mdDialog.confirm()
                                    .title('Confirmaci\u00f3n')
                                    .textContent('\u00BFDesea generar su Plan de Entrenamiento? ' + d)
                                    .ariaLabel('Lucky day')
                                    .ok('Aceptar')
                                    .cancel('Cancelar');

                            $mdDialog.show(confirm).then(function () {
                                $scope.generatePlan(userProfile);
                            }, function () {
                            });
                        } else {
                            $scope.generatePlan(userProfile);
                        }
                    },
                    function (errResponse) {
                        console.error(errResponse);
                    }
            );
        };


        $scope.confirmDialogGeneratePlan = function (ev, generatePlan, msg) {

            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea generar su Plan de Entrenamiento?' + msg)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                $scope.createOrMergeUserProfile($scope.userProfile, generatePlan);
            }, function () {
            });
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

                        if ($scope.sports != "") {
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

        $scope.getBikes = function (bikeTypeId) {
            SportEquipmentService.getBikesByBikeTypeId(bikeTypeId).then(
                    function (d) {
                        if (d.detail == null) {
                            $scope.bikes = d.output;
                            $scope.bikes.unshift({sportEquipmentId: '', name: 'Seleccione', brand: 'Seleccione'});
                            $scope.bikes.push({sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
                            $scope.getModelsBike($scope.userProfile.bikes);
                        } else {
                            console.log("No se encontraron bicicletas de ese tipo");
                        }
                    },
                    function (errResponse) {
                        console.error('Error while bikes');
                        console.error(errResponse);
                    }
            );
        };

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

        $scope.getObjectivesByDiscipline = function (disciplineId, change) {
            if (change) {
                $scope.userProfile.objective = '';
                $scope.userProfile.modality = '';
            }
            ObjectiveService.getObjectivesByDiscipline(disciplineId).then(
                    function (d) {
                        $scope.objectives = d.output;
                    },
                    function (errResponse) {
                        console.error('Error while getting objectives');
                        console.error(errResponse);
                    }
            );
        };
        this.getObjectives = function () {
            ObjectiveService.getObjectives().then(
                    function (d) {
                        $scope.objectives = d;
                    },
                    function (errResponse) {
                        console.error('Error while objectives');
                        console.error(errResponse);
                    }
            );
        };
//        this.getObjectives();

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

        $scope.getModalitiesByObjectiveId = function (id, change) {
            if (change) {
                $scope.userProfile.modality = '';
            }
            ModalityService.getModalitiesByObjectiveId(id).then(
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

        this.getLesiones = function () {
            SportService.getLesiones().then(
                    function (d) {
                        $scope.lesiones = d;
                    },
                    function (errResponse) {
                        console.error('Error get lesiones');
                        console.error(errResponse);
                    }
            );
        };
        this.getLesiones();

        $scope.getAvailabilityIdx = function (value) {
            var response = $scope.userProfile.availability;
            var res = '';
            angular.forEach(response, function (v, key) {
                if (v == value) {
                    res = key;
                }
            });

            return res;
        };

        $scope.getAvailabilityResponse = function (value) {
            var response = $scope.userProfile.availability;
            var res = false;
            angular.forEach(response, function (v, key) {
                if (v == value) {
                    res = true;
                }
            });

            return res;
        };

        $scope.setAvailabilityResponse = function (value) {
            var response = $scope.userProfile.availability;
            var idx = $scope.getAvailabilityIdx(value);
            if (idx !== "" && response[idx].checked) {
                response[idx].checked = false;
            } else if (idx !== "" && !response[idx].checked) {
                response[idx].checked = true;
            } else if (idx !== "") {
                response = value;
            }
        };

        $scope.getModelsPotentiometer = function (sportEquipmentId) {
            if (sportEquipmentId == -2) { //another potentiometer
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
            if (sportEquipmentId == -2) { //another pulsometer
                $scope.showAnotherPulsometer = true;
                $scope.showModelPulsometer = false;
            } else {
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
            if (sportEquipmentId == -2) { //another pulsometer
                $scope.showAnotherBike = true;
                $scope.showModelBike = false;
            } else {
                $scope.showAnotherBike = false;
                $scope.showModelBike = true;
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
            }
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
            if (value === true) {
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
                $scope.userProfile.ftp56 = ($scope.userProfile.power * 56) / 100;
                $scope.userProfile.ftp75 = ($scope.userProfile.power * 75) / 100;
                $scope.userProfile.ftp76 = ($scope.userProfile.power * 76) / 100;
                $scope.userProfile.ftp90 = ($scope.userProfile.power * 90) / 100;
                $scope.userProfile.ftp91 = ($scope.userProfile.power * 91) / 100;
                $scope.userProfile.ftp105 = ($scope.userProfile.power * 105) / 100;
                $scope.userProfile.ftp106 = ($scope.userProfile.power * 106) / 100;
                $scope.userProfile.ftp120 = ($scope.userProfile.power * 120) / 100;
            }
        };
        $scope.calculatePpm = function () {
            if ($scope.userProfile.ppm !== "") {
                $scope.userProfile.ppm81 = ($scope.userProfile.ppm * 81) / 100;
                $scope.userProfile.ppm89 = ($scope.userProfile.ppm * 89) / 100;
                $scope.userProfile.ppm90 = ($scope.userProfile.ppm * 90) / 100;
                $scope.userProfile.ppm93 = ($scope.userProfile.ppm * 93) / 100;
                $scope.userProfile.ppm94 = ($scope.userProfile.ppm * 94) / 100;
                $scope.userProfile.ppm99 = ($scope.userProfile.ppm * 99) / 100;
                $scope.userProfile.ppm100 = ($scope.userProfile.ppm * 100) / 100;
                $scope.userProfile.ppm102 = ($scope.userProfile.ppm * 102) / 100;
                $scope.userProfile.ppm103 = ($scope.userProfile.ppm * 103) / 100;
                $scope.userProfile.ppm106 = ($scope.userProfile.ppm * 106) / 100;
            }
        };

        $scope.validateAvailability = function () {
            var length = $scope.userProfile.availability.length;
            for (var i = 0; i < length; i++) {
                if ($scope.userProfile.availability[i].checked) {
                    return true;
                }
            }
            return false;
        };

        $scope.validatePpm = function () {

            if ($scope.userProfile.ppm81 > 0 && $scope.userProfile.ppm89 > 0 && $scope.userProfile.ppm90 > 0 && $scope.userProfile.ppm93 > 0
                    && $scope.userProfile.ppm94 > 0 && $scope.userProfile.ppm99 > 0 && $scope.userProfile.ppm100 > 0
                    && $scope.userProfile.ppm102 > 0 && $scope.userProfile.ppm103 > 0 && $scope.userProfile.ppm106 > 0) {
                return true;
            } else if ($scope.userProfile.ppm81 == 0 && $scope.userProfile.ppm89 == 0
                    && $scope.userProfile.ppm90 == 0 && $scope.userProfile.ppm93 == 0
                    && $scope.userProfile.ppm94 == 0 && $scope.userProfile.ppm99 == 0
                    && $scope.userProfile.ppm100 == 0 && $scope.userProfile.ppm102 == 0
                    && $scope.userProfile.ppm103 == 0 && $scope.userProfile.ppm106 == 0) {
                return true;
            }
            return false;
        };

        $scope.validatePower = function () {
            if ($scope.userProfile.ftp56 > 0 && $scope.userProfile.ftp75 > 0 && $scope.userProfile.ftp76 > 0 && $scope.userProfile.ftp90 > 0
                    && $scope.userProfile.ftp91 > 0 && $scope.userProfile.ftp105 > 0 && $scope.userProfile.ftp106 > 0 && $scope.userProfile.ftp120 > 0) {
                return true;
            } else if ($scope.userProfile.ftp56 == 0 && $scope.userProfile.ftp75 == 0 && $scope.userProfile.ftp76 == 0 && $scope.userProfile.ftp90 == 0
                    && $scope.userProfile.ftp91 == 0 && $scope.userProfile.ftp105 == 0 && $scope.userProfile.ftp106 == 0 && $scope.userProfile.ftp120 == 0) {
                return true;
            }
            return false;
        };

        $scope.validateFields = function () {
            var valid = true;
            if ($scope.userProfile.objective == '' || $scope.userProfile.objective == null) {
                $scope.errorMessages.push("Debe seleccionar un objetivo");
                //form.objective.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.discipline == '' || $scope.userProfile.discipline == null) {
                $scope.errorMessages.push("Debe seleccionar una disciplina");
                //form.discipline.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.modality == '' || $scope.userProfile.modality == null) {
                $scope.errorMessages.push("Debe seleccionar una modalidad");
                //form.modality.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.environmentId == '' || $scope.userProfile.environmentId == null) {
                $scope.errorMessages.push("Debe seleccionar un entorno");
                //form.environment.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.weatherId == '' || $scope.userProfile.weatherId == null) {
                $scope.errorMessages.push("Debe seleccionar un clima predominante");
                //form.weather.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.weight == '' || $scope.userProfile.weight == null) {
                //form.weight.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.height == '' || $scope.userProfile.height == null) {
                //form.height.$setTouched();
                valid = false;
            }
            if ($scope.userProfile.ageSport < 0) {
                $scope.errorMessages.push("La edad deportiva debe ser mayor o igual a 0");
                valid = false;
            }
            if ($scope.userProfile.availability != undefined && !$scope.validateAvailability()) {
                $scope.errorMessages.push("La disponiblidad de tiempo es obligatoria ");
                valid = false;
            }
            if (!$scope.validatePpm()) {
                $scope.errorMessages.push("Debe llenar todas las zonas de potencia ");
                valid = false;
            }
            if (!$scope.validatePower()) {
                $scope.errorMessages.push("Debe llenar todas las zonas de ppm ");
                valid = false;
            }
            if ($scope.userProfile.height <= 0) {
                $scope.errorMessages.push("La altura debe ser mayor a cero ");
                valid = false;
            }
            if ($scope.userProfile.weight <= 0) {
                $scope.errorMessages.push("El peso debe ser mayor a cero ");
                valid = false;
            }
            if (!isNumeric($scope.userProfile.weight)) {
                $scope.errorMessages.push("El peso debe ser un n\u00famero, verifique que no tenga comas ");
                valid = false;
            }
            if (!isNumeric($scope.userProfile.height)) {
                $scope.errorMessages.push("La altura debe ser un n\u00famero, verifique que no tenga comas ");
                valid = false;
            }
            return valid;
        };

        $scope.showTooltipEnvironment = function () {
            $scope.showMessage("Indique como es la topograf\u00eda del lugar en el que usted desarrollar\u00e1 las actividades deportivas", "Entorno Geogr\u00e1fico");
        };

        $scope.showTooltipWeather = function () {
            $scope.showMessage("Indique cual es la condici\u00f3n atmosf\u00e9rica del lugar en el que usted desarrollar\u00e1 las actividades deportivas.", "Clima Predominante en sus entrenamientos");
        };

        $scope.showTooltipSportAge = function () {
            $scope.showMessage("Corresponde al tiempo (a\u00f1os) que usted ha dedicado de manera continua a la ejecuci\u00f3n de una modalidad deportiva ", "Edad Deportiva");
        };

        $scope.showTooltipPpm = function () {
            $scope.showMessage("Corresponde a las pulsaciones por minuto. El valor registrado en este campo corresponder\u00e1 al resultante de una prueba de esfuerzo vigente,  para esto,  usted debe realizar una prueba en carretera por un per\u00edodo de 20 min,  en el que deber\u00e1 desplazarse en plano o loma durante el tiempo indicado, lo m\u00e1s r\u00e1pido posible,  con la ayuda del puls\u00f3metro guarde las pulsaciones promedio del tiempo de la prueba, Seg\u00fan teor\u00eda de Cogan", "Ppm");
        };

        $scope.showTooltipPower = function () {
            $scope.showMessage("El valor registrado en este campo corresponder\u00e1 al resultante de una prueba de esfuerzo vigente,  para esto,  usted debe realizar una prueba en carretera por un per\u00edodo de 20 min,  en el que deber\u00e1 desplazarse en plano o loma durante el tiempo indicado, lo m\u00e1s r\u00e1pido posible,  con la ayuda de un potenciometro guarde las pulsaciones promedio del tiempo de la prueba, Seg\u00fan teor\u00eda de Cogan", "Potencia");
        };

        $scope.showTooltipImc = function () {
            $scope.showMessage("El \u00edndice de masa corporal (IMC) es un n\u00famero calculado con base al peso y la altura de nuestro cuerpo. Este \u00edndice es un indicador de la cantidad de grasa corporal. ", "IMC");
        };

        $scope.showTooltipVo2Running = function () {
            $scope.showMessage("Volumen m\u00e1ximo de oxigeno que el organismo es capaz de metabolizar por unidad de tiempo determinado.", "VO2 Max Running");
        };

        $scope.showTooltipVo2Ciclismo = function () {
            $scope.showMessage("Volumen m\u00e1ximo de oxigeno que el organismo es capaz de metabolizar por unidad de tiempo determinado.", "VO2 Max Ciclismo");
        };

        this.getBikeTypes = function () {
            BikeTypeService.getBikeTypes().then(
                    function (d) {
                        $scope.bikeTypes = d;
                    },
                    function (errResponse) {
                        console.error('Error while bike types');
                        console.error(errResponse);
                    }
            );
        };
        this.getBikeTypes();

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
            if (response != null && response.response !== 'undefined' && response.questionOptionId !== "") {
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
                $scope.survey[$parentIndex].userId = $scope.user.userId;
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
                            //console.log($scope.survey);
                            //console.log($scope.categories);
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

    }]);