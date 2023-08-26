package ac.uk.bolton.onlinerecipesharingplatform.controller;

import ac.uk.bolton.onlinerecipesharingplatform.dto.RecipeDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UpdateRecipeApproveDTO;
import ac.uk.bolton.onlinerecipesharingplatform.service.RecipeService;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestController
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public AjaxResponse<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeDTO);
        return AjaxResponse.success(createdRecipe);
    }

    @GetMapping("/{id}")
    public AjaxResponse<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipeDTO = recipeService.getRecipeById(id);

        if (recipeDTO == null)
            return AjaxResponse.error("Recipe not found");

        return AjaxResponse.success(recipeDTO);
    }

    @GetMapping
    public AjaxResponse<List<RecipeDTO>> getAllRecipes() {
        return AjaxResponse.success(recipeService.getAllRecipes());
    }

    @GetMapping("/approved")
    public AjaxResponse<List<RecipeDTO>> getApprovedRecipes() {
        return AjaxResponse.success(recipeService.getAllByApproved(1));
    }

    @GetMapping("/unapproved")
    public AjaxResponse<List<RecipeDTO>> getUnapprovedRecipes() {
        return AjaxResponse.success(recipeService.getAllByApproved(0));
    }

    @PutMapping("/{id}")
    public AjaxResponse<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO updatedRecipe = recipeService.updateRecipe(id, recipeDTO);
        return AjaxResponse.success(updatedRecipe);
    }

    @PatchMapping("/approve")
    public AjaxResponse<RecipeDTO> approveRecipe(@RequestBody UpdateRecipeApproveDTO updateRecipeApproveDTO) {
        RecipeDTO updatedRecipe = recipeService.updateApproval(updateRecipeApproveDTO);
        return AjaxResponse.success(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
