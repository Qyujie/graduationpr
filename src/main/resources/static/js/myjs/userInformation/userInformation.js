$(function() {
    $("#uiSubmit").click(function() {
        var page = "updateUserInformation";
        var namevalue = $("#name-value").val();
        var sexvalue = $("#sex-value").val();
        var phonevalue = $("#phone-value").val().replace(/\s*/g, "");
        var prefecturevalue = $("#prefecture-value").val();
        var real_namevalue = $("#real_name-value").val();
        var id_cardvalue = $("#id_card-value").val().replace(/\s*/g, "");

        //处理生日，将年月日合并,且格式为yyyy-mm-dd
        var yearvalue = $("#select-year").val();
        var monthvalue = $("#select-month").val();
        var dayvalue = $("#select-day").val();
        if (yearvalue == "") yearvalue = "0";
        if (monthvalue == "") monthvalue = "0";
        if (dayvalue == "") dayvalue = "0";
        var brithdayvalue = yearvalue + "-" + monthvalue + "-" + dayvalue;
        //处理性别，如果没选择则为空
        if (sexvalue == "请选择") sexvalue = "";

        //处理所在地区，如果没选择则为空
        if (prefecturevalue == "请选择") prefecturevalue = "";

        $.post(
            page, {
                "name": namevalue,
                "birthday": brithdayvalue,
                "sex": sexvalue,
                "phone": phonevalue,
                "prefecture": prefecturevalue,
                "real_name": real_namevalue,
                "id_card": id_cardvalue,
            },
            function(result) {
                if (result == "Success") {
                    console.log('修改成功');
                } else {
                    console.log('修改失败');
                }
            }
        );
    });
});