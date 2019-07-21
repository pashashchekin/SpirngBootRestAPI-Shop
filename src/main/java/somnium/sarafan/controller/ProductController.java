package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.Product;
import somnium.sarafan.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("products")
@Api(value="products", description="Operations pertaining to messages")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value =  "Get all products", response = Iterable.class)
    @GetMapping
    private ResponseEntity<List<Product>> findAllProducts(){
        return  ResponseEntity.ok(productService.getAllProducts());
    }

    @ApiOperation(value =  "Get a one product by ID", response = Iterable.class)
    @GetMapping("{id}")
    private ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }


    @ApiOperation(value =  "Create a new product", response = Iterable.class)
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @ApiOperation(value =  "Update a currently  product", response = Iterable.class)
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product entity){
        return ResponseEntity.ok(this.productService.update(id,entity));
    }

    @ApiOperation(value =  "Delete a product", response = Iterable.class)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

}
