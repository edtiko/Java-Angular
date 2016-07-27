'use strict';

define([], function () {

    var routeResolver = function () {

        this.$get = function () {
            return this;
        };

        this.routeConfig = function () {
            var viewsDirectory = '/training/static/views/',
                    controllersDirectory = '/training/static/js/',
                    setBaseDirectories = function (viewsDir, controllersDir) {
                        viewsDirectory = viewsDir;
                        controllersDirectory = controllersDir;
                    },
                    getViewsDirectory = function () {
                        return viewsDirectory;
                    },
                    getControllersDirectory = function () {
                        return controllersDirectory;
                    };

            return {
                setBaseDirectories: setBaseDirectories,
                getControllersDirectory: getControllersDirectory,
                getViewsDirectory: getViewsDirectory
            };
        }();

        this.route = function (routeConfig) {

            var resolve = function (baseName, path, secure) {
                if (!path)
                    path = '';

                var routeDef = {};
                var indexBase = baseName.indexOf("-");
                var baseController = baseName.replace("-", "");
                var baseNameController = baseController && baseController[0].toUpperCase() + baseController.slice(1);
                
                
                if(indexBase > 0) {
                    baseNameController = baseNameController.slice(0, indexBase) + baseNameController[indexBase].toUpperCase() + baseNameController.slice(indexBase+1);
                    baseController = baseController.slice(0, indexBase) + baseController[indexBase].toUpperCase() + baseController.slice(indexBase+1);
                }
                
                routeDef.controller = baseNameController + 'Controller';
                routeDef.secure = (secure) ? secure : false;
                routeDef.resolve = {
                    load: ['$q', '$rootScope', function ($q, $rootScope) {
                            var dependencies = [routeConfig.getControllersDirectory() + path +
                                        "/" + 'controller' + "/" + baseController + 'Controller.js'];
                            return resolveDependencies($q, $rootScope, dependencies);
                        }]
                };
                routeDef.templateUrl = routeConfig.getViewsDirectory() + path + baseName + '.html';

                return routeDef;

            },
                    resolveDependencies = function ($q, $rootScope, dependencies) {
                        var defer = $q.defer();

                        require(dependencies, function () {
                            defer.resolve();
                            $rootScope.$apply();
                        });

                        return defer.promise;
                    };

            return {
                resolve: resolve
            };
        }(this.routeConfig);

    };

    var routeResolverServices = angular.module('routeResolverServices', []);

    //Must be a provider since it will be injected into module.config()    
    routeResolverServices.provider('routeResolver', routeResolver);
});