package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.Order;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.domain.User;
import somnium.sarafan.service.CartItemService;
import somnium.sarafan.service.OrderService;
import somnium.sarafan.service.ShoppingCartService;
import somnium.sarafan.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
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
}
