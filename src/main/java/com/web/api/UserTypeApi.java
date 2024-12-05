package com.web.api;

import com.web.entity.User;
import com.web.repository.UserRepository;
import com.web.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_type")
public class UserTypeApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountService discountService;

    @GetMapping("/{userId}/type")
    public ResponseEntity<String> getUserType(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user.getType());
    }

    @GetMapping("/{userId}/discount")
    public ResponseEntity<Double> getDiscountedPrice(@PathVariable Long userId, @RequestParam double originalPrice) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        double discountedPrice = discountService.calculateDiscount(user.getType(), originalPrice);
        return ResponseEntity.ok(discountedPrice);
    }
}

