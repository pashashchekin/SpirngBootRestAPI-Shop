package somnium.sarafan.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @ApiModelProperty (notes = "The database generated product ID")
    private Long id;

    @Size(min = 1, max = 30)
    @Column(name = "name")
    @ApiModelProperty (notes = "The product name")
    private String name;

    @Digits(integer = 6, fraction = 2)
    @Column(name = "price")
    @ApiModelProperty (notes = "The product price")
    private BigDecimal price;

    @Size(min = 1, max = 100)
    @Column(name = "description")
    @ApiModelProperty (notes = "The product description")
    private String description;
}
