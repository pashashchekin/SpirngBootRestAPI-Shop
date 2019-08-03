package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import somnium.sarafan.domain.Category;
import somnium.sarafan.domain.Product;
import somnium.sarafan.service.CategoryService;
import somnium.sarafan.service.ProductService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Api(value = "Category", description = "REST API for Category", tags = {"Category"})
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity allCategories(){
        Map<String, Object> responseBody =  new HashMap<>();
        Collection<Category> data = categoryService.findAll();
        if(data.size() == 0){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "categories is not found");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        responseBody.put("status","SUCCESS");
        responseBody.put("message","list of categories");
        responseBody.put("data",data);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getProdutcsByCategory(@PathVariable Long id){
        Map<String, Object> responseBody =  new HashMap<>();
        List<Product> data = productService.getProductsByCategory(id);
        if (data.size() == 0){
            responseBody.put("status","ERROR");
            responseBody.put("message","products is not found");
            return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);

        }
        responseBody.put("status","SUCCESS");
        responseBody.put("message","products in category");
        responseBody.put("data",data);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }
}
