// dateTimePicker
$(function(){
    $('#date_picker').dtpicker();
});

$(function(){
    $('*[name=date]').appendDtpicker();
});
$('*[name=date]').appendDtpicker({

    // current date/time
    "current": null,

    // e.g. DD.MM.YY H:mmTT
    "dateFormat": "default",

    /* Available languages:
    "en"
    "et"
    "br"
    "cn"
    "cz"
    "da"
    "de"
    "es"
    "fr"
    "gr"
    "hu"
    "id"
    "it"
    "ja"
    "ko"
    "nl"
    "no"
    "pt"
    "pl"
    "ro"
    "ru"
    "sv"
    "tr"
    "ua"
    */
    "locale": "en",

    "animation": true,

    "minuteInterval": 90,

    // 0 = Sunday
    "firstDayOfWeek": 0,

    // closes the calendar after selection
    "closeOnSelected": false,

    // enable/disable auto scroll
    // "timelist<a href="https://www.jqueryscript.net/tags.php?/Scroll/">Scroll</a>": true,

    "calendarMouseScroll": true,

    "todayButton": true,

    "closeButton": true,

    "dateOnly": false,

    "timeOnly": false,

    "futureOnly": true,

    "minDate": null,
    "maxDate": null,

    "autodateOnStart": true,

    "minTime": "00:00",
    "maxTime": "23:59",

    "allowWdays": null,

    "amPmInTimeList": false,

    "externalLocale": null

});

$('*[name=date]').appendDtpicker({

    "onShow": function(){
        // on show
    },

    "onHide":  function(){
        // on hide
    },

    "onSelect":  function(){
        // on select
    }

});

// shows the date picker
$('*[name=date]').handleDtpicker('show');

// hides the date picker
$('*[name=date]').handleDtpicker('hide');

// gets the selection
$('*[name=date]').handleDtpicker('getDate');

// set the date
$('*[name=date]').handleDtpicker('setDate', new Date(2019, 0o6, 11, 0, 0, 0));

// destroys the date picker
$('*[name=date]').handleDtpicker('destroy');