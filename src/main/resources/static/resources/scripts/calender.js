document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        height:850,
        expandRows: true,
        initialView : 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
        headerToolbar : { // 헤더에 표시할 툴 바
            start : 'prev next today',
            center : 'title',
            end : 'dayGridMonth,dayGridWeek,dayGridDay,listWeek'
        },
        titleFormat : function(date) {
            return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
        },
        events: [
            {
                title: 'All Day Event',
                start: '2023-12-09'
            },
            {
                title: 'Long Event',
                start: '2016-12-31',
                end: '2023-12-31'
            },
            {
                title: 'Conference',
                start: '2016-12-11',
                end: '2016-12-13'
            },
            {
                title: 'Meeting',
                start: '2023-12-12T10:30:00',
                end: '2023-12-12T12:30:00'
            },
            {
                title: 'Lunch',
                start: '2023-12-12T12:00:00'
            },
            {
                title: 'Meeting',
                start: '2023-12-12T14:30:00'
            },
            {
                title: 'Happy Hour',
                start: '2016-12-12T17:30:00'
            },
            {
                title: 'Dinner',
                start: '2023-12-12T20:00:00'
            },
            {
                title: 'Birthday Party',
                start: '2023-12-12T07:00:00'
            },
            {
                title: 'Click for Google',
                url: 'https://google.com/',
                start: '2023-12-28'
            }
        ],
        //initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
        selectable : true, // 달력 일자 드래그 설정가능
        droppable : true,
        editable : true,
        dayMaxEvents: true,
        nowIndicator: true, // 현재 시간 마크
        locale: 'ko' // 한국어 설정
    });
    calendar.render();
});
