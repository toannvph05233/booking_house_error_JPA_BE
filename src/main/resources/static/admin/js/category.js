var token = localStorage.getItem("token");
async function loadCategory() {
    $('#example').DataTable().destroy();
    var url = 'http://localhost:8080/api/category/public/findAll';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td><img src="${list[i].image}" style="width:80px"></td>
                    <td>${list[i].name} ${list[i].isPrimary==true?'<span class="dmchinh"> <i class="fa fa-check-circle"></i> danh mục chính</span>':''}</td>
                    <td>${list[i].categoryType}</td>
                    <td class="sticky-col">
                        <i onclick="deleteCategory(${list[i].id})" class="fa fa-trash-alt iconaction"></i>
                        <a onclick="loadACategory(${list[i].id})" data-bs-toggle="modal" data-bs-target="#themdanhmuc"><i class="fa fa-edit iconaction"></i></a>
                    </td>
                </tr>`
    }
    document.getElementById("listcategory").innerHTML = main
    $('#example').DataTable();
}



async function loadACategory(id) {
    var url = 'http://localhost:8080/api/category/admin/findById?id=' + id;
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    var result = await response.json();
    document.getElementById("idcate").value = id
    document.getElementById("catename").value = result.name
    document.getElementById("typecate").value = result.categoryType
    document.getElementById("imgpreview").src = result.image
    linkImage = result.image
}

async function loadAllType() {
    var url = 'http://localhost:8080/api/category/public/get-all-type-category';
    const response = await fetch(url, {
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<option value="${list[i]}">${list[i]}</option>`
    }
    document.getElementById("typecate").innerHTML = main
}


var linkImage = ''
async function saveCategory() {
    var idcate = document.getElementById("idcate").value
    var catename = document.getElementById("catename").value
    var typecate = document.getElementById("typecate").value

    const filePath = document.getElementById('fileimage')
    const formData = new FormData()
    formData.append("file", filePath.files[0])
    var urlUpload = 'http://localhost:8080/api/public/upload-file';
    const res = await fetch(urlUpload, {
        method: 'POST',
        body: formData
    });
    if (res.status < 300) {
        linkImage = await res.text();
    }

    var url = 'http://localhost:8080/api/category/admin/create';
    var category = {
        "id": idcate,
        "name": catename,
        "image": linkImage,
        "categoryType": typecate
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(category)
    });
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "thêm/sửa danh mục thành công!",
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

async function deleteCategory(id) {
    var con = confirm("Bạn chắc chắn muốn xóa danh mục này?");
    if (con == false) {
        return;
    }
    var url = 'http://localhost:8080/api/category/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    if (response.status < 300) {
        toastr.success("xóa danh mục thành công!");
        await new Promise(r => setTimeout(r, 1000));
        window.location.reload();
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}



function clearInput(){
    document.getElementById("idcate").value = ""
    document.getElementById("catename").value = ""
}