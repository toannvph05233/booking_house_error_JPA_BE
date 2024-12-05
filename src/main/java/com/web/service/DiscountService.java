package com.web.service;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public double calculateDiscount(String type, double originalPrice) {
        double discount = 0.0;

        switch (type) {
            case "DIAMOND":
                discount = 0.20; // 20%
                break;
            case "GOLD":
                discount = 0.15; // 15%
                break;
            case "SILVER":
                discount = 0.10; // 10%
                break;
            default:
                discount = 0.0; // No discount
        }

        return originalPrice * (1 - discount);
    }
}

