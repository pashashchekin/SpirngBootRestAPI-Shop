package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.service.CouponService;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
@Api(value = "Coupons", description = "REST API for Coupons", tags = {"Coupons"})
public class CouponController {

    @Autowired
    CouponService couponService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity getAllCoupons(){
        Map<String,Object> responseBody = new HashMap<>();
        Collection<Coupon> data = couponService.getAllCoupons();
        responseBody.put("status","SUCCESS");
        responseBody.put("message","list of coupons");
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity addCoupon(int percent,
                                    @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
                                    @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate){
        Map<String,Object> responseBody = new HashMap<>();
        Coupon newCoupon = couponService.addCoupon(percent, startDate, endDate);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","coupon created");
        responseBody.put("data", newCoupon);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity updateCoupon(@PathVariable  Long id,
                                       int percent,
                                       @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
                                       @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate){
        Map<String,Object> responseBody = new HashMap<>();
        Coupon coupon = couponService.findById(id);
        couponService.update(coupon,startDate,endDate,percent);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","coupon updated");
        responseBody.put("data", coupon);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }
}
