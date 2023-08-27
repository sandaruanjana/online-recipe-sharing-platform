package ac.uk.bolton.onlinerecipesharingplatform.service;

import ac.uk.bolton.onlinerecipesharingplatform.dto.RecipeDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UpdateRecipeApproveDTO;

import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface RecipeService {
    RecipeDTO createRecipe(RecipeDTO recipeDTO);
    RecipeDTO getRecipeById(Long id);
    List<RecipeDTO> getAllRecipes();
    List<RecipeDTO> getAllByApproved(int isApproved);
    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
    RecipeDTO updateApproval(UpdateRecipeApproveDTO updateRecipeApproveDTO);
    void deleteRecipe(Long id);
    RecipeDTO getRecipeImageByRecipeId(Long id);
}
