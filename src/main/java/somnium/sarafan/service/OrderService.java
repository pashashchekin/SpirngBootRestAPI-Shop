package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.*;
import somnium.sarafan.enums.OrderStatus;
import somnium.sarafan.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemService cartItemService;

    public Order createOrder(User user){
        Order order = new Order();
        ShoppingCart shoppingCart = user.getShoppingCart();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setDeliveryMethod("Самовызов");

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        for (CartItem cartItem : cartItemList){
            cartItem.setOrder(order);
        }
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

    public Order makePaided(long orderId){
        Order order = orderRepository.findById(orderId);
        order.setOrderStatus(OrderStatus.PAIDED);
        orderRepository.save(order);
        return orderRepository.save(order);
    }

    public Order makeSended(long orderId){
        Order order = orderRepository.findById(orderId);
        order.setOrderStatus(OrderStatus.SENDED);
        order.setShippingDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order makeAccepted(long orderId){
        Order order = orderRepository.findById(orderId);
        order.setOrderStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    public Order makeCancelled(long orderId){
        Order order = orderRepository.findById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

}
