package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.domain.Order;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.CouponRepository;
import somnium.sarafan.repository.OrderRepository;
import somnium.sarafan.utils.CodeConfig;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponsRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<Coupon> getAllCoupons(){
        return couponsRepository.findAll();
    }

    public Coupon addCoupon(int percent,Date startDate, Date endDate){
        Coupon coupon = new Coupon();
        CodeConfig codeConfig = CodeConfig.length(8);
        coupon.setActivateKey(CodeConfig.generate(codeConfig));
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        coupon.setPercent(percent);
        return couponsRepository.save(coupon);
    }

    public Coupon update(Coupon coupon, Date startDate, Date endDate, int percent){
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        coupon.setPercent(percent);
        return couponsRepository.save(coupon);
    }

    public Coupon findByCode(String code){
        return couponsRepository.findByActivateKey(code);
    }

    public Coupon findById(Long id){
        return couponsRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    public Order activateCoupon(long orderId, String code){
        int orderSum;
        int newOrderSum;
        Coupon coupon = couponsRepository.findByActivateKey(code);
        Order order = orderRepository.findById(orderId);
        orderSum = order.getOrderTotalSum();
        newOrderSum = orderSum - ((orderSum * coupon.getPercent()) / 100);
        order.setOrderTotalSum(newOrderSum);
        couponsRepository.delete(coupon);
        return orderRepository.save(order);
    }




}
