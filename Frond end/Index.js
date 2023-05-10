var listAccount = [];
var listDepartment = [];
var idUpdate = -1; // khac -1 dang update, bang -1 dang update
var acc = JSON.parse(localStorage.getItem("accLogIn"));
var userLogin = acc.userName;
var passwordLogin = acc.password;
var totalPage = 0; // tong so trang
var page = 0;  // trang
var size = 5; // so phan tu trong 1 trang, phai trung vs gia tri size ban dau dat o #inputSize

// ham main
$(function () {
    getAllAccount();
    getListDepartment();
});

// methor get all account
function getAllAccount() {
    // jqajax
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/v1/accounts?page=${page}&size=${size}`,
        // data: "data", DUNG DE THEM VA SUA
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
        },
        dataType: "JSON",
        success: function (response) {
            // alert("call api thanh cong")
            // console.log(response);
            listAccount = [];
            listAccount = response.content; // response.content do sd page
            totalPage = response.totalPages;
            $("#tbodyAccount").empty(); // xoa trang bang truoc khi hien  thi : jqemty
            for (let i = 0; i < listAccount.length; i++) {
                // jqappend, selector vi tri noi, content gia tri muon noi
                $("#tbodyAccount").append(`
            <tr>
                <td>${listAccount[i].id}</td>
                <td>${listAccount[i].role}</td>
                <td>${listAccount[i].firstName}</td>
                <td>${listAccount[i].lastName}</td>
                <td>${listAccount[i].userName}</td>
                <td>${listAccount[i].departmentName}</td>
                <td>${listAccount[i].email}</td>
                <td>                  
                    <button type="button" class="btn btn-warning" onclick = "deleteAccountById(${listAccount[i].id})">Delete</button>                   
                </td>
                <td>
                    <button type="button" class="btn btn-primary" onclick = "getAccountById(${listAccount[i].id})">Edit</button>
                </td>
            </tr> 
                `);

            }
            showPage();
        },
        error: function (response) {
            alert("call api ko thanh cong");
        }
    });
}

// methor delete account by id
function deleteAccountById(id) {
    var isDelete = confirm("ban co muon xoa khong");
    if (isDelete) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/api/v1/accounts/" + id,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
            },
            // data: "data",
            // dataType: "dataType",
            success: function (response) {
                alert("xoa thanh cong");
                getAllAccount();
            },
            error: function (response) {
                alert("xoa that bai");
            }
        });
    }
}

// methor get all department
function getListDepartment() {

    // dung ajax de lay department tu database len
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/departments",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
        },
        dataType: "JSON",
        success: function (response) {
            listDepartment = [];
            listDepartment = response; // listDepartment = response, sua department ko dung page do  kieu bs3-select:h lua chon ko dung page
            // console.log(listDepartment);
            $("#inputDepartment").empty();
            for (let i = 0; i < listDepartment.length; i++) {
                $("#inputDepartment").append(`
                <option value="${listDepartment[i].id}">${listDepartment[i].name}</option>
                `);
            }
        },
        error: function (response) {
            alert("lay danh sach department ko thanh cong");
        }
    });
}

//  them moi account
function addAccount() {
    if (idUpdate != -1) {
        alert("Dang update, khong duoc them moi");
        return;
    } else {
        if (checkNUll() == false) {
            alert("vui long dien du thong tin")
        } else {
            // jqvalueGet: lay gia tri tung o
            var v_email = $("#inputEmail").val();
            var v_firstName = $("#inputFirstName").val();
            var v_lastName = $("#inputLastName").val();
            var v_userName = $("#inputUserName").val();
            var v_role = $("#inputRole").val();
            var v_department = $("#inputDepartment").val();

            // them account
            var newAccount = {
                email: v_email,
                firstName: v_firstName,
                lastName: v_lastName,
                userName: v_userName,
                role: v_role,
                departmentId: v_department,
                password: "123456",
            };

            // dung ajax de them account
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/api/v1/accounts",
                data: JSON.stringify(newAccount),  // chuyen json ve dang backend doc dc
                contentType: "application/json; charset=UTF-8",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
                },
                success: function (response) {
                    alert("them thanh cong");
                    // jqvalueSet
                    $("#inputEmail").val("");
                    $("#inputUserName").val("");
                    $("#inputFirstName").val("");
                    $("#inputLastName").val("");
                    $("#inputDepartmentId").val("");
                    $("#inputRole").val("");
                    $("#inputDepartment").val("");
                    getAllAccount();
                },
                errer: function (response) {
                    alert("them that bai");
                },
            });
        }
    }
}

// methor ham edit
function getAccountById(idParam) {
    // goi toi backend de lay thong tin account can sua
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/accounts/" + idParam,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
        },
        dataType: "JSON",
        success: function (response) {
            $("#inputEmail").val(response.email);
            $("#inputUserName").val(response.userName);
            $("#inputFirstName").val(response.firstName);
            $("#inputLastName").val(response.lastName);
            $("#inputDepartmentId").val(response.departmentId);
            $("#inputRole").val(response.role);

            // for (let i = 0; i < listDepartment.length; i++) {
            //     if(listDepartment[i].name == response.departmentName){
            //         $("#inputDepartmentName").val(listDepartment[i].id)
            //     }          
            // }

            var depID = listDepartment.find((dep) => dep.name == response.departmentName).id;
            $("#inputDepartmentName").val(depID);
            idUpdate = idParam;
        },
        error: function (response) {
            alert("lay Thong tin Account ko thanh cong");
        },
    });
}

function updateAccount() {
    if (idUpdate == -1) {
        alert("chon account can update truoc da");
        return;
    }

    // jqvalueGet: lay gia tri tung o
    var v_email = $("#inputEmail").val();
    var v_firstName = $("#inputFirstName").val();
    var v_lastName = $("#inputLastName").val();
    var v_userName = $("#inputUserName").val();
    var v_role = $("#inputRole").val();
    var v_department = $("#inputDepartment").val();

    // them account
    var newAccount = {
        email: v_email,
        firstName: v_firstName,
        lastName: v_lastName,
        userName: v_userName,
        role: v_role,
        departmentId: v_department,
        password: "123456",
    };

    // update thong tin
    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/api/v1/accounts/" + idUpdate,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(`${userLogin}:${passwordLogin}`));
        },
        data: JSON.stringify(newAccount),
        contentType: "application/json; charset=UTF-8",
        success: function (response) {
            alert("update thanh cong");
            idUpdate = -1;
            $("#inputEmail").val("");
            $("#inputUserName").val("");
            $("#inputFirstName").val("");
            $("#inputLastName").val("");
            $("#inputDepartmentId").val("");
            $("#inputRole").val("");
            $("#inputDepartmentName").val("");
            getAllAccount();
        },
        error: function (response) {
            alert("update  ko thanh cong");
        },
    });
}

function checkNUll() {
    var v_email = $("#inputEmail").val();
    var v_firstName = $("#inputFirstName").val();
    var v_lastName = $("#inputLastName").val();
    var v_userName = $("#inputUserName").val();

    if (v_email == "" || v_firstName == "" || v_lastName == "" || v_userName == "") {
        return false;
    }
    return true;
}

function showPage() {
    // alert(totalPage);
    $("#pageid").empty();
    $("#pageid").append(`<li><button type="button" onclick="changeFirst()" class="btn btn-default">&laquo;</button></li>`);
    for (let i = 1; i <= Number.parseInt(totalPage); i++) {
        if ((i - 1) == page) {
            $("#pageid").append(`
                <li><button type="button" onclick="changePage(${i - 1})" class="btn btn-default active" >${i}</button></li>
            `) // active hien thi dam so trang
        } else {
            $("#pageid").append(`
                <li><button type="button" onclick="changePage(${i - 1})" class="btn btn-default">${i}</button></li>
            `)
        }
    }
    $("#pageid").append(` <li><button type="button" onclick="changeLast()" class="btn btn-default">&raquo;</button></li>`);
}

function changeFirst() {
    page = 0;
    getAllAccount();
}

function changeLast() {
    page = totalPage - 1;
    getAllAccount();
}


function changePage(i) {
    // tranh get nhieu lan
    if (i == page)
        return;
    page = i;
    getAllAccount();
}

function changeSize(e) {
    // lay so phan tu muon hien thi trong 1 trang
    size = e.value;
    page = 0;
    getAllAccount();
}
