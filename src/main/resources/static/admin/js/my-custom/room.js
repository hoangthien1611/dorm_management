$(document).ready(function () {

    $(document).on("click", ".btn-view", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();
        var area = $(this).closest('tr').children('td.area').text();
        var numberBed = $(this).closest('tr').children('td.bed').text();
        var max = $(this).closest('tr').children('td.max').text();
        var present = $(this).closest('tr').children('td.present').text();
        var cost_bed = $(this).closest('tr').children('td.cost_bed').text();
        // var stt = $(this).closest('tr').children('td.stt').find('span').text();

        $('#address-view').html(name + ' ' + area);
        $('#bed-view').html(numberBed);
        $('#max-view').html(max);
        $('#present-view').html(present);
        $('#cost-view').html(cost_bed + ' VND');

        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '/admin/subsistence_fee/room/' + id,
            success: function (result) {
                console.log(result);
            }
        });
    });

    $(document).on("change", "#select-area", function () {
        var value = this.value;

        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/room/area/' + value,
            success: function(result) {
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<tr>'
                            + '<th scope="row">'+ (index + 1) +'</th>'
                            + '<td class="name">' + item.name + '</td>'
                            + '<td class="area">' + item.name_area + '</td>'
                            + '<td class="present">' + item.studentPresent + '</td>'
                            + '<td class="max">' + item.studentMax + '</td>'
                            + '<td class="gender">' + (item.gender == 1 ? 'Nam' : 'Nữ') + '</td>'
                            + '<td class="stt">'
                            + '<span class="hide">' + item.status + '</span>'
                            + (item.status == 1 ? '<button type="button" class="btn btn-success btn-xs">Đang hoạt động</button>' : '<button type="button" class="btn btn-dark btn-xs">Đang bảo trì</button>')
                            + '</td>'
                            + '<td>'
                            + '<button class="btn btn-primary btn-xs btn-view" data-toggle="modal" data-target="#roomInfoMd"><i class="fa fa-folder"></i> View </button>'
                            + ' <button class="btn btn-info btn-xs" data-toggle="modal" data-target="#modalEdit"><i class="fa fa-pencil"></i> Edit </button>'
                            + ' <a href="#" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Delete </a>'
                            + '</td>'
                            + '<td class="id hide">' + item.id + '</td>'
                            + '<td class="bed hide">' + item.numberBed + '</td>'
                            + '<td class="cost_bed hide">' + item.value_cost + '</td>'
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
    })

    $('.alert').fadeOut(3000);
});