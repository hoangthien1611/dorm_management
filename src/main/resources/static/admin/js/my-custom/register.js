$(document).ready(function () {
    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        console.log("area: " + areaId);
        var uriFloor = '/admin/floor/area/' + areaId;
        var uriRoom = '/admin/room/area/' + areaId;
        var uri = '/admin/register-room/area/' + areaId;

        showDataSelect(uriFloor, '#select-floor');
        showDataSelect(uriRoom, '#select-room');
        showRegisters(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        var floorId = this.value;
        var uriRoom = '/admin/room/floor/' + floorId;
        var uri = '/admin/register-room/floor/' + floorId;

        if (floorId == 0) {
            uriRoom = '/admin/room/area/' + areaId;
            uri = '/admin/register-room/area/' + areaId;
        }
        showDataSelect(uriRoom, '#select-room');
        showRegisters(uri);
    });

    $(document).on("change", "#select-room", function () {
        var roomId = this.value;
        var areaId = $('#select-area').val();
        var floorId = $('#select-floor').val();
        var uri = '/admin/register-room/room/' + roomId;
        console.log("area: " + areaId + " floor: " + floorId);
        if (roomId == 0) {
            if (floorId == 0) {
                uri = '/admin/register-room/area/' + areaId;
            } else {
                uri = '/admin/register-room/floor/' + floorId;
            }
        }
        showRegisters(uri);
    });

    $(document).on("click", ".btn-view", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var className = $(this).closest('tr').children('td.class').text();
        var gender = $(this).closest('tr').children('td.gender').text();
        var timeRegister = $(this).closest('tr').children('td.time-register').text();
        var room = $(this).closest('tr').children('td.room').text();
        var stdCode = $(this).closest('tr').children('td.std-code').text();
        var semester = $(this).closest('tr').children('td.semester').text();
        var year = $(this).closest('tr').children('td.year').text();
        var timeCensor = $(this).closest('tr').children('td.time-censor').text();
        var floor = $(this).closest('tr').children('td.floor').text();
        var area = $(this).closest('tr').children('td.area').text();

        $('#room-view').html(room + ', ' + floor + ', ' + area);
        $('#name-view').html(name);
        $('#class-view').html(className);
        $('#gender-view').html(gender);
        $('#register-view').html(timeRegister);
        $('#accept-view').html(timeCensor);
        $('#std-code-view').html(stdCode);
        $('#year-view').html(year);
        $('#semester-view').html(semester);
    });

    function showRegisters(uri) {
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
                            + '<td><input type="checkbox" class="checkbox mycheckbox" ' + (item.status == 1 ? 'disabled' : '') + ' name="regId" value="' + item.id + '"  /></td>'
                            + '<th scope="row">'+ (index + 1) +'</th>'
                            + '<td class="id hide">' + item.id + '</td>'
                            + '<td class="name">' + item.fullName + '</td>'
                            + '<td class="class">' + item.className + '</td>'
                            + '<td class="gender">' + (item.gender == 1 ? 'Nam' : 'Nữ') + '</td>'
                            + '<td class="time-register">' + item.timeRegisterCustom + '</td>'
                            + '<td class="room">' + item.roomName + '</td>'
                            + '<td class="stt">'
                            + '<span class="hide">' + item.status + '</span>'
                            + (item.status == 1 ? '<button type="button" class="btn btn-success btn-xs">Đã phê duyệt</button>' : '<button type="button" class="btn btn-danger btn-xs">Chờ phê duyệt</button>')
                            + '</td>'
                            + '<td>'
                            + '<button type="button" class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#viewModal"><i class="fa fa-folder"></i> View </button>'
                            + '</td>'
                            + '<td class="std-code hide">' + item.studentCode + '</td>'
                            + '<td class="year hide">' + item.year + '</td>'
                            + '<td class="time-censor hide">' + item.timeCensorCustom + '</td>'
                            + '<td class="semester hide">' + item.semesterName + '</td>'
                            + '<td class="floor hide">' + item.floorName + '</td>'
                            + '<td class="area hide">' + item.areaName + '</td>'
                        '</tr>';
                    });
                } else {
                    html = '<tr><td colspan="9" class="text-center"><h3>Không có dữ liệu!</h3></td></tr>';
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

    $(document).on("change", "#checkAll", function(){
        $("input:checkbox").not("[disabled]").prop('checked', $(this).prop("checked"));
        if($(this).prop("checked")) {
            var count = $('.checkbox:checked').length;
            if (count > 0) {
                $('#acceptAll').html('Phê duyệt tất cả (' + count + ')');
                $('#acceptAll').show();
            }
        } else {
            $('#acceptAll').hide();
        }
    });

    $(document).on("change", ".checkbox", function(){
        var count = 0;
        if(false == $(this).prop("checked")){
            $("#checkAll").prop('checked', false);
        }
        if ($('.checkbox:checked').length == $('.checkbox').not("[disabled]").length ){
            $("#checkAll").prop('checked', true);
        }
        if ($('.checkbox:checked').length > 0) {
            count = $('.checkbox:checked').length;
            $('#acceptAll').html('Phê duyệt tất cả (' + count + ')');
            $("#acceptAll").show();
        } else {
            $("#acceptAll").hide();
        }
    });

    $('.alert').fadeOut(5000);
});