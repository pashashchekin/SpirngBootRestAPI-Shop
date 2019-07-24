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
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(shoppingCart));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addToShoppingCart(Long itemId, Long userId) {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = user.getShoppingCart();
        Product product = productService.findById(itemId);
        CartItem cartItem = cartItemService.addProductToCartItem(product,user,2);
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(shoppingCart));
    }

}
