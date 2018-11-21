function changeStatus(userId, stt) {
    var result = confirm('Bạn có chắc muốn thay đổi trạng thái người dùng?');
    if (result) {
        var btn = '<span class="hide">' + stt + '</span>';
        if (stt == 1) {
            btn += '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus(' + userId + ',0)">'
                + 'Active'
                + ' </button>';
        } else {
            btn += '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus(' + userId + ',1)">'
                + 'Inactive'
                + ' </button>';
        }

        $.ajax({
            type: 'post',
            url: '/admin/user/change-status',
            data: {
                userId,
                stt
            },
            success: function (data) {
                if (data != null) {
                    $('.change-stt-' + userId).html(btn);
                } else {
                    alert('Cập nhật trạng thái thất bại!');
                }
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }
}

$(document).ready(function () {
    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        console.log("area: " + areaId);
        var uriFloor = '/admin/floor/area/' + areaId;
        var uriRoom = '/admin/room/area/' + areaId;
        var uri = '/admin/student/area/' + areaId;

        showDataSelect(uriFloor, '#select-floor');
        showDataSelect(uriRoom, '#select-room');
        showStudents(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        var floorId = this.value;
        var uriRoom = '/admin/room/floor/' + floorId;
        var uri = '/admin/student/floor/' + floorId;

        if (floorId == 0) {
            uriRoom = '/admin/room/area/' + areaId;
            uri = '/admin/student/area/' + areaId;
        }
        showDataSelect(uriRoom, '#select-room');
        showStudents(uri);
    });

    $(document).on("change", "#select-room", function () {
        var roomId = this.value;
        var areaId = $('#select-area').val();
        var floorId = $('#select-floor').val();
        var uri = '/admin/student/room/' + roomId;
        console.log("area: " + areaId + " floor: " + floorId);
        if (roomId == 0) {
            if (floorId == 0) {
                uri = '/admin/student/area/' + areaId;
            } else {
                uri = '/admin/student/floor/' + floorId;
            }
        }
        showStudents(uri);
    });

    $(document).on("click", ".btn-view", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var fullname = $(this).closest('tr').children('td.fullname').text();
        var className = $(this).closest('tr').children('td.class').text();
        var gender = $(this).closest('tr').children('td.gender').text();
        var username = $(this).closest('tr').children('td.username').text();
        var phone = $(this).closest('tr').children('td.phone').text();
        var address = $(this).closest('tr').children('td.address').text();
        var mssv = $(this).closest('tr').children('td.mssv').text();

        $('#fullname-view').html(fullname);
        $('#class-view').html(className);
        $('#gender-view').html(gender);
        $('#username-view').html(username);
        $('#phone-view').html(phone);
        $('#address-view').html(address);
        $('#mssv-view').html(mssv);
    });

    function showStudents(uri) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : uri,
            success: function(result) {
                console.log(result);
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<th scope="row">'+ (index + 1) +'</th>'
                            + '<td class="id hide">' + item.id + '</td>'
                            + '<td class="username">' + item.userName + '</td>'
                            + '<td class="fullname">' + item.userDetail.fullName + '</td>'
                            + '<td class="class">' + item.studentCode.name + '</td>'
                            + '<td class="gender">' + (item.gender == 1 ? 'Nam' : 'Nữ') + '</td>'
                            + '<td class="stt change-stt-' + item.id + '">'
                            + '<span class="hide">' + item.status + '</span>'
                            + (item.status == 1 ? '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus('+ item.id +',0)">Active</button>' : '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus('+ item.id +',1)">Inactive</button>')
                            + '</td>'
                            + '<td>'
                            + '<button type="button" class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#viewModal"><i class="fa fa-folder"></i> View </button>'
                            + ' <button class="btn btn-info btn-xs btn-edit" data-toggle="modal" data-target="#editModal"><i class="fa fa-pencil"></i> Edit </button>'
                            + '</td>'
                            + '<td class="phone hide">' + item.userDetail.phone + '</td>'
                            + '<td class="address hide">' + item.userDetail.address + '</td>'
                            + '<td class="mssv hide">' + item.studentCode.value + '</td>'
                        '</tr>';
                    });
                } else {
                    html = '<tr><td colspan="7" class="text-center"><h3>Không có dữ liệu!</h3></td></tr>';
                }
                $('#table-body').html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    function showDataSelect(uri, destination) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : uri,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    html += '<option value="0">Tất cả</option>';
                    result.forEach(function (item, index) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                } else {
                    html = '<option value="">Not found!</option>';
                }
                $(destination).html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }
});