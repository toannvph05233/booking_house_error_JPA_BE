package com.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AllViewUser {

    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public String account() {
        return "user/account.html";
    }

    @RequestMapping(value = {"/blog"}, method = RequestMethod.GET)
    public String blog() {
        return "user/blog.html";
    }

    @RequestMapping(value = {"/blogdetail"}, method = RequestMethod.GET)
    public String blogdetail() {
        return "user/blogdetail.html";
    }

    @RequestMapping(value = {"/booking-room"}, method = RequestMethod.GET)
    public String bookingRoom() {
        return "user/booking-room.html";
    }

    @RequestMapping(value = {"/checkout"}, method = RequestMethod.GET)
    public String checkout() {
        return "user/checkout.html";
    }

    @RequestMapping(value = {"/confirm"}, method = RequestMethod.GET)
    public String confirm() {
        return "user/confirm.html";
    }

    @RequestMapping(value = {"/forgot"}, method = RequestMethod.GET)
    public String forgot() {
        return "user/forgot.html";
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index() {
        return "user/index.html";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "user/login.html";
    }

    @RequestMapping(value = {"/payment"}, method = RequestMethod.GET)
    public String payment() {
        return "user/payment.html";
    }

    @RequestMapping(value = {"/regis"}, method = RequestMethod.GET)
    public String regis() {
        return "user/regis.html";
    }

    @RequestMapping(value = {"/service"}, method = RequestMethod.GET)
    public String service() {
        return "user/service.html";
    }

}
