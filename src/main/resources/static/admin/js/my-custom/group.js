$(document).ready(function () {
    var baseUrl = 'http://localhost:8080';

    $(document).on("click", ".btn-edit", function () {
        var id = $(this).closest('tr').children('td.id').text();
        var name = $(this).closest('tr').children('td.name').text();

        $('#id').val(id);
        $('#name').val(name);
    });

    $(document).on("click", "#view-group", function () {
        var groupId = $('#select-group').val();
        console.log("group: " + groupId);

        var url = baseUrl + '/admin/group/detail?group=' + groupId;
        window.location = url;
    });

    $(document).on("click", ".btn-add-action", function () {
        var group = $('#select-group option:selected').text();
        var groupId = $('#select-group').val();
        console.log("groupName: " +group);

        $('#groupId').val(groupId);
        $('#groupName').val(group);

        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/admin/group/detail/get-actions-add/' + groupId,
            success: function(result) {
                console.log('actions: ' + result.length);
                var html = '';
                if (result != null && result.length > 0) {
                    result.forEach(function (item, index) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                } else {
                    html = '<option value="0">Không còn action nào để thêm!</option>';
                }
                $("#actionId").html(html);
            },
            error: function () {
                alert('Error! Không thêm được!');
            }
        });
    });

    $('.alert').fadeOut(5000);
});