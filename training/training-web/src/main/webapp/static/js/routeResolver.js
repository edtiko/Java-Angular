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
                routeDef.controller = baseName + 'Controller';
                routeDef.secure = (secure) ? secure : false;
                routeDef.resolve = {
                    load: ['$q', '$rootScope', function ($q, $rootScope) {
                            var dependencies = [routeConfig.getControllersDirectory() + path +
                                        baseName + "/" + 'controller' + "/" + baseName + '_controller.js'];
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
            }
        }(this.routeConfig);

    };

    var routeResolverServices = angular.module('routeResolverServices', []);

    //Must be a provider since it will be injected into module.config()    
    routeResolverServices.provider('routeResolver', routeResolver);
});