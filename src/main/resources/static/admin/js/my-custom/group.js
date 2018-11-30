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
    });

    $('.alert').fadeOut(5000);
});