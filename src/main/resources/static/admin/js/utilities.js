var token = localStorage.getItem("token");
async function loadTienIch() {
    $('#example').DataTable().destroy();
    var url = 'http://localhost:8080/api/utilities/public/findAll';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td><i class="${list[i].icon} iconnameti"></td>
                    <td>${list[i].name}</td>
                    <td class="sticky-col">
                        <i onclick="deleteTienIch(${list[i].id})" class="fa fa-trash-alt iconaction"></i>
                        <a onclick="loadATi(${list[i].id})" data-bs-toggle="modal" data-bs-target="#themdanhmuc"><i class="fa fa-edit iconaction"></i></a>
                    </td>
                </tr>`
    }
    document.getElementById("listti").innerHTML = main
    $('#example').DataTable();
}



async function loadATi(id) {
    var url = 'http://localhost:8080/api/utilities/admin/findById?id=' + id;
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    var result = await response.json();
    document.getElementById("idti").value = id
    document.getElementById("name").value = result.name
    document.getElementById("icon").value = result.icon
}

async function saveTienIch() {
    var idti = document.getElementById("idti").value
    var name = document.getElementById("name").value
    var icon = document.getElementById("icon").value
    
    var url = 'http://localhost:8080/api/utilities/admin/create';
    var tienIch = {
        "id": idti,
        "name": name,
        "icon": icon
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(tienIch)
    });
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "thêm/sửa tiện ích thành công!",
                type: "success"
            },
            function() {
                window.location.reload();
            });
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}

async function deleteTienIch(id) {
    var con = confirm("Bạn chắc chắn muốn xóa tiện ích này?");
    if (con == false) {
        return;
    }
    var url = 'http://localhost:8080/api/utilities/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    if (response.status < 300) {
        toastr.success("xóa thành công!");
        await new Promise(r => setTimeout(r, 1000));
        window.location.reload();
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}



function clearInput(){
    document.getElementById("idti").value = ""
    document.getElementById("name").value = ""
    document.getElementById("icon").value = ""
}