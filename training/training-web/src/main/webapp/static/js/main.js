require.config({
    baseUrl: '/training/static',
    urlArgs: 'v=1.0'
});

require(
    [    
'js/routeResolver', 
        'js/app',
         
        
//        'js/calendar/controller/calendar_controller'
    ],
    function () {
        angular.bootstrap(document, ['trainingApp']);
    });