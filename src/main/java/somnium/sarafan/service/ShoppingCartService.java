package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.repository.ShoppingCartRepository;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        int cartTotal = 0;
        int cartTotalQty = 0;
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        for (CartItem cartItem: cartItemList){
            cartTotal += cartItem.getSubtotal();
            cartTotalQty += cartItem.getQty();
        }
        shoppingCart.setGrandTotalSum(cartTotal);
        shoppingCart.setGrandTotalQty(cartTotalQty);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public void clearShoppingCart(ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        for (CartItem cartItem : cartItemList) {
            cartItemService.delete(cartItem.getId());
        }
        shoppingCartRepository.save(shoppingCart);
    }
}
