function getAllRoomsByAreaId(idArea) {
    // $('#' + idArea ).css('background-color', '#d2d2d2');
    $.ajax({
        type: "GET",
        url: "/dorm/room/area/" + idArea,
    })
    .done(function(data) {
        $('#list-all-rooms').html(data);
        console.log(data);
    })
    .fail(function (json) {
        var msg = json.responseJSON.message;
        // alert(msg);
    });
}
function getSubsistence(roomId) {
    // $("#bangPhiDN_" + roomId).html("<center><img src='../../static/public/listRoom/images/loading.gif' width='20' /></center>");
    var month = document.getElementById("month_" + roomId).value;
    var year = document.getElementById("year_" + roomId).value;
    $.ajax({
        type: "GET",
        url: "/dorm/room/getSubsistence/idRoom=" + roomId + "/month=" + month + "/year=" + year,
        success: function (data) {
            console.log(data);
            $("#tableSubsistenceOfRoom_" + roomId).html(data);
        }
    });
}
