package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.*;
import somnium.sarafan.enums.OrderStatus;
import somnium.sarafan.repository.OrderRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    ShoppingCartService shoppingCartService;

    public Order createOrder(User user){
        Order order = new Order();
        Date date = new Date();
        ShoppingCart shoppingCart = user.getShoppingCart();
        order.setOrderDate(date);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setDeliveryMethod("Самовызов");

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        order.setCartItemList(cartItemList);
        order.setOrderTotalSum(shoppingCart.getGrandTotalSum());
        order.setOrderTotalQty(shoppingCart.getGrandTotalQty());
        order.setUser(user);

        return orderRepository.save(order);

    }

}
