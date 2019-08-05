package somnium.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "tb_product")
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

    @Column(name = "price")
    @ApiModelProperty (notes = "The product price")
    private int price;

    @Size(min = 1, max = 100)
    @Column(name = "description")
    @ApiModelProperty (notes = "The product description")
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="category_id")
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
