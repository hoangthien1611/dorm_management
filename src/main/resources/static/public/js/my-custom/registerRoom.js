$(document).ready(function () {
    $("#table-room").DataTable( );

    $(document).on("change", "#select-area", function () {
        var gender = 1;
        var areaId = this.value;
        var semesterId = $('#semester-id').val();
        var semesterYear = $('#semester-year').val();
        var uri = '/dorm/register-room/get-room-in-area/' + areaId;
        showFloors(areaId, '#select-floor', 1, null);
        showRooms(uri, gender, semesterId, semesterYear);
    });

    $(document).on("change", "#select-floor", function () {
        var gender = 1;
        var areaId = $('#select-area').val();
        console.log('Area: ' + areaId);
        var floorId = this.value;
        var semesterId = $('#semester-id').val();
        var semesterYear = $('#semester-year').val();
        var uri = '/dorm/register-room/get-room-in-floor/' + floorId;

        if (floorId == 0) {
            uri = '/dorm/register-room/get-room-in-area/' + areaId;
        }
        showRooms(uri, gender, semesterId, semesterYear);
    });

    function showRooms(uri, gender, semesterId, semesterYear) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : uri,
            success: function(result) {
                // console.log('rooms: ' + result.length);
                var date = new Date();
                var year = semesterYear;
                var currentTimeStamp = date.getTime();
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {

                        if(item.gender == gender && item.status == 1 && item.functionId == 1) {
                            var numberBedEmpty = item.numberBed - item.studentRegister;
                            html += '<tr>'
                                + '<th scope="row">'+ (index + 1) +'</th>'
                                + '<td class="name">' + item.name + '</td>'
                                + '<td class="area">' + item.areaName + '</td>'
                                + '<td class="max">' + item.numberBed + '</td>'
                                + '<td class="cost">' + item.costValue + ' VND</td>'
                                + '<td class="present">' + numberBedEmpty + '</td>';
                                if (numberBedEmpty > 0) {
                                    html += '<td>'
                                        + '<select class="form-control number-register">';
                                         for(var i = 1; i <= numberBedEmpty; i++) {
                                             html += '<option value="' + i + '">' + i + '</option>';
                                         }
                                        html += '</select>'
                                    + '</td>';
                                 } else {
                                    html += '<td>Hết giường trống!</td>';
                                 }
                                html +=  '<td>'
                                // + '<button class="btn btn-info btn-xs btn-edit btn-reg-room"><i class="fa fa-plus fa-fw"></i></i>Đăng ký</button>'
                                + '<a href="" class="btn btn-info btn-xs btn-edit">Đăng ký</a>'
                                + '</td>'
                                + '<td class="room_id hide">' + item.id + '</td>'
                                + '<td class="year hide">' + year + '</td>'
                                + '<td class="user_id hide">' + item.costValue + '</td>'
                                + '<td class="time_register hide">' + currentTimeStamp + '</td>'
                                + '<td class="semester_id hide">' + semesterId + '</td>'
                                +'</tr>';
                        }
                    });
                } else {
                    html = '<tr><td colspan="8" class="text-center"><h3>Không có dữ liệu!</h3></td></tr>';
                }
                $('#table-body').html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    function showFloors(areaId, destination, type, defaultVal) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/dorm/register-room/get-floor/' + areaId,
            success: function(result) {
                console.log('floors: ' + result.length);
                var html = '';
                if (result != null && result.length > 0) {
                    html += type == 1 ? '<option value="0">Tất cả</option>' : '';
                    result.forEach(function (item, index) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                } else {
                    html = '<option value="">Không tìm thấy tầng nào!</option>';
                }
                $(destination).html(html);
                if (type == 0) {
                    $(`${destination} option:contains(${defaultVal})`).prop('selected',true);
                }
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }
    $('.alert').fadeOut(5000);

    $(".btn-reg-room").click(function () {
         var $row = $(this).closest("tr");
         var roomId = $row.find(".room_id").text();
         var userId = $row.find(".user_id").text();
         var semesterId = $row.find(".semester_id").text();
         var number = $row.find(':selected').data('number-register');
         var year = $row.find(".year").text();
         var timeRegister = $row.find(".time_register").text();
        $.ajax({
            type: "POST",
            url: "/dorm/register-room/add",
            data: { roomId: roomId, userId: userId, semesterId: semesterId, number: number, timeRegister: timeRegister, year: year},
        })
        .done(function(data) {
            $("#list-room").html(data);
            console.log(data);
        })
        .fail(function (json) {
            var msg = json.responseJSON.message;
            alert(msg);
            $("#notice-message").html("Lỗi hệ thống!");
        });
    })

    $(".btn-del-reg").click(function () {
        var $row = $(this).closest("tr");
        var regId = $row.find(".reg-id").text();
        $.ajax({
            type: "GET",
            url: "/dorm/register-room/delete/" + regId,
        })
        .done(function(data) {
            $("#info-reg-room").html(data);
            console.log(data);
        })
        .fail(function (json) {
            var msg = json.responseJSON.message;
            alert(msg);
            $("#notice-message").html("Lỗi hệ thống!");
        });
    })

});