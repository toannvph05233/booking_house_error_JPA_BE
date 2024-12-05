var listRoomArr = [];

async function emptyRoom() {
    $('#example').DataTable().destroy();
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
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td><img src="${list[i].image}" style="width:80px"></td>
                    <td>${list[i].name}</td>
                    <td>${formatmoney(list[i].price)}</td>
                    <td>${list[i].numBed}</td>
                    <td>${list[i].maxPeople}</td>
                    <td>${list[i].category.name}</td>
                    <td class="sticky-col">
                        <label class="checkbox-custom"> Chọn
                            <input id="inputcheck${list[i].id}" onchange="pushToList(this,${list[i].id},'${list[i].name}',${list[i].price})" type="checkbox"><span class="checkmark-checkbox"></span>
                        </label>
                    </td>
                </tr>`
    }
    document.getElementById("listroom").innerHTML = main
    document.getElementById("slphong").innerHTML = "("+list.length + " phòng)"
    document.getElementById("ngaytraphong").innerHTML = result.detailDate
    loadRoomSelect();
    $('#example').DataTable();
    document.getElementById("fromDatecr").value = document.getElementById("fromdate").value
    document.getElementById("numDatecr").value = document.getElementById("songay").value
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


function clearData(){

}


async function createBooking() {
    if(listRoomArr.length == 0){
        alert("Chưa có phòng nào được chọn!")
        return;
    }
    var ls = [];
    for(i=0; i<listRoomArr.length; i++){
        ls.push(listRoomArr[i].id);
    }
    var bookingDto = {
        "fromDate": document.getElementById("fromDatecr").value,
        "numDate": document.getElementById("numDatecr").value,
        "fullname": document.getElementById("fullname").value,
        "phone": document.getElementById("phone").value,
        "cccd": document.getElementById("cccd").value,
        "note": document.getElementById("note").value,
        "listRoomId": ls
    }
    var url = 'http://localhost:8080/api/booking/admin/create-booking';
    var token = localStorage.getItem("token");
    const res = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(bookingDto)
    });
    if (res.status < 300) {
        toastr.success("Thành công");
        await new Promise(r => setTimeout(r, 1000));
        window.location.reload();
    }
    else{
        toastr.error("Thất bại");
    }

}