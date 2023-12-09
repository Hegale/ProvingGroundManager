(function(){

    var availableTimes = [[${availableTimes}]];
    var pathArray = window.location.pathname.split('/');
    var carTypeId = pathArray[pathArray.length - 1];  // URL의 마지막 부분이 carTypeId


    $.datepicker.setDefaults({ dateFormat: 'yy-mm-dd' });

    $('.datepicker').datepicker(
        {
            dateFormat: 'yy-mm-dd',
            maxDate: '30',
            minDate: '1',
            onSelect: function(dateText, inst) {
                // 모든 날짜 셀의 배경색을 초기화
                $('.ui-state-active').css('backgroundColor', '');

                // 선택한 날짜 셀의 배경색을 변경
                setTimeout(function() {
                    $('.ui-state-active').css('backgroundColor', '#D8D8D8');
                }, 0);

                $.ajax({
                    url: '/car-rental/select/' + carTypeId + '/date', // 서버에 요청할 URL
                    type: 'get',
                    data: { date: dateText }, // 선택된 날짜를 서버에 전달
                    success: function(data) {
                        // 서버로부터 받은 정보를 html에 표시
                        var timesHtml = data.availableTimes.map(function(time) {
                            return '<label class="time-btn">' +
                                '<input type="radio" name="selectedTime" value="' + time + '">' +
                                '<span>' + time + ':00</span>' +
                                '</label>';
                        }).join('');

                        $('.juykim-timebtnbox').html(timesHtml);
                        // ...
                    },

                    error: function(error) {
                        console.error(error);
                    }
                });

                // 선택한 날짜를 페이지에 출력
                $('#selectedDateDisplay').text(dateText);

                $("#selectedDate").val(dateText);

                $("#userDate").val(dateText);

            }
        });

})();