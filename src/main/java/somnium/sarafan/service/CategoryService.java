package somnium.sarafan.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.Category;
import somnium.sarafan.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, Category category){
        Category entity = categoryRepository.findById(id);
        BeanUtils.copyProperties(entity,category,"id");
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    public Category findOneById(long id) {
        return categoryRepository.findById(id);
    }

    public Category findOneByName(String name){
        return categoryRepository.findByName(name);
    }

}
