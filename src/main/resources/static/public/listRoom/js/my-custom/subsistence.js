$(document).ready(function () {

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

    $('.alert').fadeOut(3000);
});