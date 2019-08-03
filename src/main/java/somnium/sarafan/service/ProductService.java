package somnium.sarafan.service;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.Category;
import somnium.sarafan.domain.Product;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.CategoryRepository;
import somnium.sarafan.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService  {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    public Product save(Product entity){
        return productRepository.save(entity);
    }

    public Product update(Long id, Product entity){
        Product product = this.findById(id);
        BeanUtils.copyProperties(entity,product,"id");
        return productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public Product addCategoryToProduct(Long productId, long categoryId){
        Product product = this.findById(productId);
        Category category = categoryRepository.findById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(long categoryId){
        Category category =  categoryRepository.findById(categoryId);
        return category.getProducts().stream().collect(Collectors.toList());
    }

}
