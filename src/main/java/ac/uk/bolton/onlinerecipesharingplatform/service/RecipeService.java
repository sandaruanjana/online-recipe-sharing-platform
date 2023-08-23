package ac.uk.bolton.onlinerecipesharingplatform.service;

import ac.uk.bolton.onlinerecipesharingplatform.dto.RecipeDTO;

import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface RecipeService {
    RecipeDTO createRecipe(RecipeDTO recipeDTO);
    RecipeDTO getRecipeById(Long id);
    List<RecipeDTO> getAllRecipes();
    List<RecipeDTO> getApprovedRecipes();
    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}
