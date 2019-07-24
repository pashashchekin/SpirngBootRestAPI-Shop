package somnium.sarafan.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.Product;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.domain.User;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.CartItemRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    public CartItem addProductToCartItem(Product product, User user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if(product.getId().equals(cartItem.getProduct().getId())) {
                cartItem.setQty(cartItem.getQty()+qty);
//                cartItem.setSubtotal(product.getPrice().multiply(new BigDecimal(qty)));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setProduct(product);

        cartItem.setQty(qty);
//        cartItem.setSubtotal(product.getPrice().multiply(new BigDecimal(qty)));
        cartItem = cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem findById(Long id){
        return cartItemRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    public CartItem save(CartItem entity){
        return cartItemRepository.save(entity);
    }

    public CartItem update(Long id, CartItem entity){
        CartItem product = this.findById(id);
        BeanUtils.copyProperties(entity,product,"id");
        return cartItemRepository.save(product);
    }

    public void delete(Long id){
        cartItemRepository.deleteById(id);
    }

}
