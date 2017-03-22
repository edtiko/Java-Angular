trainingApp.controller('StarTeamController', ['$scope', 'StarTeamService',
    'UserService', 'TrainingPlanService', '$window', function ($scope, StarTeamService,
            UserService, TrainingPlanService,
            $window) {
        $scope.starTeam = {starTeamId: null,
            starUserId: {userId: null, name: ''},
            coachUserId: {userId: null, name: ''},
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.starTeamList = [];
        $scope.starUserList = [];
        $scope.coachUserList = [];
        $scope.count = 0;

        $scope.selected = [];

        $scope.query = {
            order: 'starUserId.name',
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

        $scope.getStarTeamPaginate = function () {
            $scope.promise = StarTeamService.getPaginate($scope.query, function (response) {
                $scope.starTeamList = success(response);

                if ($scope.starTeamList.length > 0) {
                    $scope.count = $scope.starTeamList[0].count;
                }
            }).$promise;
        };

        $scope.getStarUserList = function () {
            StarTeamService.getStarUser(function (response) {
                if (response.data.status == 'fail') {
                    $scope.showMessage(response.data.output);
                } else {
                    $scope.starUserList = response.data.output;
                }
            });
        };
        $scope.getCoachUserList = function () {
            StarTeamService.getCoachUser(function (response) {
                $scope.coachUserList = success(response);
            });
        };


        $scope.createStarTeam = function (starTeam) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                starTeam.userCreate = (user.userId);
            }
            
            StarTeamService.createStarTeam(starTeam)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    starTeam = d.output;
                                    $scope.createStarTeamWordpress(starTeam);
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating StartTeam.');
                            }
                    );
        };

        $scope.createStarTeamWordpress = function (starTeam) {
            var starUserId = starTeam.starUserId.userId;
            UserService.getUserDisciplineById(starUserId).then(
                    function (d) {
                        if (d.status == 'success') {
                            var userDTO = d.output;
                            var discipline = userDTO.disciplineIdExt;
                            if (userDTO.secondName == '') {
                                userDTO.secondName = '';
                            } else {
                                userDTO.secondName = trim(userDTO.secondName) + ' ';
                            }
                            var name = trim(userDTO.firstName) + ' ' + (userDTO.secondName) +
                                    trim(userDTO.lastName);
                            var description = userDTO.aboutMe;
                            var image = userDTO.profilePhoto;
                            var userParam = 'discipline=' + discipline +
                                    '&name=' + name + '&description=' + description +
                                    '&image=' + image;
                            $scope.createStarWordPress(starTeam, userParam);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while creating StartTeam.');
                    }
            );
        };

        $scope.createStarWordPress = function (starTeam, userParam) {
            StarTeamService.createStarWordPress(userParam)
                    .then(
                            function (d) {
                                var response = d.data;

                                if (response.status == 'success') {
                                    var idExt = response.output.name;
                                    var coachUserId = starTeam.coachUserId.userId;
                                    $scope.createCoachTeamWordpress(starTeam, coachUserId, idExt);
                                } else {
                                    if (response.code == -1) {
                                        var idExt = response.output.name;
                                        var coachUserId = starTeam.coachUserId.userId;
                                        $scope.createCoachTeamWordpress(starTeam, coachUserId, idExt);
                                        return;
                                    }
                                    $scope.showMessage('Error al integrar estrella');
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating StartTeam.');
                            }
                    );
        }

        $scope.createCoachTeamWordpress = function (starTeam, coachUserId, starIdExt) {
            UserService.getUserDisciplineById(coachUserId).then(
                    function (d) {
                        if (d.status == 'success') {
                            var userDTO = d.output;
                            if (userDTO.secondName == '') {
                                userDTO.secondName = '';
                            } else {
                                userDTO.secondName = trim(userDTO.secondName) + ' ';
                            }

                            var name = trim(userDTO.firstName) + ' ' +
                                    (userDTO.secondName) +
                                    trim(userDTO.lastName);
                            var description = userDTO.aboutMe;
                            var image = userDTO.profilePhoto;
                            var userParam = 'parentId=' + starIdExt +
                                    '&name=' + name + '&description=' + description +
                                    '&image=' + image + '&starTeamId='+starTeam.starTeamId;
                            $scope.getPlan(starTeam, userParam);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while getting StartTeam.');
                    }
            );
        };

        $scope.getPlan = function (starTeam, userParam) {
            TrainingPlanService.getPlanTypes().then(
                    function (d) {
                        if (d.status == 'success') {
                            var data = d.output;
                            var rcPlan = '', rcPrice = '';

                            for (var i = 0; i < data.length; i++) {
                                var plan = data[i];

                                if (i == (data.length - 1)) {
                                    rcPlan += plan.name;
                                    rcPrice += plan.price;
                                } else {
                                    rcPlan += plan.name + '_';
                                    rcPrice += plan.price + '_';
                                }
                            }

                            userParam = userParam + '&plan=' + rcPlan + '&price=' + rcPrice;
                            $scope.createCoachWordPress(starTeam, userParam);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while getting StartTeam.');
                    }
            );
        };

        $scope.createCoachWordPress = function (starTeam, userParam) {
            StarTeamService.createCoachWordPress(userParam)
                    .then(
                            function (d) {
                                var response = d.data;

                                if (response.status == 'success') {
                                    $scope.showMessage('Registro creado exitosamente');
                                    $scope.resetStarTeam();
                                    $scope.getStarTeamPaginate();
                                } else {
                                    $scope.showMessage('Error al integrar coach');
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating coach.');
                            }
                    );
        }

        $scope.updateStarTeam = function (starTeam) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                starTeam.userUpdate = (user.userId);
            }

            StarTeamService.mergeStarTeam(starTeam)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetStarTeam();
                                    $scope.showMessage(d.output);
                                    $scope.getStarTeamPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating StartTeam.');
                            }
                    );
        };

        $scope.deleteStarTeam = function (starTeam) {
            StarTeamService.deleteStarTeam(starTeam)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetStarTeam();
                                    $scope.showMessage(d.output);
                                    $scope.getStarTeamPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting StartTeam.');
                            }
                    );
        };

        $scope.submitStarTeam = function (form) {
            if (form.$valid) {
                if ($scope.starTeam.starTeamId === null) {
                    $scope.createStarTeam($scope.starTeam);
                } else {
                    $scope.updateStarTeam($scope.starTeam);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editStarTeam = function (id) {
            for (var i = 0; i < $scope.starTeamList.length; i++) {
                if ($scope.starTeamList[i].starTeamId === id) {
                    $scope.starTeam = angular.copy($scope.starTeamList[i]);
                    break;
                }
            }
        };

        $scope.inactivateStarTeam = function (id) {
            for (var i = 0; i < $scope.starTeamList.length; i++) {
                if ($scope.starTeamList[i].starTeamId === id) {
                    $scope.starTeam = angular.copy($scope.starTeamList[i]);
                    break;
                }
            }
            $scope.starTeam.stateId = 0;
            $scope.updateStarTeam($scope.starTeam);
            $scope.resetStarTeam();
        };

        $scope.activateStarTeam = function (id) {
            for (var i = 0; i < $scope.starTeamList.length; i++) {
                if ($scope.starTeamList[i].starTeamId === id) {
                    $scope.starTeam = angular.copy($scope.starTeamList[i]);
                    break;
                }
            }
            $scope.starTeam.stateId = 1;
            $scope.updateStarTeam($scope.starTeam);
        };

        $scope.removeStarTeam = function (id) {
            if ($scope.starTeam.starTeamId === id) {
                $scope.resetStarTeam();
            }
            $scope.deleteStarTeam(id);
        };

        $scope.resetStarTeam = function () {
            $scope.starTeam = {starTeamId: null, starUserId: {userId: null}, startUserName: '',
                coachUserId: {userId: null}, coachUserName: '',
                stateId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        };

        $scope.getStarTeamPaginate();

        $scope.getStarUserList();
        $scope.getCoachUserList();

    }]);