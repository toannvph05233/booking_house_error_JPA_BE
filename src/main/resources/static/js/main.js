var token = localStorage.getItem("token");
const exceptionCode = 417;

async function loadMenu() {
    var dn = `
    <span class="nav-item dropdown pointermenu gvs menulink">
        <span class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-user"></i> Tài khoản</span>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="account">Tài khoản</a></li>
            <li onclick="logout()"><a class="dropdown-item" href="#">Đăng xuất</a></li>
        </ul>
    </span>
    `
    if (token == null) {
        dn = `<a href="login" class="pointermenu gvs menulink"><i class="fa fa-user"></i> Đăng ký/ Đăng nhập</a>`
    }
    var menu =
        `<nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand navbar-toggler" href="index"><img class="imglogo" src="image/logo.png"></a>
            <span>
                <i data-bs-toggle="modal" data-bs-target="#modalsearch" class="fa fa-search navbar-toggler"></i>
                <i class="fa fa-shopping-bag navbar-toggler"> <span class="slcartmenusm">0</span></i>
            </span>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="linktop"><a href="index"><img class="imglogo" src="image/logo.png"></a></li>
                <li class="nav-item"><a class="nav-link menulink" href="index">Trang chủ</a></li>
                <li class="nav-item"><a class="nav-link menulink" href="blog">Bài viểt</a></li>
                <li class="nav-item"><a class="nav-link menulink" href="service">Dịch vụ</a></li>
                <li class="nav-item"><a class="nav-link menulink" href="booking-room">Đặt phòng</a></li>
            </ul>
            <div class="d-flex right10p">
                ${dn}
            </div>
            <div class="d-flex right10p">
                <a href="tel:1900%201833" class="phonemenu"><i class="fa fa-phone"></i> 1900 1833</a>
            </div>
            <div class="d-flex">
                <a href="" class="linkidmenu"><i class="fab fa-facebook icmenu pointer"></i></a>
                <a href="" class="linkidmenu"><i class="fab fa-youtube icmenu pointer"></i></a>
                <a href="" class="linkidmenu"><i class="fab fa-instagram pointer"></i></a>
            </div>
            </div>
        </div>
    </nav>`
    document.getElementById("menu").innerHTML = menu
    try { loadFooter(); } catch (error) {}

}


function loadFooter() {
    var foo = `<footer class="text-center text-lg-start text-muted">
    <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
      <div class="me-5 d-none d-lg-block"><img src="image/logo.webp" alt=""></div>
      <div>
        <a href="" class="me-4 text-reset"><i class="fab fa-facebook-f"></i></a>
        <a href="" class="me-4 text-reset"><i class="fab fa-twitter"></i></a>
        <a href="" class="me-4 text-reset"><i class="fab fa-google"></i></a>
        <a href="" class="me-4 text-reset"><i class="fab fa-instagram"></i></a>
        <a href="" class="me-4 text-reset"><i class="fab fa-linkedin"></i></a>
        <a href="" class="me-4 text-reset"><i class="fab fa-github"></i></a>
      </div>
    </section>
    <section class="">
      <div class=" text-center text-md-start mt-5">
        <div class="row mt-3">
          <div class="col-md-2 col-lg-2 col-xl-3 mx-auto mb-4">
            <h6 class="text-uppercase fw-bold mb-4">HỖ TRỢ KHÁCH HÀNG 24/7</h6>
            <p><a href="#!" class="text-reset">Điều khoản dịch vụ</a></p>
            <p><a href="#!" class="text-reset">Liên hệ hỗ trợ</a></p>
            <p><a href="#!" class="text-reset">Câu hỏi thường gặp</a></p>
          </div>
          <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
            <h6 class="text-uppercase fw-bold mb-4">DỊCH VỤ CUNG CẤP</h6>
            <p><a href="#!" class="text-reset">Nhà</a></p>
            <p><a href="#!" class="text-reset">Căn hộ</a></p>
            <p><a href="#!" class="text-reset">Resort</a></p>
            <p><a href="#!" class="text-reset">Biệt thự</a></p>
            <p><a href="#!" class="text-reset">Nhà khách</a></p>
          </div>
          <div class="col-md-4 col-lg-4 col-xl-2 mx-auto mb-4">
            <h6 class="text-uppercase fw-bold mb-4">VỀ CHÚNG TÔI</h6>
            <div>
                Mường Thanh Luxury là phân khúc khách sạn hạng sang cao cấp nhất của Mường Thanh, nằm ở các thành phố lớn và trung tâm du lịch nổi tiếng trong nước và quốc tế. Quy mô lớn và đẳng cấp khác biệt, Mường Thanh Luxury mang đến cho khách hàng không gian nghỉ dưỡng tuyệt vời mang đậm giá trị Việt đến từ dịch vụ tận tâm và văn hóa ẩm thực bản địa độc đáo.
            </div>
          </div>
          <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                <img src="image/footer.png" class="imgfooter">
          </div>
        </div>
      </div>
    </section>
</footer>`
    document.getElementById("footer").innerHTML = foo;
}

async function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.replace('login')
}


function formatmoney(money) {
    const VND = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
    return VND.format(money);
}
