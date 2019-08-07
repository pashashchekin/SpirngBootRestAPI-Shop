package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.CartItem;
import somnium.sarafan.domain.Product;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.domain.User;
import somnium.sarafan.enums.ServerStatus;
import somnium.sarafan.service.CartItemService;
import somnium.sarafan.service.ProductService;
import somnium.sarafan.service.ShoppingCartService;
import somnium.sarafan.service.UserService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/cart")
@Api(value="Cart", description="REST API for shopping cart", tags = {"Cart"})
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
    public ResponseEntity shoppingCart(@PathVariable Long id) {
        Map<String,Object> responseBody = new HashMap<>();
        User user = userService.findById(id);
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCartService.updateShoppingCart(shoppingCart);
        responseBody.put("status", ServerStatus.SUCCESS);
        responseBody.put("message","shopping cart");
        responseBody.put("data", shoppingCart);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addToShoppingCart(Long itemId, Long userId, int qty) {
        Map<String,Object> responseBody = new HashMap<>();
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = user.getShoppingCart();
        Product product = productService.findById(itemId);
        CartItem cartItem = cartItemService.addProductToCartItem(product,user,qty);
        shoppingCartService.updateShoppingCart(shoppingCart);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","productItem added to cart");
        responseBody.put("data", shoppingCart);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateCartItem(Long itemId,int qty){
        Map<String,Object> responseBody = new HashMap<>();
        CartItem cartItem = cartItemService.findById(itemId);
        cartItemService.update(cartItem,qty);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","productItem updated");
        responseBody.put("data", cartItem);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteItem(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        cartItemService.delete(id);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","productItem deleted");
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity clearShoppingCart(Long id){
        Map<String,Object> responseBody = new HashMap<>();
        User user = userService.findById(id);
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCartService.clearShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        responseBody.put("status", ServerStatus.SUCCESS);
        responseBody.put("message", "shopping cart cleaned");
        responseBody.put("data", shoppingCart);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
