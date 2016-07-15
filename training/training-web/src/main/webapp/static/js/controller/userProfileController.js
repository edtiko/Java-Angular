'use strict';

trainingApp.controller('UserProfileController', ['$scope', 'UserProfileService',function ($scope, UserProfileService) {
        
        $scope.userProfile = {
            userProfileId:null,
            indPulsometer: '',
            indPower: '',
            ageSport: '',
            ppm: '12',
            power:'',
            sportsAchievements: 'asdf',
            aboutMe:''
        };
        
        this.findById = function (userProfile) {
            UserProfileService.getProfile(userProfile).then(
                    function (d) {
                        this.userProfile = d;
                    },
                    function (errResponse) {
                        console.log('user Controller '+userProfile.userProfileId);
                        console.error('Error while fetching the profile');
                        console.error(errResponse);
                    }
            );
        };

    }]);
