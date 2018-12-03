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
                if (data == "OK") {
                    $('.change-stt-' + userId).html(btn);
                } else {
                    alert('Cập nhật trạng thái thất bại!');
                }
            },
            error: function () {
                alert('Error! Không cập nhật được!');
            }
        });
    }
}

function changeStatusAction(username, actionId, stt) {
    var result = '';
    if (stt == 1) {
        result += '<input type="checkbox"  onclick="changeStatusAction(\'' + username + '\',' + actionId + ',0)">';
    } else {
        result += '<input type="checkbox" checked onclick="changeStatusAction(\'' + username + '\',' + actionId + ',1)">';
    }

    $.ajax({
        type: 'post',
        url: '/admin/user/change-status-action',
        data: {
            username,
            actionId
        },
        success: function (data) {
            if (data == "OK") {
                result += '<span class="slider round"></span>';
                $('.change-action-' + actionId).html(result);
            } else {
                if (stt == 0) {
                    $('.change-action-' + actionId).find('input').prop('checked', false);
                } else {
                    $('.change-action-' + actionId).find('input').prop('checked', true);
                }
                alert('Cập nhật trạng thái Action thất bại!');
            }
        },
        error: function () {
            if (stt == 0) {
                $('.change-action-' + actionId).find('input').prop('checked', false);
            } else {
                $('.change-action-' + actionId).find('input').prop('checked', true);
            }
            alert('Error! Không cập nhật được!');
        }
    });
}

$(document).ready(function () {
    var baseUrl = 'http://localhost:8080';

    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        console.log("area: " + areaId);
        var uriFloor = '/admin/floor/area/' + areaId;
        var uriRoom = '/admin/room/area/' + areaId;
        var uri = '/admin/user/student/area/' + areaId;

        showDataSelect(uriFloor, '#select-floor');
        showDataSelect(uriRoom, '#select-room');
        // showStudents(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        var floorId = this.value;
        var uriRoom = '/admin/room/floor/' + floorId;
        var uri = '/admin/user/student/floor/' + floorId;

        if (floorId == 0) {
            uriRoom = '/admin/room/area/' + areaId;
            uri = '/admin/user/student/area/' + areaId;
        }
        showDataSelect(uriRoom, '#select-room');
        // showStudents(uri);
    });

    $(document).on("change", "#select-room", function () {
        var roomId = this.value;
        var areaId = $('#select-area').val();
        var floorId = $('#select-floor').val();
        var uri = '/admin/user/student/room/' + roomId;
        console.log("area: " + areaId + " floor: " + floorId);
        if (roomId == 0) {
            if (floorId == 0) {
                uri = '/admin/user/student/area/' + areaId;
            } else {
                uri = '/admin/user/student/floor/' + floorId;
            }
        }
        // showStudents(uri);
    });

    $(document).on("click", "#view-student-list", function () {
        var areaId = $('#select-area').val();
        var floorId = $('#select-floor').val();
        var roomId = $('#select-room').val();
        console.log("roomId: " + roomId);

        var url = baseUrl + '/admin/user/student?area=' + areaId + '&floor=' + floorId + '&room=' + roomId;
        window.location = url;
    });

    $(document).on("click", "#view-empl-list", function () {
        var groupId = $('#select-group').val();
        console.log("group: " + groupId);

        var url = baseUrl + '/admin/user/employee?group=' + groupId;
        window.location = url;
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
        var group = $(this).closest('tr').children('td.group-empl').text();

        $('#fullname-view').html(fullname);
        $('#class-view').html(className);
        $('#gender-view').html(gender);
        $('#username-view').html(username);
        $('#phone-view').html(phone);
        $('#address-view').html(address);
        $('#mssv-view').html(mssv);
        $('#group-view').html(group);
    });

    $(document).on("click", ".btn-reset-pass", function () {
        var username = $(this).closest('tr').children('td.username').text();
        var result = confirm('Bạn có chắc muốn reset password cho tài khoản ' + username + '?');
        if (result) {
            $.ajax({
                type: 'post',
                url: '/admin/user/reset-password',
                data: {
                    name: username,
                },
                success: function (data) {
                    if (data == "OK") {
                        alert('Reset password cho tài khoản ' + username + ' thành công!');
                    } else {
                        alert('Reset password thất bại!');
                    }
                },
                error: function () {
                    alert('Error! Không reset password được!!');
                }
            });
        }
    });

    $(document).on("click", ".btn-change-group", function () {
        var username = $(this).closest('tr').children('td.username').text();
        var userId = $(this).closest('tr').children('td.id').text();
        var group = $(this).closest('tr').children('td.group-empl').text();

        $('#name').val(username);
        $('#id').val(userId);
        $(`#update-group option:contains(${group})`).prop('selected',true);
    });

    $(document).on("click", ".btn-phan-quyen", function () {
        var username = $("#username-view").text();
        var url = baseUrl + '/admin/user/' + username + '/update-permission';
        window.location = url;
    });

    $(document).on("click", ".btn-check-username", function () {
        var username = $("#userName").val();

        $.ajax({
            type: 'post',
            url: '/admin/user/check-username',
            data: {
                username
            },
            success: function (data) {
                if (data == "Existed") {
                    alert('Username này đã tồn tại. Vui lòng nhập username khác!');
                    ("#userName").focus();
                } else {
                    alert('Có thể sử dụng username này!');
                }
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
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
                    html = '<option value="0">Not found!</option>';
                }
                $(destination).html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    $('.alert').fadeOut(5000);
});