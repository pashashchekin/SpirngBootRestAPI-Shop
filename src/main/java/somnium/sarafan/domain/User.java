package somnium.sarafan.domain;

import lombok.Data;
import somnium.sarafan.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    private String email;
    private String activationCode;

    private Boolean active;
    private Boolean isAdmin;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JoinColumn(name="shoppingcart_id")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

}
