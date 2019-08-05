package somnium.sarafan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrandTotalSum() {
        return grandTotalSum;
    }

    public void setGrandTotalSum(int grandTotalSum) {
        this.grandTotalSum = grandTotalSum;
    }

    public int getGrandTotalQty() {
        return grandTotalQty;
    }

    public void setGrandTotalQty(int grandTotalQty) {
        this.grandTotalQty = grandTotalQty;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
