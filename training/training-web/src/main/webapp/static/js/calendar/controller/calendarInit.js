var calendar;
(function ($) {
    calendar = initCalendar(true);

    $('.btn-group button[data-calendar-nav]').each(function () {
        var $this = $(this);
        $this.click(function () {
            calendar.navigate($this.data('calendar-nav'));
        });
    });

   /* $('*[data-add-event]').click(function () {
        $.ajax({
            url: 'static/tmpls/add-event.html',
            dataType: 'html',
            type: 'GET',
            async: false
        }).done(function (html) {
            $('#add-events-modal-body').html((html));
            $('#add-events-modal').modal();
            compileAngularElement('#add-events-modal-body');
        });
    });*/
        
    $('.btn-group button[data-calendar-view]').each(function () {
        var $this = $(this);
        $this.click(function () {
            calendar.view($this.data('calendar-view'));
        });
    });

    $('#first_day').change(function () {
        var value = $(this).val();
        value = value.length ? parseInt(value) : null;
        calendar.setOptions({first_day: value});
        calendar.view();
    });

    $('#language').change(function () {
        calendar.setLanguage($(this).val());
        calendar.view();
    });

    $('#format-12-hours').change(function () {
        var val = $(this).is(':checked') ? true : false;
        calendar.setOptions({format12: val});
        calendar.view();
    });
    $('#show_wbn').change(function () {
        var val = $(this).is(':checked') ? true : false;
        calendar.setOptions({display_week_numbers: val});
        calendar.view();
    });
    $('#show_wb').change(function () {
        var val = $(this).is(':checked') ? true : false;
        calendar.setOptions({weekbox: val});
        calendar.view();
    });
    $('#events-modal .modal-header, #events-modal .modal-footer').click(function (e) {
//		e.preventDefault();
//		e.stopPropagation();
    });
}(jQuery));

   function modalActivity (ev) {
       console.log(ev);
        var manual = $(ev.target).attr("data-manual-activity");
        var id = $(ev.target).attr("data-activity-id");
        if (manual == 'true') {
            $('#events-modal').modal('hide');
            var scope = angular.element($("#calendar")).scope();
            scope.$apply(function () {
               scope.showEditManualActivity(id); 
            });
        }
    };

function initCalendar(isNew) {
    var userSession = JSON.parse(sessionStorage.getItem("userInfo"));
    var planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
    var calendarUserId = userSession.userId;
    if (planSelected != null && userSession.typeUser == '4' ) { //Si el usuario de sesion es asesor, carga el calendario con el atleta seleccionado
        if(planSelected.athleteUserId != undefined) {
            calendarUserId = planSelected.athleteUserId.userId;
        }
    }
     
    //"use strict";
    var options = {
        language: 'es-CO',
        events_source: 'trainingPlanWorkout/get/planWorkout/by/user/' + calendarUserId,
        view: 'month',
        //modal: "#events-modal",
        //modal_type: 'template',
        tmpl_path: 'static/tmpls/',
        tmpl_cache: false,
        day: getDate(),
        onAfterEventsLoad: function (events) {
            if (!events) {
                return;
            }
            var list = $('#eventlist');
            list.html('');

            $.each(events, function (key, val) {
                $(document.createElement('li'))
                        .html('<a>' + val.title + '</a>')
                        .appendTo(list);
            });
        },
        onAfterViewLoad: function (view) {
            $('#title_date').text(this.getTitle());
            $('.btn-group button').removeClass('active');
            $('button[data-calendar-view="' + view + '"]').addClass('active');
        },
        classes: {
            months: {
                general: 'label'
            }
        }
    };
    
    if(isNew) {
        calendar = $('#calendar').calendar(options);
    } else {
        calendar.view();
    }
    
    return calendar;
}
function manualActivity(ev){
        var scope =  angular.element($("#calendar")).scope();
          scope.$apply(function(){
        scope.showCreateManualActivity(ev);
    });    
}

function replaceActivity(ev){
        var scope =  angular.element($("#calendar")).scope();
          scope.$apply(function(){
        scope.showCreateReplaceActivity(ev);
    });    
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
    //ev.dataTransfer.setData("manualActivityId", $(ev.target).attr("data-manual-activity"));
}

function drop(ev) {
    ev.preventDefault();
    var user = JSON.parse(sessionStorage.getItem("userInfo"));
    var planAthleteSelected = JSON.parse(sessionStorage.getItem("planSelected"));
    var userId = user.userId;
    if (planAthleteSelected != null) {
        userId = planAthleteSelected.athleteUserId.userId;
    }
    
    var data = ev.dataTransfer.getData("text");
    //var manualActivityId = ev.dataTransfer.getData("manualActivityId");

    if(data != undefined && (data.indexOf('cal') !== -1 || data.indexOf('act') !== -1 || data.indexOf('ma') !== -1)) {
        var rcData = data.split('_');
         var activityId = '';
         var manualActivityId = '';
        if(data.indexOf('act') !== -1){
           activityId = rcData[1];
        }
        else if(data.indexOf('ma') !== -1){
           manualActivityId =  rcData[1];
        }
        var date = $(ev.target).attr("data-event-date");
        var objActivity = {'userId' : userId, 'activityId' : activityId, 'manualActivityId':manualActivityId, 'activityDate' : date};
      
        if(data.indexOf('act') !== -1 || data.indexOf('ma') !== -1) {
            createActivity(objActivity);
        } else if(data.indexOf('cal') !== -1) {
            var date = $(ev.target).attr("data-event-date");
            objActivity = {'userId' : userId, 'trainingPlanWorkoutId' : rcData[1], 'activityDate' : date};
            var dataEventId = $(ev.target).attr("data-event-id");
             console.log("aqui"+dataEventId);
            if(dataEventId != undefined && dataEventId.indexOf('cal') !== -1) {
                createPlan(objActivity, true);
            } else {
                deletePlan(objActivity);
            }
        }
    }
}

function createActivity(objActivity) {
    $.ajax({
            url: $contextPath + 'trainingPlanWorkout/create',
            contentType: "application/json; charset=utf-8",
            type: 'POST',
            data: JSON.stringify(objActivity)
        }).success(function (html) {
            initCalendar(false);
        }).error(function (html) {
            console.debug(html);
        });
}

function createPlan(objActivity, isDetelePlan) {
    $.ajax({
            url: $contextPath + 'trainingPlanWorkout/createPlan',
            contentType: "application/json; charset=utf-8",
            type: 'POST',
            data: JSON.stringify(objActivity)
        }).success(function (html) {
            if(isDetelePlan) {
                //console.log("-->"+objActivity['manualActivityId'])
                deletePlan(objActivity);
            } else {
                initCalendar(false);
            }
        }).error(function (html) {
            console.debug(html);
        });
}

function deletePlan(objActivity) {
    $.ajax({
            url: $contextPath + 'trainingPlanWorkout/delete',
            contentType: "application/json; charset=utf-8",
            type: 'POST',
            data: JSON.stringify(objActivity)
        }).success(function (html) {
            initCalendar(false);
        }).error(function (html) {
            console.debug(html);
        });
}

function getManualActivity(id){
    
        $.ajax({
        url: $contextPath + 'get/activity/id/' + id,
        contentType: "application/json; charset=utf-8",
        type: 'GET',
    }).success(function (data) {
         createManualActivity(data);
    }).error(function (html) {
        console.debug(html);
    });
}

function createManualActivity(objActivity) {
    $.ajax({
            url: $contextPath + 'create/manual/activity',
            contentType: "application/json; charset=utf-8",
            type: 'POST',
            data: JSON.stringify(objActivity)
        }).success(function (html) {
            initCalendar(false);
        }).error(function (html) {
            console.debug(html);
        });
}

function deleteManualActivity(id) {
    $.ajax({
        url: $contextPath + 'delete/manual/activity/' + id,
        contentType: "application/json; charset=utf-8",
        type: 'GET',
    }).success(function (html) {
        initCalendar(false);
    }).error(function (html) {
        console.debug(html);
    });
}