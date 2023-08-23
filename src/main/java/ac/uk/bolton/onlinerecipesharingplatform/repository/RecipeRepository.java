package ac.uk.bolton.onlinerecipesharingplatform.repository;

import ac.uk.bolton.onlinerecipesharingplatform.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE r.is_approved = true")
    List<Recipe> findAllByApproved();
}
