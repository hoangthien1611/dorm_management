$(function () {
        function getAllArea() {
            $.ajax({
                type: "GET",
                url: "/dorm/area",
            })
            .done(function(data) {
                $('#content-page').html(data);
                console.log(data);
            })
            .fail(function (json) {
                var msg = json.responseJSON.message;
                alert(msg);
            });
        }
    function registerRoom() {
        $.ajax({
            type: "GET",
            url: "/dorm/register-room/list-room",
        })
        .done(function(data) {
            $('#content-page').html(data);
            console.log(data);
        })
        .fail(function (json) {
            var msg = json.responseJSON.message;
            alert(msg);
        });
    }
})
