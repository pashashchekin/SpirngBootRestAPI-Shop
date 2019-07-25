package somnium.sarafan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int qty;
    private int subtotal;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name="shopping_cart_id")
    @JsonIgnore
    private ShoppingCart shoppingCart;
}
