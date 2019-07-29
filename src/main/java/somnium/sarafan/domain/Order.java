package somnium.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import somnium.sarafan.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date orderDate;
    private Date shippingDate;
    private OrderStatus orderStatus;
    private String deliveryMethod;
    private int orderTotalSum;
    private int orderTotalQty;

    @OneToMany(mappedBy = "order")
    private List<CartItem> cartItemList;

    @OneToOne(cascade=CascadeType.ALL)
    private ShippingAddress shippingAddress;

    @ManyToOne
    @JsonIgnore
    private User user;
}
