package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.domain.Order;
import somnium.sarafan.domain.Product;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.CouponsRepository;
import somnium.sarafan.repository.OrderRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    CouponsRepository couponsRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<Coupon> getAllCoupons(){
        return couponsRepository.findAll();
    }

    public Coupon addCoupon(int percent){
        Coupon coupon = new Coupon();
        coupon.setActivateKey(UUID.randomUUID().toString());
        coupon.setStartDate(new Date());
        coupon.setEndDate(new Date());
        coupon.setPercent(percent);
        return couponsRepository.save(coupon);
    }

    public Coupon update(Coupon coupon, Date startDate, Date endDate, int percent){
        coupon.setActivateKey(UUID.randomUUID().toString());
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        coupon.setPercent(percent);
        return couponsRepository.save(coupon);
    }

    public void delete(Long id){
        couponsRepository.deleteById(id);
    }

    public Coupon findById(Long id){
        return couponsRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    public Order activateCoupon(long orderId, long coupId){
        int orderSum;
        int newOrderSum;
        Coupon coupon = couponsRepository.findById(coupId);
        Order order = orderRepository.findById(orderId);
        orderSum = order.getOrderTotalSum();
        newOrderSum = orderSum - ((orderSum * coupon.getPercent()) / 100);
        order.setOrderTotalSum(newOrderSum);
        return orderRepository.save(order);
    }
}
