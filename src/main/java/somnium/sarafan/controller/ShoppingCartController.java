package somnium.sarafan.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.Product;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.domain.User;
import somnium.sarafan.service.CartItemService;
import somnium.sarafan.service.ProductService;
import somnium.sarafan.service.ShoppingCartService;
import somnium.sarafan.service.UserService;

import java.util.List;

@RestController
@RequestMapping("cart")
@Api(value="cart", description="Operations pertaining to shopping cart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<ShoppingCart> shoppingCart(@PathVariable Long id) {
        User user = userService.findById(id);
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCartService.updateShoppingCart(shoppingCart);
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(shoppingCart));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addToShoppingCart(Long itemId, Long userId, int qty) {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = user.getShoppingCart();
        Product product = productService.findById(itemId);
        CartItem cartItem = cartItemService.addProductToCartItem(product,user,qty);
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(shoppingCart));
    }

    @PutMapping("update")
    public ResponseEntity<CartItem> updateProduct(Long itemId,int qty){
        CartItem cartItem = cartItemService.findById(itemId);
        return ResponseEntity.ok(cartItemService.update(cartItem,qty));
    }

    @DeleteMapping("/remove")
    public void deleteItem(Long id){
        cartItemService.delete(id);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ShoppingCart> clearShoppingCart(Long id){
        User user = userService.findById(id);
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCartService.clearShoppingCart(shoppingCart);
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(shoppingCart));
    }

}
