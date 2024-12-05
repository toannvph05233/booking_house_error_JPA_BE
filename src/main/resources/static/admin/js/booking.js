async function allBooking() {
    $('#example').DataTable().destroy();
    var from = document.getElementById("from").value
    var to = document.getElementById("to").value
    var url = 'http://localhost:8080/api/booking/admin/all-room';
    if(from != "" && to != ""){
        url+= "?from="+from+"&to="+to;
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    var list = await response.json();
    console.log(list);
    var main = '';
    for(i=0; i<list.length; i++){
        main +=
            `<tr>
            <td>${list[i].id}</td>
            <td class="floatr">${list[i].createdTime} ${list[i].createdDate}</td>
            <td>${list[i].fromDate}</td>
            <td class="floatr">${list[i].numDate}</td>
            <td>${list[i].fullname}</td>
            <td class="floatr">${list[i].phone}</td>
            <td><span class="span_pending">${loadTrangThai(list[i].payStatus)}</span></td>
            <td class="floatr"><span class="span_"><span class="yls">${formatmoney(list[i].amountRoom + list[i].amountService)}</span></span></td>
            <td>
                <i onclick="bookingRoomAndService(${list[i].id})" data-bs-toggle="modal" data-bs-target="#modaldeail" class="fa fa-eye iconaction"></i>
                <i onclick="loadIdBooking(${list[i].id}, '${list[i].payStatus}')" data-bs-toggle="modal" data-bs-target="#capnhattrangthai" class="fa fa-edit iconaction"></i><br>
                <br><i onclick="deleteBooking(${list[i].id})" class="fa fa-trash iconaction"></i>
                <i onclick="setIdCreateService(${list[i].id})" data-bs-toggle="modal" data-bs-target="#addService" class="fa fa-plus iconaction"></i><br>
            </td>
        </tr>`
    }
    document.getElementById("listBooking").innerHTML = main;
    $('#example').DataTable();
}


async function bookingRoomAndService(id) {
    var url = 'http://localhost:8080/api/booking-room/public/find-by-booking?id='+id;
    const response = await fetch(url, {
        method: 'POST'
    });
    var list = await response.json();
    var main = '';
    for(i=0; i<list.length; i++){
        main +=
            `<tr>
            <td><img src="${list[i].room.image}" class="imgdetailacc"></td>
            <td>${list[i].room.name}</td>
            <td>${formatmoney(list[i].price)}</td>
        </tr>`
    }
    document.getElementById("listBookingRoom").innerHTML = main;


    var url = 'http://localhost:8080/api/booking-service/public/find-by-booking?id='+id;
    const res = await fetch(url, {
        method: 'POST'
    });
    var list = await res.json();
    var main = '';
    for(i=0; i<list.length; i++){
        main +=
            `<tr>
            <td><img src="${list[i].services.image}" class="imgdetailacc"></td>
            <td>${list[i].services.name}</td>
            <td>${formatmoney(list[i].price)}</td>
            <td>${list[i].createdDate}, ${list[i].createdTime}</td>
            <td>${list[i].quantity}</td>
            <td><i onclick="deleteBkService(${list[i].id})" class="fa fa-trash iconaction"></i></td>
        </tr>`
    }
    document.getElementById("listBookingService").innerHTML = main;
}



function loadTrangThai(name){
    if(name == "DEPOSITED"){
        return "<span class='dadatcoc'>Đã đặt cọc</span>";
    }
    if(name == "PAID"){
        return "<span class='dathanhtoan'>Đã thanh toán</span>";
    }
    if(name == "CANCELLED"){
        return "<span class='dahuy'>Đã hủy</span>";
    }
}

function loadIdBooking(id, sstName){
    document.getElementById("idbooking").value = id;
    document.getElementById("sstname").value = sstName;
}

async function deleteBooking(id, sttName){
    var con = confirm("Xác nhận xóa lịch đặt này?")
    if (con == false) {
        return;
    }
    if(sttName == "PAID"){
        toastr.warning("Lịch đã thanh toán đủ, không thể xóa");
    }

    var url = 'http://localhost:8080/api/booking/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "xóa thành công!",
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


async function updateStatus() {
    var idbooking = document.getElementById("idbooking").value
    var sstname = document.getElementById("sstname").value
    var url = 'http://localhost:8080/api/booking/admin/update-status?id=' + idbooking + '&status=' + sstname;
    const res = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    if (res.status < 300) {
        toastr.success("Cập nhật trạng thái thành công!");
        $("#capnhattrangthai").modal("hide");
        allBooking();
    }
    if (res.status == exceptionCode) {
        var result = await res.json()
        toastr.warning(result.defaultMessage);
    }
}


async function loadServiceSelect() {
    var url = 'http://localhost:8080/api/services/public/findAll';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<option value="${list[i].id}">${list[i].name} - ${formatmoney(list[i].price)}</option>`
    }
    document.getElementById("listservice").innerHTML = main
    const ser = $("#listservice");
    ser.select2({
        placeholder: "Chọn dịch vụ",
    });
}

async function loadTableService(){
    var listId = $("#listservice").val()
    var url = 'http://localhost:8080/api/services/public/find-by-list-id';
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(listId)
    });
    var list = await response.json();

    var main = "";
    for(i=0; i< list.length; i++){
        main += `<tr id="trser${list[i].id}">
        <td>${list[i].name}</td>
        <td>${formatmoney(list[i].price)}</td>
        <td><input value="0" id="quantityser${list[i].id}"></td>
    </tr>`
    }
    document.getElementById("listServiceSelect").innerHTML = main
}

function setIdCreateService(id){
    document.getElementById("idbookingcreate").value = id
}

async function saveBookingService(){
    var listId = $("#listservice").val()
    var id = document.getElementById("idbookingcreate").value
    var list = []
    for(i=0; i< listId.length; i++){
        var obj = {
            "id":listId[i],
            "quantity":document.getElementById("quantityser"+listId[i]).value
        }
        list.push(obj);
    }
    var url = 'http://localhost:8080/api/booking-service/admin/create?id='+id;


    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(list)
    });
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "thêm thành công!",
                type: "success"
            },
            function() {
                $("#addService").modal("hide");
                allBooking();
            });
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}


async function deleteBkService(id) {
    var con = confirm("Xác nhận xóa dịch vụ này?")
    if (con == false) {
        return;
    }
    var url = 'http://localhost:8080/api/booking-service/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "xóa thành công!",
                type: "success"
            },
            function() {
                window.location.reload();
                $("#modaldeail").modal("hide");
            });
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}