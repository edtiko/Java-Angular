// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$http', '$scope', 'AuthService',
    'VisibleFieldsUserService', 'ModuleService', 'ExternalCoachService',
    '$window', '$mdDialog', '$mdToast', '$location', function ($http, $scope,
            AuthService, VisibleFieldsUserService, ModuleService, ExternalCoachService, $window, $mdDialog, $mdToast, $location) {

        var self = this;
        $scope.successTextAlert = "";
        $scope.fields = [];
        $scope.visibleFields = [];
        $scope.appReady = true;
        $scope.userLogin = "";
        $scope.moduleList = [];
        $scope.userSessionTypeUserAtleta = "1";//Atleta
        $scope.userSessionTypeUserCoach = "2";//Coach
        $scope.userSessionTypeUserAdmin = "3";//Admin
        $scope.userSessionTypeUserCoachInterno = "4";//CoachInterno
        $scope.userSessionTypeUserCoachEstrella = "5";//CoachEstrella
        $scope.userSessionTypeUserSupervisor = "6";//Supervisor
        $scope.invitation = null;
        $scope.roleSelected = -1; //-1 No aplica | 5 CoachEstrella | 4 CoachInterno 


        $scope.viewInvitations = function (userId) {
            ExternalCoachService.getInvitation(userId).then(
                    function (response) {
                        if (response != null && response != "") {
                            $scope.invitation = angular.copy(response);
                        } else {
                            $scope.invitation = null;
                        }
                    },
                    function (error) {
                        console.log(error);
                    }

            );

        };

        $scope.switchBool = function (id) {
            var e = angular.element('#' + id);
            e.hide();
            //$scope[value] = !$scope[value];
        };
        $scope.showToast = function (msg) {
            // var pinTo = $scope.getToastPosition();
            $mdToast.show(
                    $mdToast.simple()
                    .textContent(msg)
                    .position("right")
                    .hideDelay(3000)
                    );
        };
        $scope.closeToast = function () {
            $mdToast.hide();
        };

        $scope.showMessage = function (msg, title, html = false) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog

            var titleDefault = 'Confirmaci\u00f3n';
            if (title != "") {
                titleDefault = title;
            }

            if (html) {
                $scope.message = msg;
                $scope.title = title;
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/tmpls/dialogConfirmation.html',
                    parent: angular.element(document.querySelector('#trainingApp')),
                    clickOutsideToClose: true,
                    fullscreen: $scope.customFullscreen,
                    controller: function () {
                        $scope.cancel = function () {
                            $mdDialog.cancel();
                        }
                    }
                });
            } else {

                $mdDialog.show(
                        $mdDialog.alert()
                        .parent(angular.element(document.querySelector('#user-container')))
                        .clickOutsideToClose(true)
                        .title(titleDefault)
                        .textContent(msg)
                        .ariaLabel('Alert Dialog Demo')
                        .ok('Aceptar')
                        //.targetEvent(ev)
                        );
            }
        };

        $scope.showMessageConfirmation = function (msg, title, link) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog

            var titleDefault = 'Confirmaci\u00f3n';
            if (title != "") {
                titleDefault = title;
            }

            $scope.message = msg;
            $scope.title = title;
            $scope.link = link;
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/tmpls/dialogConfirmationLink.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    }
                }
            });
        };

        $scope.goCalendar = function () {
            $window.sessionStorage.setItem("coachAssignedPlanSelected", null);
            $location.path("/calendar");
        };
        $scope.getUserSessionByResponse = function (res) {
            if (res.data.entity.output == null) {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
                return res;
            }

            $scope.appReady = true;
            if (res.data.entity.output.secondName == null || res.data.entity.output.secondName == 'undefined') {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.lastName;
            } else {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.secondName + " " + res.data.entity.output.lastName;
            }
            $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
            $scope.userSession = res.data.entity.output;
            return JSON.parse(sessionStorage.getItem("userInfo"));
        };
        $scope.setUserSession = function () {
            AuthService.setUserSession($scope).then(
                    function (d) {
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
                        console.error(errResponse);
                    }
            );
        };
        $scope.getMenuByUser = function () {
            $http.get($contextPath + '/user/getUserSession')
                    .then(function (res) {
                        var id = res.data.entity.output;
                        $scope.userId = id.userId;
                        $scope.roleName = id.roleName;
                        ModuleService.getModuleByUserId(id.userId).then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.moduleList = d.output;
                                    } else {
                                        $scope.showMessage(d.detail);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while merging the profile');
                                    console.error(errResponse);
                                });


                        $scope.viewInvitations($scope.userId);
                    }, function (errResponse) {
                        console.error('Error while getting ' + errResponse);
                    }
                    );
        };
        $scope.getUserSession = function (fn) {
            $http.get($contextPath + '/user/getUserSession')
                    .then(fn, function (errResponse) {
                        console.error('Error while getting ' + errResponse);
                    });
        };
        $scope.setUserSession();
        $scope.getMenuByUser();

        $scope.getVisibleFieldsUserByUser = function (user) {
            if (user != null) {
                VisibleFieldsUserService.getVisibleFieldsUserByUser(user)
                        .then(
                                function (response) {
                                    $scope.fields = response;
                                    for (var i = 0; i < $scope.fields.length; i++) {
                                        $scope[$scope.fields[i].tableName + $scope.fields[i].columnName] = true;
                                        if (!$scope.inFieldsArray({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId}, $scope.visibleFields)) {

                                            $scope.visibleFields.push({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId});
                                        }
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Visible Fields');
                                    console.error(errResponse);
                                }
                        );
            }
        };
        $scope.inFieldsArray = function (field, array) {
            var length = array.length;
            for (var i = 0; i < length; i++) {
                if (array[i].tableName == field.tableName && array[i].columnName == field.columnName)
                    return true;
            }
            return false;
        };
        $scope.calculateAge = function (birthday) { // birthday is a date
            if (birthday != null) {
                var ageDifMs = Date.now() - birthday.getTime();
                var ageDate = new Date(ageDifMs); // miliseconds from epoch
                return Math.abs(ageDate.getUTCFullYear() - 1970);
            }
        };
        $scope.logout = function () {
            window.location = $wordPressContextPath+'mi-cuenta/customer-logout/';
        };

        $scope.viewInvitationDialog = function () {
            $mdDialog.show({
                controller: InvitationController,
                scope: $scope.$new(),
                templateUrl: 'static/views/externalCoach/invitation.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen
            });
        };

        function InvitationController($scope, $mdDialog) {

            $scope.acceptInvitation = function () {

                ExternalCoachService.acceptInvitation($scope.invitation.id).then(
                        function (data) {
                            $scope.hide();
                            $scope.invitation = null;
                            $scope.viewInvitations($scope.userId);
                        },
                        function (error) {

                        }
                );
            };

            $scope.rejectInvitation = function () {

                ExternalCoachService.rejectInvitation($scope.invitation.id).then(
                        function (data) {
                            $scope.hide();
                            $scope.invitation = null;
                            $scope.viewInvitations($scope.userId);
                        },
                        function (error) {

                        }
                );
            };

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };


        }


        $scope.offPlanDialog = function (ev) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmar')
                    .textContent('\u00BFEsta seguro de salir del plan con el Coach: ' + $scope.planSelected.coachUserId.fullName + '?')
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Salir del plan')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                self.desactivarPlanActivo();
            }, function () {
                $scope.status = 'You decided to keep on the plan';
            });
        };

        self.desactivarPlanActivo = function () {
            if ($scope.planSelected != null) {
                ExternalCoachService.removeAthleteCoach($scope.planSelected.id)
                        .then(
                                function (response) {
                                    if (response.entity.status == 'success') {
                                        window.location.reload();
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting User.');
                                }
                        );
            }
        };



        var mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        var mediaRecorder;
        var recordedBlobs;
        var sourceBuffer;
        $scope.mediaModel = null;

        var constraints = {
            audio: true,
            video: true
        };

        function handleSuccess(stream) {
            console.log('getUserMedia() got stream: ', stream);
            window.stream = stream;
            if (window.URL) {
                $scope.gumVideo.src = window.URL.createObjectURL(stream);
            } else {
                $scope.gumVideo.src = stream;
            }
        }

        function handleError(error) {
            console.log('navigator.getUserMedia error: ', error);
        }

        function handleSourceOpen(event) {
            console.log('MediaSource opened');
            sourceBuffer = mediaSource.addSourceBuffer('video/webm; codecs="vp8"');
            console.log('Source buffer: ', sourceBuffer);
        }



        function handleDataAvailable(event) {
            if (event.data && event.data.size > 0) {
                recordedBlobs.push(event.data);
                 $scope.mediaModel = event.data;
            }
        }

        function handleStop(event) {
            console.log('Recorder stopped: ', event);
        }

        $scope.recordedVideo = '';
        $scope.gumVideo = '';
        $scope.mediaRecorder = '';

        $scope.initVideo = function (recordedVideo, gumVideo) {
            if (gumVideo != undefined && gumVideo != '') {
                $scope.gumVideo = document.querySelector('video#' + gumVideo);
            }

            $scope.recordedVideo = document.querySelector('video#' + recordedVideo);
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

            $scope.recordedVideo.addEventListener('error', function (ev) {
                console.error('MediaRecording.recordedMedia.error()');
                alert('Error al reproducir video');
                console.error('Your browser can not play\n\n' + $scope.recordedVideo.src
                        + '\n\n media clip. event: ' + JSON.stringify(ev));
            }, true);
        };
        // The nested try blocks will be simplified when Chrome 47 moves to Stable
        $scope.startRecordingVideo = function () {
            $scope.mediaRecorder = '';
            if ($scope.mediaRecorder.state == undefined) {
                $scope.gumVideo.controls = false;
                recordedBlobs = [];
                var options = {mimeType: 'video/webm;codecs=vp9'};
                if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                    console.log(options.mimeType + ' is not Supported');
                    options = {mimeType: 'video/webm;codecs=vp8'};
                    if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                        console.log(options.mimeType + ' is not Supported');
                        options = {mimeType: 'video/webm'};
                        if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                            console.log(options.mimeType + ' is not Supported');
                            options = {mimeType: ''};
                        }
                    }
                }
                try {
                    $scope.mediaRecorder = new MediaRecorder(window.stream, options);
                } catch (e) {
                    console.error('Exception while creating MediaRecorder: ' + e);
                    alert('Exception while creating MediaRecorder: '
                            + e + '. mimeType: ' + options.mimeType);
                    return;
                }
                $scope.mediaRecorder.currentTime = 0;
                $scope.mediaRecorder.onstop = handleStop;
                $scope.mediaRecorder.ondataavailable = handleDataAvailable;
                $scope.mediaRecorder.start(0); // collect 10ms of data
            } else {
                $scope.mediaRecorder.start();
            }

        };

        $scope.stopRecordingVideo = function () {
            if ($scope.mediaRecorder.state != 'inactive' && $scope.mediaRecorder.state != undefined) {
                $scope.gumVideo.controls = false;
                $scope.mediaRecorder.stop();
            } else {
                $scope.recordedVideo.pause();
            }
        };

        $scope.cleanVideo = function () {
            $scope.gumVideo = document.querySelector('video#gumVideo');
            document.getElementById('gumVideo').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideo.src = '';
            window.stream = null;
            $scope.mediaModel = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

        };

        $scope.playVideo = function (path) {
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoLocal = function () {
            var superBuffer = new Blob(recordedBlobs, {type: 'video/webm'});
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = window.URL.createObjectURL(superBuffer);
        };

        $scope.playVideoRecorded = function () {
            $scope.recordedVideo.play();
        };

        $scope.savePlanVideo = function (url, fnResponse) {
            $scope.gumVideo.controls = false;
            if ($scope.mediaRecorder.state != 'inactive') {
                $scope.mediaRecorder.stop();
            }

            var blob = new Blob(recordedBlobs, {type: 'video/webm'});
            var fd = new FormData();
            fd.append("fileToUpload", blob);

            $http.post(url, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                    .then(
                            fnResponse,
                            function (errResponse) {
                                console.error('Error while getting ' + errResponse);
                            }
                    );
        };

        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

    }]);
trainingApp.directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value);
            });
        }
    };
});
trainingApp.directive('schrollBottom', function () {
    return {
        scope: {
            schrollBottom: "="
        },
        link: function (scope, element) {
            scope.$watchCollection('schrollBottom', function (newValue) {
                if (newValue)
                {
                    $(element).scrollTop($(element)[0].scrollHeight);
                }
            });
        }
    };
});
function getDate() {
    var d = new Date();
    var ano = d.getFullYear();
    var mes = (d.getMonth() + 1);
    var dia = d.getDate();
    if (mes < 10) {
        mes = '0' + mes;
    }

    if (dia < 10) {
        dia = '0' + dia;
    }

    var fechaRecibo = ano + '-' + mes + '-' + dia;
    return fechaRecibo;
}

/**
 * Elimina espacios
 * @param {type} cadena
 * @returns {String}
 */
function trim(cadena) {
    return cadena.replace(/^\s+|\s+$/gm, '');
}