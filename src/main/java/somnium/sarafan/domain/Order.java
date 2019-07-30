package somnium.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import somnium.sarafan.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime shippingDate;

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
