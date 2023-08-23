package ac.uk.bolton.onlinerecipesharingplatform.repository;

import ac.uk.bolton.onlinerecipesharingplatform.entity.RecipeInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeInteractionRepository extends JpaRepository<RecipeInteraction, Long> {
}
