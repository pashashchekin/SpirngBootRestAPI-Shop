package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.Product;
import somnium.sarafan.enums.ServerStatus;
import somnium.sarafan.service.ProductService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/products")
@Api(value="Products", description="REST API for products" , tags = { "Products" })
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value =  "Get all products", tags = { "Products" })
    @GetMapping
    public ResponseEntity listProducts(){
        Map<String,Object> responseBody = new HashMap<>();
        Collection<Product> data = productService.getAllProducts();
        responseBody.put("status", ServerStatus.SUCCESS);
        responseBody.put("message","list of products");
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);

    }

    @ApiOperation(value =  "Get a one product by ID", tags = { "Products" })
    @GetMapping("{id}")
    public ResponseEntity getProductById(@PathVariable Long id){
        Map<String,Object> responseBody = new HashMap<>();
        Product findingProduct = productService.findById(id);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","product");
        responseBody.put("data", findingProduct);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @ApiOperation(value =  "Create a new product", tags = { "Products" })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity addProduct(@Valid @RequestBody Product product){
        Map<String,Object> responseBody = new HashMap<>();
        Product newProduct = productService.save(product);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","product created");
        responseBody.put("data", newProduct);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ApiOperation(value =  "Update a currently  product", tags = { "Products" })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product entity){
        Map<String,Object> responseBody = new HashMap<>();
        Product updatedProduct = productService.update(id,entity);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","product updated");
        responseBody.put("data", updatedProduct);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @ApiOperation(value =  "Delete a product", tags = { "Products" })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Map<String,Object> responseBody = new HashMap<>();
        productService.delete(id);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","product deleted");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("addToCategory")
    public ResponseEntity addProductToCategory(Long productId, Long categoryId){
        Map<String,Object> responseBody = new HashMap<>();
        Product product = productService.addCategoryToProduct(productId,categoryId);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","product added to category");
        responseBody.put("data", product);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
