async function myBooking() {
    var token = localStorage.getItem("token");
    var url = 'http://localhost:8080/api/booking/user/my-booking';
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token
        })
    });
    var list = await response.json();
    var main = '';
    var total = 0;
    for(i=0; i<list.length; i++){
        total = Number(total)+Number(1)
        main +=
            `<tr>
            <td><a class="yls">#${list[i].id}</a></td>
            <td class="floatr">${list[i].createdTime} ${list[i].createdDate}</td>
            <td>${list[i].fromDate}</td>
            <td class="floatr">${list[i].numDate}</td>
            <td>${list[i].user.fullname}</td>
            <td class="floatr">${list[i].phone}</td>
            <td><span class="span_pending">${loadTrangThai(list[i].payStatus)}</span></td>
            <td class="floatr"><span class="span_"><span class="yls">${formatmoney(list[i].amountRoom + list[i].amountService)}</span></span></td>
            <td><button onclick="bookingRoomAndService(${list[i].id})" data-bs-toggle="modal" data-bs-target="#modaldeail" class="btn btn-primary">Chi tiết</button></td>
        </tr>`
    }
    document.getElementById("listBooking").innerHTML = main;
    document.getElementById("sldonhang").innerHTML = total+" đơn đặt phòng";
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