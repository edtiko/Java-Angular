trainingApp.controller('StarTeamController', ['$scope', 'StarTeamService',
    'UserService', '$window', function ($scope, StarTeamService, UserService,
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
                $scope.starUserList = success(response);
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
            $scope.createStarTeamWordpress(starTeam);
//            StarTeamService.createStarTeam(starTeam)
//                    .then(
//                            function (d) {
//                                if (d.status == 'success') {
//                                    $scope.createStarTeamWordpress(starTeam);
//                                    $scope.showMessage(d.output);
//                                    $scope.resetStarTeam();
//                                    $scope.getStarTeamPaginate();
//                                } else {
//                                    $scope.showMessage(d.output);
//                                }
//                            },
//                            function (errResponse) {
//                                console.error('Error while creating StartTeam.');
//                            }
//                    );
        };

        $scope.createStarTeamWordpress = function (starTeam) {
            var starUserId = starTeam.starUserId.userId;
            UserService.getUserDisciplineById(starUserId).then(
                    function (d) {
console.debug(d);
                        if (d.status == 'success') {
                            var userDTO = d.output;
                            var discipline = userDTO.disciplineIdExt;
                            var name = userDTO.firstName + ' ' + userDTO.secondName + ' ' + userDTO.lastName;
                            var description = userDTO.aboutMe;
                            var image = userDTO.profilePhoto;
                            
                            console.debug(userDTO);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while creating StartTeam.');
                    }
            );

//            StarTeamService.createStarTeam(starTeam)
//                    .then(
//                            function (d) {
//                                if (d.status == 'success') {
//                                    $scope.showMessage(d.output);
//                                    $scope.resetStarTeam();
//                                    $scope.getStarTeamPaginate();
//                                } else {
//                                    $scope.showMessage(d.output);
//                                }
//                            },
//                            function (errResponse) {
//                                console.error('Error while creating StartTeam.');
//                            }
//                    );
        };

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