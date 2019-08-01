package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.*;
import somnium.sarafan.service.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Api(value = "Order", description = "REST API for Order", tags = {"Order"})

public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CouponService couponService;

    @PostMapping("/checkout")
    public ResponseEntity checkout(Long userId){
        Map<String,Object> responseBody = new HashMap<>();
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        if (cartItemList.size() == 0){
            responseBody.put("status","FAILED");
            responseBody.put("message","Cart is empty");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        Order newOrder = orderService.createOrder(user);
        shoppingCartService.clearShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","order created");
        responseBody.put("data", newOrder);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getAllOrders(){
        Map<String,Object> responseBody = new HashMap<>();
        Collection<Order> data = orderService.getAllOrders();
        responseBody.put("status","SUCCESS");
        responseBody.put("message","list of orders");
        responseBody.put("data",data);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity getAllOrdersByUser(Long userId){
        Map<String,Object> responseBody = new HashMap<>();
        User user = userService.findById(userId);
        Collection<Order> data = orderService.getOrdersByUserId(user.getId());
        if (data.size() == 0){
            responseBody.put("status","SUCCESS");
            responseBody.put("message","order history is empty");
            return new ResponseEntity<>(responseBody,HttpStatus.OK);
        }
        responseBody.put("status","SUCCESS");
        responseBody.put("message","list of orders");
        responseBody.put("data",data);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/paid")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity paidOrder(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        Order order = orderService.makePaided(id);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","order paided");
        responseBody.put("data",order);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity sendOrder(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        Order order = orderService.makeSended(id);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","order sended");
        responseBody.put("data",order);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/accept")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity acceptOrder(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        Order order = orderService.makeAccepted(id);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","order accepted");
        responseBody.put("data",order);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity cancellOrder(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        Order order = orderService.makeCancelled(id);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","order cancelled");
        responseBody.put("data",order);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity activateGiftCoupon(Long orderId, String code) {
        Map<String,Object> responseBody = new HashMap<>();
        Coupon coupon = couponService.findByCode(code);
        if (coupon == null){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "coupon is not found");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        Order order = couponService.activateCoupon(orderId,code);
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "user activated");
        responseBody.put("data", order);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
