package somnium.sarafan.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.*;
import somnium.sarafan.enums.OrderStatus;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    UserService userService;

    public Order createOrder(User user){
        Order order = new Order();
        ShoppingCart shoppingCart = user.getShoppingCart();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setDeliveryMethod("Самовызов");

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        order.setCartItemList(cartItemList);
        order.setOrderTotalSum(shoppingCart.getGrandTotalSum());
        order.setOrderTotalQty(shoppingCart.getGrandTotalQty());
        order.setUser(user);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order makePaided(Long orderId){
        Order order = this.findById(orderId);
        order.setOrderStatus(OrderStatus.PAIDED);
        orderRepository.save(order);
        return orderRepository.save(order);
    }

    public Order makeSended(Long orderId){
        Order order = this.findById(orderId);
        order.setOrderStatus(OrderStatus.SENDED);
        order.setShippingDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order makeAccepted(Long orderId){
        Order order = this.findById(orderId);
        order.setOrderStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    public Order makeCancelled(Long orderId){
        Order order = this.findById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

}
