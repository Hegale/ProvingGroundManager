// 현재 날짜와 다음 달의 날짜를 계산
var start = moment().subtract(1, 'month').startOf('month'); // 지난 달의 첫째 날
var end = moment().endOf('month'); // 이번 달의 마지막 날

$(function(){
    // 폼에서 날짜 값 읽기
    var storedStartDate = $('#startDate').val();
    var storedEndDate = $('#endDate').val();

    // 저장된 값이 있으면 사용하고, 없으면 기본값 설정
    var start = storedStartDate ? moment(storedStartDate) : moment().subtract(1, 'month').startOf('month');
    var end = storedEndDate ? moment(storedEndDate) : moment().endOf('month');

    // Date Range Picker 초기화
    $('#dateRange').daterangepicker({
        locale: { format: 'YYYY-MM-DD' },
        startDate: start,
        endDate: end
    }, function(start, end, label) {
        $('#startDate').val(start.format('YYYY-MM-DD'));
        $('#endDate').val(end.format('YYYY-MM-DD'));
    });

    // 폼 필드에 초기 날짜 설정
    if (!storedStartDate) $('#startDate').val(start.format('YYYY-MM-DD'));
    if (!storedEndDate) $('#endDate').val(end.format('YYYY-MM-DD'));
});