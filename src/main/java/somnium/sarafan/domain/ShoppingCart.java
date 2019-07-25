package somnium.sarafan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int grandTotalSum;
    private int grandTotalQty;

    @OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<CartItem> cartItemList;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
}
