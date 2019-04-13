$(function() {
    //获得从后端传回到页面的数据
    var resYear = $("#select-year option:eq(0)").val();
    var resMonth = $("#select-month option:eq(0)").val();
    var resDay = $("#select-day option:eq(0)").val();

    //创建年,从今年到之前100年,2018->1918
    var date = new Date;
    var optionYear = nowYear = date.getFullYear();
    while (nowYear - optionYear <= 100) {
        if (optionYear == resYear)
            var $optionYear = $('<option value=' + optionYear + ' selected>' + optionYear + '</option>');
        else
            var $optionYear = $('<option value=' + optionYear + '>' + optionYear + '</option>');
        $("#select-year").append($optionYear);
        optionYear--;
    }
    $("#select-year option:eq(0)").val("");

    //创建月,1->12
    var optionMonth = 1;
    while (optionMonth <= 12) {
        if (optionMonth == resMonth)
            var $optionMonth = $('<option value=' + optionMonth + ' selected>' + optionMonth + '</option>');
        else
            var $optionMonth = $('<option value=' + optionMonth + ' th:value="${birthdayMonth}">' + optionMonth + '</option>');
        $("#select-month").append($optionMonth);
        optionMonth++;
    }
    $("#select-month option:eq(0)").val("");

    //创建日,1->31,之后的29,30,31由js判定是否删除
    var optionDay = 1;
    while (optionDay <= 31) {
        if (optionDay == resDay)
            var $optionDay = $('<option value=' + optionDay + ' selected>' + optionDay + '</option>');
        else
            var $optionDay = $('<option value=' + optionDay + '>' + optionDay + '</option>');
        $("#select-day").append($optionDay);
        optionDay++;
    }
    $("#select-day option:eq(0)").val("");

    //监听年和月的改变
    $("#select-year").change(function() {
        changeDay();
    });
    $("#select-month").change(function() {
        changeDay();
    });

    function changeDay() {
        //补充被删去的日期
        var $optionV29 = $('<option value="29">29</option>');
        var $optionV30 = $('<option value="30">30</option>');
        var $optionV31 = $('<option value="31">31</option>');
        if (!$("#select-day option[value='29']")[0]) $("#select-day").append($optionV29);
        if (!$("#select-day option[value='30']")[0]) $("#select-day").append($optionV30);
        if (!$("#select-day option[value='31']")[0]) $("#select-day").append($optionV31);

        var year = $("#select-year").val();
        var month = $("#select-month").val();

        var leapYear = isLeapYear(year);
        var bigMonth = isBigMonth(month);

        if (!bigMonth) { //小月
            $("#select-day option[value='31']").remove();
            if (month == "2") {
                $("#select-day option[value='30']").remove();
                if (!leapYear) {
                    $("#select-day option[value='29']").remove();
                }
            }
        }
    }

    //判断是否为闰年
    function isLeapYear(year) {
        if (!year) return true;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) return true; //能被400整除的，是闰年
                else return false;
            } //能被100整除，但不能被400整除的，不是闰年
            else return true;
        } //能被4整除，但不能被100整除的，不是闰年
        else return false; //不能被4整除的，不是闰年
    }

    //判断是否为大月
    function isBigMonth(month) {
        if (!month) return true;
        if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) return false;
        else return true;
    }
});