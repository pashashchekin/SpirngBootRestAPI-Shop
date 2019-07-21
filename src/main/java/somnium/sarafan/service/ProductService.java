package somnium.sarafan.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.Product;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.ProductRepository;

import java.util.List;

@Service("productService")
public class ProductService  {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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

}
