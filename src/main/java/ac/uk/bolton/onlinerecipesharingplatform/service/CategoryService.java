package ac.uk.bolton.onlinerecipesharingplatform.service;

import ac.uk.bolton.onlinerecipesharingplatform.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    boolean deleteCategory(Long id);
}
