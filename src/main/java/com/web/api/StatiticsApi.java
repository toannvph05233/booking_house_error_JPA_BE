package com.web.api;

import com.web.enums.PayStatus;
import com.web.repository.BookingRepository;
import com.web.repository.RoomRepository;
import com.web.repository.UserRepository;
import com.web.utils.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@CrossOrigin
public class StatiticsApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;




    @GetMapping("/admin/number-admin")
    public Double numberAdmin(){
        return userRepository.countAdmin(Contains.ROLE_ADMIN);
    }

    @GetMapping("/admin/number-room")
    public Long numberProduct(){
        return roomRepository.count();
    }

    @GetMapping("/admin/revenue-year")
    public List<Double> doanhThu(@RequestParam("year") Integer year){
        List<Double> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Double sum = bookingRepository.calDt(i, year, PayStatus.PAID);
            if(sum == null){
                sum = 0D;
            }
            list.add(sum);
        }
        return list;
    }

    @GetMapping("/admin/revenue-this-month")
    public Double doanhThuThangNay(){
        Date date = new Date(System.currentTimeMillis());
        String[] str = date.toString().split("-");
        Integer year = Integer.valueOf(str[0]);
        Integer month = Integer.valueOf(str[1]);
        return bookingRepository.calDt(month, year, PayStatus.PAID);
    }
    @GetMapping("/admin/revenue-quarter")
    public List<Double> revenueQuarter(@RequestParam("year") Integer year) {
        List<Double> quarterlyRevenue = new ArrayList<>();
        for (int quarter = 1; quarter <= 4; quarter++) {
            Double sum = bookingRepository.calRevenueByQuarter(quarter, year, PayStatus.PAID);
            if (sum == null) {
                sum = 0D;
            }
            quarterlyRevenue.add(sum);
        }
        return quarterlyRevenue;
    }

}
