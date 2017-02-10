trainingApp.service('sharedProperties', function () {
    var profileImage = "static/img/profile-default.png";

    var setProfileImage = function (newObj) {
        profileImage = newObj;
    };

    var getProfileImage = function () {
        return profileImage;
    };

    return {
        setProfileImage: setProfileImage,
        getProfileImage: getProfileImage
    };

});