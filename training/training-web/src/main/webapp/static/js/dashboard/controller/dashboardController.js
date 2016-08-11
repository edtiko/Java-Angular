'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService','DashboardService',
    function ($scope, UserService, DashboardService) {
        $scope.user = {userId: null, name: '', lastName: '', email: '', sex: '',
            weight: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '',discipline: '', sport: '', shoes: '', bikes: '',potentiometer: '',
            modelPotentiometer:'', pulsometer: '', modelPulsometer: '', objective: '', modality: '',
            availability: ''
        };
        $scope.profileImage = "static/img/profile-default.png";
        
        
        $scope.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse(sessionStorage.getItem("userInfo"));
                DashboardService.getDashboard(user).then(
                        function (d) {
                            $scope.user = d;
                            $scope.getImageProfile(user.userId);
                        },
                        function (errResponse) {
                            console.error('Error while fetching the dashboard');
                            console.error(errResponse);
                        }
                );
            } else {
                $scope.showMessage("El usuario no se encuentra logueado.", "error");
            }
        };

        $scope.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.profileImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.profileImage = "static/img/profile-default.png";
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                    console.error(errResponse);
                                }
                        );
            }
        };

        $scope.visibleField = function (tableName, columnName) {
            if(columnName != null) {
                for (var i = 0; i < $scope.fields.length; i++) {
                    if ($scope.fields[i].tableName == tableName && $scope.fields[i].columnName == columnName) {
                        return true;
                    }
                }
            } else {
                for (var i = 0; i < $scope.fields.length; i++) {
                    if ($scope.fields[i].tableName == tableName) {
                        return true;
                    }
                }
            }
            return false;
        };
        $scope.getUserById();
        

    }]);
