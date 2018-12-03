function changeStatus(roomId, stt) {
    var result = confirm('Bạn có chắc muốn thay đổi trạng thái phòng?');
    if (result) {
        var btn = '<span class="hide">' + stt + '</span>';
        if (stt == 1) {
            btn += '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus(' + roomId + ',0)">'
                + 'Active'
                + ' </button>';
        } else {
            btn += '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus(' + roomId + ',1)">'
                + 'Inactive'
                + ' </button>';
        }

        $.ajax({
            type: 'post',
            url: '/admin/room/change-status',
            data: {
                roomId: roomId,
                stt: stt
            },
            success: function (data) {
                if (data == "OK") {
                    $('.change-stt-' + roomId).html(btn);
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

$(document).ready(function () {

    $(document).on("click", ".btn-view", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var area = $(this).closest('tr').children('td.area').text();
        var numberBed = $(this).closest('tr').children('td.bed').text();
        var max = $(this).closest('tr').children('td.max').text();
        var present = $(this).closest('tr').children('td.present').text();
        var cost_bed = $(this).closest('tr').children('td.cost_bed').text();
        var function_name = $(this).closest('tr').children('td.function_name').text();
        var name_floor = $(this).closest('tr').children('td.name_floor').text();
        var gender = $(this).closest('tr').children('td.gender').text();

        $('#address-view').html(name + ' ' + name_floor + ' ' + area);
        $('#function-view').html(function_name);
        $('#bed-view').html(numberBed);
        $('#max-view').html(max);
        $('#present-view').html(present);
        $('#gender-view').html(gender);
        $('#cost-view').html(cost_bed + ' VND');
        $('#id-room-view').html(id);

        var time = $('#timeFilter').val();
        var yearMonth = time.split('-');

        showStudents(id);

       if (yearMonth.length == 2) {
           getSubsistenceByRoomIdAndTime(id, yearMonth[1], yearMonth[0]);
       } else {
           $('#table-subsistence-view').hide();
           $('#msg-view').html('<h4>Vui lòng chọn lại thời gian muốn xem!</h4>');
           $('.msg-view').show();
       }
    });

    $(document).on("click", ".btn-edit", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var areaId = $('#select-area').val();
        var numberBed = $(this).closest('tr').children('td.bed').text();
        var max = $(this).closest('tr').children('td.max').text();
        var cost_bed = $(this).closest('tr').children('td.cost_bed').text();
        var gender = $(this).closest('tr').children('td.gender').text();
        var stt = $(this).closest('tr').children('td.stt').find('span').text();
        var function_name = $(this).closest('tr').children('td.function_name').text();
        var name_floor = $(this).closest('tr').children('td.name_floor').text();
        var present = $(this).closest('tr').children('td.present').text();
        var register = $(this).closest('tr').children('td.register').text();

        console.log(name_floor);
        showFloors(areaId, '#floorEdit', 0, name_floor);

        $('#idEdit').val(id);
        $('#roomEdit').val(name);
        $('#areaEdit').val(areaId);
        $('#bedEdit').val(numberBed);
        $('#maxEdit').val(max);
        $('#presentEdit').val(present);
        $('#registerEdit').val(register);
        $(`#costBedEdit option:contains(${cost_bed})`).prop('selected',true);
        $(`#roomFunctionEdit option:contains(${function_name})`).prop('selected',true);
        $(`#genderEdit option:contains(${gender})`).prop('selected',true);
        $('#statusEdit').val(stt);
        $('#maxEdit').attr({"min": present});
    });

    $(document).on("change", "#areaEdit", function () {
        var areaId = this.value;
        showFloors(areaId, '#floorEdit', 0, null);
    });

    $(document).on("change", "#areaAdd", function () {
        var areaId = this.value;
        showFloors(areaId, '#floorAdd', 0, null);
    });

    $(document).on("click", "#submit-timeFilter-view", function () {
        var roomId = $('#id-room-view').text();
        var time = $('#timeFilter').val();
        var yearMonth = time.split('-');

        if (yearMonth.length == 2) {
            getSubsistenceByRoomIdAndTime(roomId, yearMonth[1], yearMonth[0]);
        } else {
            $('#table-subsistence-view').hide();
            $('#msg-view').html('<h4>Vui lòng chọn lại thời gian muốn xem!</h4>');
            $('.msg-view').show();
        }
    });

    function getSubsistenceByRoomIdAndTime(roomId, month, year) {
        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '/admin/subsistence/room/' + roomId + '/' + month + '/' + year,
            success: function (result) {
                console.log(result);
                if (result != null && result.length > 0) {
                    var obj = result[0];
                    var elecUsed = parseInt(obj.newNumberElec) - parseInt(obj.oldNumberElec);
                    var waterUsed = parseInt(obj.newNumberWater) - parseInt(obj.oldNumberWater);
                    var elec = elecUsed*obj.costElec;
                    var water = waterUsed*obj.costWater;

                    $('#oldElec-view').html(obj.oldNumberElec);
                    $('#newElec-view').html(obj.newNumberElec);
                    $('#oldWater-view').html(obj.oldNumberWater);
                    $('#newWater-view').html(obj.newNumberWater);
                    $('#elec-view').html(elec + ' VND');
                    $('#water-view').html(water + ' VND');
                    $('#elecUsed-view').html(elecUsed);
                    $('#waterUsed-view').html(waterUsed);
                    $('#total').html(obj.total + ' VND');

                    if (obj.status == 0) {
                        $('#isPaid').prop('checked', false);
                    } else {
                        $('#isPaid').prop('checked', true);
                    }

                    $('.msg-view').hide();
                    $('#table-subsistence-view').show();
                } else {
                    $('#table-subsistence-view').hide();
                    $('#msg-view').html('<h4>Không có dữ liệu!</h4>');
                    $('.msg-view').show();
                }
            }
        });
    }

    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        var uri = '/admin/room/area/' + areaId;
        showFloors(areaId, '#select-floor', 1, null);
        showRooms(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        console.log('Area: ' + areaId);
        var floorId = this.value;
        var uri = '/admin/room/floor/' + floorId;

        if (floorId == 0) {
            uri = '/admin/room/area/' + areaId;
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
            url : '/admin/floor/area/' + areaId,
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
            url : '/admin/user/student/room/' + roomId,
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