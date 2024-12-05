package com.web.controller.admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AllViewAdmin {

    @RequestMapping(value = {"/addblog"}, method = RequestMethod.GET)
    public String addblog() {
        return "admin/addblog.html";
    }

    @RequestMapping(value = {"/adddichvu"}, method = RequestMethod.GET)
    public String adddichvu() {
        return "admin/adddichvu.html";
    }

    @RequestMapping(value = {"/addroom"}, method = RequestMethod.GET)
    public String addroom() {
        return "admin/addroom.html";
    }

    @RequestMapping(value = {"/blog"}, method = RequestMethod.GET)
    public String blog() {
        return "admin/blog.html";
    }

    @RequestMapping(value = {"/booking"}, method = RequestMethod.GET)
    public String booking() {
        return "admin/booking.html";
    }

    @RequestMapping(value = {"/category"}, method = RequestMethod.GET)
    public String category() {
        return "admin/category.html";
    }

    @RequestMapping(value = {"/dichvu"}, method = RequestMethod.GET)
    public String dichvu() {
        return "admin/dichvu.html";
    }

    @RequestMapping(value = {"/doanhthu"}, method = RequestMethod.GET)
    public String doanhthu() {
        return "admin/doanhthu.html";
    }

    @RequestMapping(value = {"/empty-room"}, method = RequestMethod.GET)
    public String emptyRoom() {
        return "admin/empty-room.html";
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index() {
        return "admin/index.html";
    }

    @RequestMapping(value = {"/room"}, method = RequestMethod.GET)
    public String room() {
        return "admin/room.html";
    }

    @RequestMapping(value = {"/taikhoan"}, method = RequestMethod.GET)
    public String taikhoan() {
        return "admin/taikhoan.html";
    }

    @RequestMapping(value = {"/tienich"}, method = RequestMethod.GET)
    public String tienich() {
        return "admin/tienich.html";
    }

}
