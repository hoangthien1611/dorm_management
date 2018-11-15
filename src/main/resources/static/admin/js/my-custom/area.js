function changeStatus(areaId, stt) {
    var result = confirm('Bạn có chắc muốn thay đổi trạng thái khu nhà?');
    if (result) {
        var btn = '<span class="hide">' + stt + '</span>';
        if (stt == 1) {
            btn += '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus(' + areaId + ',0)">'
                + 'Active'
                + ' </button>';
        } else {
            btn += '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus(' + areaId + ',1)">'
                + 'Inactive'
                + ' </button>';
        }

        $.ajax({
            type: 'post',
            url: '/admin/area/change-status',
            data: {
                areaId,
                stt
            },
            success: function (data) {
                if (data != null) {
                    $('.change-stt-' + areaId).html(btn);
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

    $(document).on("click", ".btn-edit", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var floor = $(this).closest('tr').children('td.floor').text();
        var stt = $(this).closest('tr').children('td.stt').find('span').text();

        $('#id').val(id);
        $('#name').val(name);
        $('#numberFloor').val(floor);
        $('#status').val(stt);
    });

    $('.alert').fadeOut(5000);
});