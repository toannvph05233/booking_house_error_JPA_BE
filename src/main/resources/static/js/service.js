async function loadAllService() {
    var url = 'http://localhost:8080/api/services/public/findAll';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();

    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<div onclick="loadAService(${list[i].id})" data-bs-toggle="modal" data-bs-target="#modaldeail" class="col-sm-4 singledv pointer">
        <p class="titledv">${list[i].category.name}</p>
        <img src="${list[i].image}" class="blogimg pointer">
        <div class="contentbl">
          <p class="titleblog pointer">${list[i].name}</p>
          <span class="desdichvu">${list[i].description}</span>
        </div>
      </div>`
    }
    document.getElementById("listserviceindex").innerHTML = main
}


async function loadAService(id) {
    var url = 'http://localhost:8080/api/services/public/findById?id=' + id;
    const response = await fetch(url, {
        method: 'GET'
    });
    var result = await response.json();
    document.getElementById("imgdetailpro").src = result.image
    document.getElementById("detailnamepro").innerHTML = result.name
    document.getElementById("codepro").innerHTML = result.category.name
    document.getElementById("descriptiondetail").innerHTML = result.description
    document.getElementById("pricedetail").innerHTML = formatmoney(result.price)
    var main = ''
    for (i = 0; i < result.serviceImages.length; i++) {
        main += `<div class="col-sm-3 singdimg">
        <img onclick="clickImgdetail(this)" src="${result.serviceImages[i].image}" class="imgldetail imgactive">
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


async function loadCategoryService() {
    var url = 'http://localhost:8080/api/category/public/findAll?type=SERVICE';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<div class="col-sm-4">
        <p onclick="loadServiceByCategory(${list[i].id})" class="cateservicename">${list[i].name}</p>
    </div>`
    }
    document.getElementById("listcateservicename").innerHTML = main
}

async function loadCategoryRoom() {
    var url = 'http://localhost:8080/api/category/public/findAll?type=ROOM';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<div class="col-sm-4">
        <p class="cateservicename"><a href="booking-room?room=${list[i].id}">${list[i].name}</a></p>
        <a href="booking-room?room=${list[i].id}"><img src="${list[i].image}" class="imgcategory"></a>
    </div>`
    }
    document.getElementById("listcateroomname").innerHTML = main
}


async function loadServiceByCategory(id) {
    var url = 'http://localhost:8080/api/services/public/findByCategory?id='+id;
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();

    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<div onclick="loadAService(${list[i].id})" data-bs-toggle="modal" data-bs-target="#modaldeail" class="col-sm-4 singledv pointer">
        <p class="titledv">${list[i].category.name}</p>
        <img src="${list[i].image}" class="blogimg pointer">
        <div class="contentbl">
          <p class="titleblog pointer">${list[i].name}</p>
          <span class="desdichvu">${list[i].description}</span>
        </div>
      </div>`
    }
    document.getElementById("listserviceindex").innerHTML = main
}
