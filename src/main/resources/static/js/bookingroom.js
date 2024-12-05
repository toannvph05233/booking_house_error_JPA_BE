var listRoomArr = [];

async function loadCategoryRoomSelect() {
    var url = 'http://localhost:8080/api/category/public/findAll?type=ROOM';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    console.log(list);
    var main = '<option value="-1">Tất cả loại phòng</option>';
    for (i = 0; i < list.length; i++) {
        main += `<option value="${list[i].id}">${list[i].name}</option>`
    }
    document.getElementById("listcategoryselect").innerHTML = main
}


async function loadAllRoom() {
    var cateId = document.getElementById("listcategoryselect").value
    var from = document.getElementById("fromdate").value
    var songay = document.getElementById("songay").value
    var url = 'http://localhost:8080/api/booking/public/cal-date?from='+from+'&numDate='+songay;
    if(cateId != -1){
        url += '&id='+cateId
    }
    const response = await fetch(url, {
        method: 'GET'
    });
    var result = await response.json();
    var list = result.rooms
    var main = '';
    for (i = 0; i < list.length; i++) {
        var tienich = '';
        for(j=0; j<list[i].roomUtilities.length; j++){
            tienich += `<div class="col-sm-6 singletich">
            <i class="${list[i].roomUtilities[j].utilities.icon}"></i> ${list[i].roomUtilities[j].utilities.name}
        </div>`
        }
        main += `<div class="row singleroom">
        <div class="col-4">
            <img onclick="loadDetailRoom(${list[i].id})" src="${list[i].image}" data-bs-toggle="modal" data-bs-target="#modaldeail" class="imgroom pointer">
        </div>
        <div class="col-8">
            <div class="nameroomdiv">
                <span class="roomnames pointer">${list[i].name}</span>
                <span data-bs-toggle="modal" data-bs-target="#modaldeail" class="motaroom pointer">Xem mô tả & ảnh phòng</span>
            </div>
            <div class="d-flex numbedroom">
                <span><i class="fa fa-bed"></i> Số giường: ${list[i].numBed}</span>
                <span><i class="fa fa-users"></i> Số người tối đa: ${list[i].maxPeople}</span>
            </div>
            <div class="row">
                <div class="col-8">
                    <div class="row">${tienich}</div>
                </div>
                <div class="col-4">
                    <span class="priceroom">${formatmoney(list[i].price)}</span>
                    <span class="phongdem">/ phòng / đêm</span>
                    <label class="checkbox-custom"> Chọn phòng
                        <input id="inputcheck${list[i].id}" onchange="pushToList(this,${list[i].id},'${list[i].name}',${list[i].price})" type="checkbox"><span class="checkmark-checkbox"></span>
                    </label>
                </div>
            </div>
        </div>
    </div>`
    }
    document.getElementById("listRoom").innerHTML = main
    document.getElementById("sophongtrong").innerHTML = "("+list.length +' phòng)'
    document.getElementById("ngaytraphong").innerHTML = result.detailDate
    loadRoomSelect();
}


async function loadDetailRoom(id) {
    var url = 'http://localhost:8080/api/room/public/findById?id='+id;
    const response = await fetch(url, {
    });
    var result = await response.json();
    document.getElementById("descriptiondetail").innerHTML = result.description
    document.getElementById("imgdetailpro").src = result.image
    var main = "";
    for(i=0; i<result.roomImages.length; i++){
        main += `<div class="col-2 singdimg">
        <img onclick="clickImgdetail(this)" src="${result.roomImages[i].image}" class="imgldetail imgactive">
    </div>`
    }
    document.getElementById("listimgdetail").innerHTML = main
}

async function clickImgdetail(e) {
    var img = document.getElementsByClassName("imgldetail");
    for (i = 0; i < img.length; i++) {
        document.getElementsByClassName("imgldetail")[i].classList.remove('imgactive');
    }
    e.classList.add('imgactive')
    document.getElementById("imgdetailpro").src = e.src
}

function loadDaySelect(){
    var main = '';
    var curdate = new Date()
    for(i=1; i< 11; i++){
        var dateObj = new Date();
        dateObj.setDate(curdate.getDate() + i);

        let month = dateObj.getUTCMonth() + 1;
        let day = dateObj.getUTCDate();
        let year = dateObj.getUTCFullYear();
        newdate = day + "/" + month + "/" + year;

        main += `<option value="${i}">${i} Ngày - ${newdate}</option>`
    }
    document.getElementById("songay").innerHTML = main;
}

async function pushToList(e, id, roomname, price){
    if(e.checked == false){
        removeRoom(id);
    }
    else{
        var obj = {
            "id":id,
            "roomname":roomname,
            "price":price,
        }
        listRoomArr.push(obj);
    }
    loadRoomSelect();
}


function loadRoomSelect(){
    var main = '';
    for(i=0; i<listRoomArr.length;i++){
        main += `<div class="row">
        <div class="col-7"><span>${listRoomArr[i].roomname}</span></div>
        <div class="col-3"><span>${formatmoney(listRoomArr[i].price)}</span></div>
        <div onclick="removeRoom(${listRoomArr[i].id})" class="col-2"><i class="fa fa-trash pointer"></i></div>
    </div>`;
    try {
        document.getElementById("inputcheck"+listRoomArr[i].id).checked = true;
    } catch (error) {}
    }
    document.getElementById("listroomdc").innerHTML = main;
}

function removeRoom(id){
	listRoomArr = listRoomArr.filter(data => data.id != id);
    loadRoomSelect();
    document.getElementById("inputcheck"+id).checked = false;
}

function loadInitBooking(){
    var user = localStorage.getItem('user');
    if(user == null){
        return;
    }
    user = JSON.parse(user);
    document.getElementById("fullname").value = user.fullname
    document.getElementById("phone").value = user.phone
}

function checkOut(){
    if(token == null){
        toastr.warning("Bạn chưa đăng nhập");
        return;
    }
    var fromdate = document.getElementById("fromdate").value
    var songay = document.getElementById("songay").value

    if(listRoomArr.length == 0){
        toastr.warning("Bạn hãy chọn ít nhất 1 phòng");
        return;
    }
    var listId = [];
    for(i=0; i<listRoomArr.length; i++){
        listId.push(listRoomArr[i].id);
    }
    var obj = {
        "fromdate":fromdate,
        "songay":songay,
        "listroom":listId,
    }
    window.localStorage.setItem("bookingcart", JSON.stringify(obj));
    window.location.href = 'checkout';
}

async function loadRoomCheckout() {
    if(token == null){
        window.location.href = 'login';return;
    }
    var cart = localStorage.getItem("bookingcart");
    if(cart == null){
        window.location.href = 'booking-room';return;
    }
    cart = JSON.parse(cart);
    var url = 'http://localhost:8080/api/booking/public/get-room-by-listId';
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(cart.listroom)
    });
    var list = await response.json();
    var main = '';
    var totalAmount = 0;
    for (i = 0; i < list.length; i++) {
        main += ` <div class="row">
        <div class="col-lg-2 col-md-3 col-sm-3 col-3 colimgcheck">
            <img src="${list[i].image}" class="procheckout">
        </div>
        <div class="col-lg-7 col-md-6 col-sm-6 col-6">
            <span class="namecheck">${list[i].name}</span>
            <span class="colorcheck">Ngày đặt: ${cart.fromdate}, ${cart.songay} ngày</span>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-3 col-3 pricecheck">
            <span>${formatmoney(list[i].price)}</span>
        </div>
    </div>`
    totalAmount = Number(totalAmount) + Number(list[i].price)
    }
    document.getElementById("listRoomselect").innerHTML = main
    document.getElementById("totalAmount").innerHTML = formatmoney(totalAmount)
    document.getElementById("tiencoc").innerHTML = formatmoney(totalAmount*30/100)
}

async function requestPayMentMomo() {
    var cart = JSON.parse(localStorage.getItem("bookingcart"));
    var obj = {
        "ghichu":document.getElementById("ghichu").value,
        "fullname":document.getElementById("fullname").value,
        "phone":document.getElementById("phone").value,
        "cccd":document.getElementById("cccd").value,
        "fromdate":cart.fromdate,
        "songay":cart.songay,
        "listroom":cart.listroom,
    }
    window.localStorage.setItem('payinfor', JSON.stringify(obj));

    var returnurl = 'http://localhost:8080/payment';

    var urlinit = 'http://localhost:8080/api/booking/user/create-payment';

    var paymentDto = {
        "content": "thanh toán đặt cọc phòng",
        "returnUrl": returnurl,
        "listRoomId": cart.listroom,
    }
    console.log(paymentDto)
    const res = await fetch(urlinit, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(paymentDto)
    });
    var result = await res.json();
    if (res.status < 300) {
        window.open(result.url, '_blank');
    }
    if (res.status == exceptionCode) {
        toastr.warning(result.defaultMessage);
    }

}

async function createBooking() {
    var uls = new URL(document.URL)
    var orderId = uls.searchParams.get("orderId");
    var requestId = uls.searchParams.get("requestId");

    var obj = JSON.parse(window.localStorage.getItem("payinfor"));

    var bookingDto = {
        "fromDate": obj.fromdate,
        "numDate": obj.songay,
        "fullname": obj.fullname,
        "phone": obj.phone,
        "cccd": obj.cccd,
        "note": obj.ghichu,
        "requestId": requestId,
        "orderId": orderId,
        "listRoomId": obj.listroom,
    }
    var url = 'http://localhost:8080/api/booking/user/create-booking';
    var token = localStorage.getItem("token");
    const res = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(bookingDto)
    });
    var result = await res.json();
    if (res.status < 300) {
        document.getElementById("thanhcong").style.display = 'block'
    }
    if (res.status == exceptionCode) {
        document.getElementById("thatbai").style.display = 'block'
        document.getElementById("thanhcong").style.display = 'none'
        document.getElementById("errormess").innerHTML = result.defaultMessage
    }

}
