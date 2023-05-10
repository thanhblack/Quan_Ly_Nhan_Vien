$(function () {

});

function login() {

    // lay gia tri
    var v_userName = $("#inputUserName").val();
    var v_password = $("#inputPassword").val();
    var account = {
        userName: v_userName,
        password: v_password,
    };

    // goi toi api login o backend
    $.ajax({
        type: "GET",
        url: " http://localhost:8080/api/v1/auth/login",
        dataType: "JSON",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(`${v_userName}:${v_password}`));
        },
        success: function (response) {
            alert("Dang nhap thanh cong");
            localStorage.setItem("accLogIn", JSON.stringify(account)); // lua tai khoan o localStorage
            window.open("./index.html", "_self"); // trang can chuyen, vi tri can chuyen           
        },
        error: function (response) {
            alert("dang nhap that bai");
        }
    });
}

function forgotPassword(params) {
    window.open("./forgotPassword.html", "_self");
}

function sendEmail() {
    
    // lay email can thay doi passs
    var v_email = $("#inputEmail").val();

    // call API gui token de thay doi pass
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/auth/sendEmail?email=" + v_email,
        // data: "data",
        contentType: "application/json; charset=UTF-8",
        success: function (response) {
            alert("Đã gửi thong tin ve email");
        },
        error: function (response) {
            alert("Kiểm tra lại email");
        }
    });

}

function changPass() {

    // lay password
    var v_pass = $("#inputPassword").val();

    // lay token
    const queryString = window.location.search;
    console.log(queryString);
    const urlParams = new URLSearchParams(queryString);
    const token = urlParams.get('token');

    // call API den BE de thay doi mk
    $.ajax({
        type: "PUT",
        url: `http://localhost:8080/api/v1/auth/changePassword?token=${token}&newPassword=${v_pass}`,
        // data: "data",
        contentType: "application/json; charset=UTF-8",
        success: function (response) {
            alert("doi mk thanh cong");
            window.open("./login.html", "_self"); 
        },
        error: function (response) {
            alert("Sai đường link hoặc đường link hết hạn");
        }
    });
}

function toLogin() {
    window.open("./login.html", "_self"); 
}