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