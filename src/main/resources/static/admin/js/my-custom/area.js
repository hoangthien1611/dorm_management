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
                if (data == "OK") {
                    $('.change-stt-' + areaId).html(btn);
                    $('#status-view').html(btn);
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

function changeStatusFloor(floorId, stt) {
    var result = confirm('Bạn có chắc muốn thay đổi trạng thái tầng?');
    if (result) {
        var btn = '';
        if (stt == 1) {
            btn += '<button type="button" class="btn btn-success btn-xs" onclick="changeStatusFloor(' + floorId + ',0)">'
                + 'Active'
                + ' </button>';
        } else {
            btn += '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatusFloor(' + floorId + ',1)">'
                + 'Inactive'
                + ' </button>';
        }

        $.ajax({
            type: 'post',
            url: '/admin/floor/change-status',
            data: {
                floorId,
                stt
            },
            success: function (data) {
                if (data == "OK") {
                    $('.change-stt-floor-' + floorId).html(btn);
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

    $(document).on("click", ".btn-view", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var floor = $(this).closest('tr').children('td.floor').text();
        var stt = $(this).closest('tr').children('td.stt').find('span').text();
        var btn = '<span class="hide">' + stt + '</span>';

        if (stt == 0) {
            btn += '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatus('+ id +', 1)">Inactive</button>';
        } else {
            btn += '<button type="button" class="btn btn-success btn-xs" onclick="changeStatus('+ id +', 0)">Active</button>';
        }

        $('#id-view').html(id);
        $('#name-view').html(name);
        $('#number-floor-view').html(floor);
        $('#status-view').html(btn);

        showFloors(id, floor);
    });

    $(document).on("click", ".btn-add-floor", function () {
        $(".btn-add-floor").hide();
        $("#form-add-floor").show();
    });

    $(document).on("click", ".btn-cancel-add", function () {
        closeForm();
    });

    $(document).on("click", ".btn-submit-add", function () {
        var name = $("#nameFloor").val();
        var areaId = $("#id-view").text();
        var numberFloor =  $('#number-floor-view').text();
        var status = $("#status-view").find('span').text();;
        if (name && areaId) {
            console.log(name + ' ' + areaId);

            $.ajax({
                type : 'post',
                url : '/admin/floor/add',
                data : {
                    name,
                    areaId,
                    status
                },
                success: function(data) {
                    if (data != null) {
                        $("#nameFloor").val('');
                        closeForm();
                        showFloors(areaId, numberFloor);
                    } else {
                        alert('Thêm thất bại!');
                    }
                },
                error: function () {
                    alert('Đã có lỗi xảy ra!');
                }
            });

        }
    });

    function closeForm() {
        $("#form-add-floor").hide();
        $(".btn-add-floor").show();
    }

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

    function showFloors(areaId, numberFloor) {
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/floor/area/' + areaId,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    console.log(result);
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<th class="hide">' + item.id + '</th>'
                            + '<td>' + item.name + '</td>'
                            + '<td class="change-stt-floor-' + item.id + '">' + (item.status == 1 ? '<button type="button" class="btn btn-success btn-xs" onclick="changeStatusFloor('+ item.id +',0)">Active</button>' : '<button type="button" class="btn btn-dark btn-xs" onclick="changeStatusFloor('+ item.id +',1)">Inactive</button>') + '</td>'
                            + '</tr>';
                    });
                } else {
                    html = '<tr><td colspan="2" class="text-center"><h4>Không tìm thấy tầng nào!</h4></td></tr>';
                }
                $('#tbody-floors-view').html(html);
                if (result.length >= numberFloor) {
                    $(".btn-add-floor").hide();
                } else {
                    $(".btn-add-floor").show();
                }
            },
            error: function () {
                alert('Đã có lỗi xảy ra!');
            }
        });
    }

    $('.alert').fadeOut(5000);
});