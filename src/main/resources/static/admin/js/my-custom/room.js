$(document).ready(function () {
    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        var uri = '/dorm/register-room/get-room-in-area/' + areaId;
        showFloors(areaId, '#select-floor', 1, null);
        showRooms(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        console.log('Area: ' + areaId);
        var floorId = this.value;
        var uri = '/dorm/register-room/get-room-in-floor/' + floorId;

        if (floorId == 0) {
            uri = '/dorm/register-room/get-room-in-area/' + areaId;
        }
        showRooms(uri);
    });

    function showRooms(uri) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : uri,
            success: function(result) {
                console.log('rooms: ' + result.length);
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<th scope="row">'+ (index + 1) +'</th>'
                            + '<td class="name">' + item.name + '</td>'
                            + '<td class="area">' + item.areaName + '</td>'
                            + '<td class="present">' + item.studentPresent + '</td>'
                            + '<td class="max">' + item.studentMax + '</td>'
                            + '<td class="gender">' + (item.gender == 1 ? 'Nam' : 'Nữ') + '</td>'
                            + '<td class="stt change-stt-' + item.id + '">'
                            + '<span class="hide">' + item.status + '</span>'
                            + (item.status == 1 ? '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus('+ item.id +',0)">Active</button>' : '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus('+ item.id +',1)">Inactive</button>')
                            + '</td>'
                            + '<td>'
                            + '<button class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#roomInfoMd"><i class="fa fa-folder"></i> View </button>'
                            + ' <button class="btn btn-info btn-xs btn-edit" data-toggle="modal" data-target="#modalEdit"><i class="fa fa-pencil"></i> Edit </button>'
                            + '</td>'
                            + '<td class="id hide">' + item.id + '</td>'
                            + '<td class="bed hide">' + item.numberBed + '</td>'
                            + '<td class="register hide">' + item.studentRegister + '</td>'
                            + '<td class="cost_bed hide">' + item.costValue + '</td>'
                            + '<td class="name_floor hide">' + item.floorName + '</td>'
                            + '<td class="function_name hide">' + item.functionName + '</td>'
                        '</tr>';
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

    function showStudents(roomId) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/user/room/' + roomId,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    console.log(result);
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<th>' + item.userDetail.fullName + '</th>'
                            + '<td>' + item.studentCode.name + '</td>'
                            + '<td>Đang thuê</td>'
                            + '</tr>';
                    });
                } else {
                    html = '<tr><td colspan="3" class="text-center"><h4>Không có sinh viên!</h4></td></tr>';
                }
                $('#tbody-students-view').html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    $('.alert').fadeOut(5000);
});