function changeStatus(feeId) {
    var result = confirm('Bạn có chắc muốn xác nhận phòng này đã nộp tiền?');
    if (result) {
        var btn = '<span class="hide">1</span>'
                    + '<button type="button" class="btn btn-success btn-xs">Đã nộp</button>';
        var actions = '<button href="#" class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#viewModal"><i class="fa fa-folder"></i> View </button>'
                        + ' <button href="#" class="btn btn-info btn-xs disabled"><i class="fa fa-pencil"></i> Edit </button>';


        $.ajax({
            type: 'post',
            url: '/admin/subsistence/paid',
            data: {
                feeId
            },
            success: function (data) {
                if (data != null) {
                    $('.change-stt-' + feeId).html(btn);
                    $('#btn-actions').html(actions);
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

    $(document).on("click", "#submit", function () {
        var areaId = $('#select-area-index').val();
        var floorId = $('#select-floor-index').val();
        var time = $('#timeFilter').val();
        var monthYear = time.split('-');
        console.log(areaId + " " + floorId + " " + time);

        if (monthYear.length == 2) {
            var year = monthYear[0];
            var month = monthYear[1];
            var url = '/admin/subsistence/area/' + areaId + '/' + month + '/' + year;
            if (floorId != 0) {
                url = '/admin/subsistence/floor/' + floorId + '/' + month + '/' + year;
            }
            showData(url);
        }
    });

    $(document).on("change", "#select-area-index", function () {
        var areaId = this.value;
        showFloors(areaId, '#select-floor-index');
    });

    function showFloors(areaId, destination) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/floor/area/' + areaId,
            success: function(result) {
                console.log('floors: ' + result.length);
                var html = '';
                if (result != null && result.length > 0) {
                    html += '<option value="0">Tất cả</option>';
                    result.forEach(function (item, index) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                } else {
                    html = '<option value="">Không tìm thấy tầng nào!</option>';
                }
                $(destination).html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    function showData(url) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : url,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<td scope="row">' + (index + 1) + '</td>'
                            + '<td class="id hide">' + item.id + '</td>'
                            + '<td class="room-name">' + item.roomName + '</td>'
                            + '<td class="area-name">' + item.areaName + '</td>'
                            + '<td class="time">' + item.month + '/' + item.year + '</td>'
                            + '<td class="elec">' + (item.newNumberElec - item.oldNumberElec)*item.costElec + ' VND' + '</td>'
                            + '<td class="water">' + (item.newNumberWater - item.oldNumberWater)*item.costWater + ' VND' + '</td>'
                            + '<td class="total">' + item.total + ' VND' + '</td>'
                            + '<td class="stt change-stt-' + item.id + '">'
                            + '<span class="hide">' + item.status + '</span>'
                            + (item.status == 0 ? '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus('+ item.id +')">Chưa nộp</button>' : '<button type="button" class="btn btn-success btn-xs">Đã nộp</button>')
                            + '</td>'
                            + '<td id="btn-actions">'
                            + '<button href="#" class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#viewModal"><i class="fa fa-folder"></i> View </button>'
                            + (item.status == 0 ? ' <button href="#" class="btn btn-info btn-xs btn-edit" data-toggle="modal" data-target="#editModal"><i class="fa fa-pencil"></i> Edit </button>' : ' <button href="#" class="btn btn-info btn-xs disabled"><i class="fa fa-pencil"></i> Edit </button>')
                            + '</td>'
                            + '<td class="oldElec hide">' + item.oldNumberElec + '</td>'
                            + '<td class="newElec hide">' + item.newNumberElec + '</td>'
                            + '<td class="oldWater hide">' + item.oldNumberWater + '</td>'
                            + '<td class="newWater hide">' + item.newNumberWater + '</td>'
                            + '<td class="roomId hide">' + item.roomId + '</td>'
                            + '</tr>';
                    });
                } else {
                    html = '<tr><td colspan="9" class="text-center"><h3>Không có dữ liệu!</h3></td></tr>';
                }
                $('#tbody-subsistence').html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    $(document).on("click", ".btn-view", function () {
        var oldElec = $(this).closest('tr').children('td.oldElec').text();
        var newElec = $(this).closest('tr').children('td.newElec').text();
        var oldWater = $(this).closest('tr').children('td.oldWater').text();
        var newWater = $(this).closest('tr').children('td.newWater').text();
        var elec = $(this).closest('tr').children('td.elec').text();
        var water = $(this).closest('tr').children('td.water').text();
        var total = $(this).closest('tr').children('td.total').text();
        var stt = $(this).closest('tr').children('td.stt').find('span').text();

        var elecUsed = parseInt(newElec) - parseInt(oldElec);
        var waterUsed = parseInt(newWater) - parseInt(oldWater);

        var room = $(this).closest('tr').children('td.room-name').text();
        var area = $(this).closest('tr').children('td.area-name').text();
        var time = $(this).closest('tr').children('td.time').text();
        var titleView = 'THÔNG TIN ĐIỆN NƯỚC ' + room + ' ' + area + ' Tháng ' + time;

        $('.title-view').html(titleView);
        $('#oldElec').html(oldElec);
        $('#newElec').html(newElec);
        $('#oldWater').html(oldWater);
        $('#newWater').html(newWater);
        $('#elec').html(elec);
        $('#water').html(water);
        $('#elecUsed').html(elecUsed);
        $('#waterUsed').html(waterUsed);
        $('#total').html(total);

        if (stt == '0') {
            $('#isPaid').prop('checked', false);
        } else {
            $('#isPaid').prop('checked', true);
        }
    });

    $(document).on("click", ".btn-edit", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var oldElec = $(this).closest('tr').children('td.oldElec').text();
        var newElec = $(this).closest('tr').children('td.newElec').text();
        var oldWater = $(this).closest('tr').children('td.oldWater').text();
        var newWater = $(this).closest('tr').children('td.newWater').text();
        var roomId = $(this).closest('tr').children('td.roomId').text();

        var room = $(this).closest('tr').children('td.room-name').text();
        var area = $(this).closest('tr').children('td.area-name').text();
        var time = $(this).closest('tr').children('td.time').text();
        var titleView = 'CẬP NHẬT THÔNG TIN ĐIỆN NƯỚC ' + room + ' ' + area + ' Tháng ' + time;

        var monthYear = time.split('/');
        if (monthYear.length == 2) {
            var month = monthYear[0];
            var year = monthYear[1];
        }

        $('.title-edit').html(titleView);
        $('#id').val(id);
        $('#oldElecEdit').val(oldElec);
        $('#oldWaterEdit').val(oldWater);
        $('#newElecEdit').val(newElec);
        $('#newWaterEdit').val(newWater);
        $('#roomId').val(roomId);
        $('#month').val(month);
        $('#year').val(year);
    });

    $(document).on("change", "#select-area", function () {
        var areaId = this.value;

        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/room/area/' + areaId,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                } else {
                    html = '<option value="">Không tìm thấy phòng!</option>';
                }
                $('#select-room').html(html);
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    });

    $('.alert').fadeOut(5000);
});