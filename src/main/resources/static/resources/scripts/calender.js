const addSchedule = document.getElementById('addSchedule');
addSchedule.show = () =>{
    addSchedule.classList.add('observable');
}
addSchedule.remove = () =>{
    addSchedule.classList.remove('observable');
}

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        height:850,
        expandRows: true,
        initialView : 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
        headerToolbar : { // 헤더에 표시할 툴 바
            start : 'prev next addEventButton',
            center : 'title',
            end: 'dayGridMonth,dayGridWeek,dayGridDay,listWeek'
        },
        titleFormat: function (date) {
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
        customButtons: {
            addEventButton: { // 추가한 버튼 설정
                text: "일정 추가",  // 버튼 내용
                click: function () { // 버튼 클릭 시 이벤트 추가
                    // Show the modal
                   addSchedule.show();

                    // Set the onsubmit handler
                    addSchedule.onsubmit = e => {
                        var content = addSchedule.querySelector('.calendar_content').value;
                        var start_date = addSchedule.querySelector('.calendar_start_date').value;
                        var end_date = addSchedule.querySelector('.calendar_end_date').value;

                        if (content == null || content === "") {
                            alert("내용을 입력하세요.");
                        } else if (start_date === "" || end_date === "") {
                            alert("날짜를 입력하세요.");
                        } else if (new Date(end_date) - new Date(start_date) < 0) {
                            alert("종료일이 시작일보다 먼저입니다.");
                        } else { // 정상적인 입력 시
                            var obj = {
                                "title": content,
                                "start": start_date,
                                "end": end_date
                            }; // 전송할 객체 생성

                            // console.log(obj); // 서버로 해당 객체를 전달해서 DB 연동 가능
                            //
                            // // Hide the modal after processing the form data
                            // $('#addSchedule').modal('hide');
                        }
                    };
                }
            }
        },
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

// document.addEventListener('DOMContentLoaded', function () {
//     var calendarEl = document.getElementById('calendar');
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         timeZone: 'UTC',
//         initialView: 'dayGridMonth', // 홈페이지에서 다른 형태의 view를 확인할  수 있다.
//         events:[ // 일정 데이터 추가 , DB의 event를 가져오려면 JSON 형식으로 변환해 events에 넣어주면된다.
//             {
//                 title:'일정DD',
//                 start:'2021-05-26 00:00:00',
//                 end:'2021-05-27 24:00:00'
//                 // color 값을 추가해 색상도 변경 가능 자세한 내용은 하단의 사이트 참조
//             }
//         ], headerToolbar: {
//             center: 'addEventButton' // headerToolbar에 버튼을 추가
//         }, customButtons: {
//             addEventButton: { // 추가한 버튼 설정
//                 text : "일정 추가",  // 버튼 내용
//                 click : function(){ // 버튼 클릭 시 이벤트 추가
//                     $("#calendarModal").modal("show"); // modal 나타내기
//
//                     $("#addCalendar").on("click",function(){  // modal의 추가 버튼 클릭 시
//                         var content = $("#calendar_content").val();
//                         var start_date = $("#calendar_start_date").val();
//                         var end_date = $("#calendar_end_date").val();
//
//                         //내용 입력 여부 확인
//                         if(content == null || content == ""){
//                             alert("내용을 입력하세요.");
//                         }else if(start_date == "" || end_date ==""){
//                             alert("날짜를 입력하세요.");
//                         }else if(new Date(end_date)- new Date(start_date) < 0){ // date 타입으로 변경 후 확인
//                             alert("종료일이 시작일보다 먼저입니다.");
//                         }else{ // 정상적인 입력 시
//                             var obj = {
//                                 "title" : content,
//                                 "start" : start_date,
//                                 "end" : end_date
//                             }//전송할 객체 생성
//
//                             console.log(obj); //서버로 해당 객체를 전달해서 DB 연동 가능
//                         }
//                     });
//                 }
//             }
//         },
//         editable: true, // false로 변경 시 draggable 작동 x
//         displayEventTime: false // 시간 표시 x
//     });
//     calendar.render();
// });
