package ac.uk.bolton.onlinerecipesharingplatform.repository;

import ac.uk.bolton.onlinerecipesharingplatform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
