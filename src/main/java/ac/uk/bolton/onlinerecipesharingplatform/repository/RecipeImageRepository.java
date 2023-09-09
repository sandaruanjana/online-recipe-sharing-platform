package ac.uk.bolton.onlinerecipesharingplatform.repository;

import ac.uk.bolton.onlinerecipesharingplatform.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    RecipeImage findByRecipeId(Long recipeId);
    void deleteByRecipeId(Long recipeId);
}
