package somnium.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import somnium.sarafan.domain.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {

}
