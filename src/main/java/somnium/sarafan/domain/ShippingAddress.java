package somnium.sarafan.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "tb_address")
public class ShippingAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String shippingCity;
    private String shippingStreet;
    private String shippingZipcode;

    @OneToOne
    private Order order;
}
