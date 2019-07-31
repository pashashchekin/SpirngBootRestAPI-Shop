package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.service.CouponService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
@Api(value = "Coupons", description = "REST API for Coupons", tags = {"Coupons"})
public class CouponsController {

    @Autowired
    CouponService couponService;

    @PostMapping
    public ResponseEntity addCoupon(int percent){
        Map<String,Object> responseBody = new HashMap<>();
        Coupon newCoupon = couponService.addCoupon(percent);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","coupon created");
        responseBody.put("data", newCoupon);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
