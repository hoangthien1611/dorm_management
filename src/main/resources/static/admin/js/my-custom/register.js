$(document).ready(function () {
    $(document).on("change", "#select-area", function () {
        var areaId = this.value;
        console.log("area: " + areaId);
        var uriFloor = '/admin/floor/area/' + areaId;
        var uriRoom = '/admin/room/area/' + areaId;

        showDataSelect(uriFloor, '#select-floor');
        showDataSelect(uriRoom, '#select-room');
        // showRegisters(uri);
    });

    $(document).on("change", "#select-floor", function () {
        var areaId = $('#select-area').val();
        var floorId = this.value;
        var uriRoom = '/admin/room/floor/' + floorId;

        if (floorId == 0) {
            uriRoom = '/admin/room/area/' + areaId;
        }
        showDataSelect(uriRoom, '#select-room');
        // showRegisters(uri);
    });

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

    $("#checkAll").change(function(){
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

    $('.checkbox').change(function(){
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