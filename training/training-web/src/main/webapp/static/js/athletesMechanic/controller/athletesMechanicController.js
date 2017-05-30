trainingApp.controller('AthletesMechanicController', ['$scope', 'MecanicoService', '$window',
    function ($scope, MecanicoService, $window) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athletes = [];
        $scope.athletesFiltered = [];
        $scope.names = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];


        //$scope.filt = 'A';
        $scope.setFilter = function (letter) {
            $scope.filt = letter;
        };

        $scope.startsWith = function (athlete) {
            var lowerStr = (athlete.fullName + "").toLowerCase();
            var letter = $scope.filt;
            if (letter != undefined) {
                return lowerStr.indexOf(letter.toLowerCase()) === 0;
            } else {
                $scope.athletesFiltered = $scope.athletes;
                return true;
            }
        };

        self.getAthletes = function () {
            MecanicoService.getAthletes().then(
                    function (data) {
                        $scope.athletes = data.output;
                        $scope.athletesFiltered = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };
        $scope.goAthleteHistoryFitting = function (user) { 
            $window.sessionStorage.setItem("userFitting", JSON.stringify(user));
            $scope.go('/athlete-history-fitting/' + user.userId, 2);

        };

        $scope.ignoreAccents = function (item) {
            if (!$scope.search)
                return true;
            var text = removeAccents(item.fullName.toLowerCase());
            var search = removeAccents($scope.search.toLowerCase());
            return text.indexOf(search) > -1;
        };

        function removeAccents(source) {

            var accent = [
                /[\300-\306]/g, /[\340-\346]/g, // A, a
                /[\310-\313]/g, /[\350-\353]/g, // E, e
                /[\314-\317]/g, /[\354-\357]/g, // I, i
                /[\322-\330]/g, /[\362-\370]/g, // O, o
                /[\331-\334]/g, /[\371-\374]/g, // U, u
                /[\321]/g, /[\361]/g, // N, n
                /[\307]/g, /[\347]/g, // C, c
            ],
                    noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

            for (var i = 0; i < accent.length; i++) {
                source = source.replace(accent[i], noaccent[i]);
            }

            return source;

        } // removeAccents

        self.getAthletes();


    }]);