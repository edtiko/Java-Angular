(function ($) {
    var user = JSON.parse(sessionStorage.getItem("userInfo"));
    "use strict";
    var options = {
        language: 'es-CO',
        events_source: 'trainingPlanWorkout/get/planWorkout/by/user/'+user.userId,
        view: 'month',
        modal: "#events-modal",
        modal_type: 'template',
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
            $('.page-header h3').text(this.getTitle());
            $('.btn-group button').removeClass('active');
            $('button[data-calendar-view="' + view + '"]').addClass('active');
        },
        classes: {
            months: {
                general: 'label'
            }
        }
    };

    var calendar = $('#calendar').calendar(options);

    $('.btn-group button[data-calendar-nav]').each(function () {
        var $this = $(this);
        $this.click(function () {
            calendar.navigate($this.data('calendar-nav'));
        });
    });

    $('*[data-add-event]').click(function () {
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
    });

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

    $('#events-in-modal').click(function () {
        calendar.setOptions({modal: "#events-modal"});
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