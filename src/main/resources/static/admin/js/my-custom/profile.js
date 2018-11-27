$(document).ready(function () {
    var password = document.getElementById("newPassword")
        , confirm_password = document.getElementById("confirm-new-password");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords không trùng!");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;

    $('.alert').fadeOut(5000);
});