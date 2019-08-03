package somnium.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import somnium.sarafan.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);
    Category findByName(String name);

}
