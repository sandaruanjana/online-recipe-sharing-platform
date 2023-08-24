package ac.uk.bolton.onlinerecipesharingplatform.controller;

import ac.uk.bolton.onlinerecipesharingplatform.dto.LikeRecipeDTO;
import ac.uk.bolton.onlinerecipesharingplatform.service.UserService;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public AjaxResponse<Object> getCurrentUser() {
        return AjaxResponse.success(userService.getCurrentUser());
    }

    @PostMapping("/like/recipe")
    public AjaxResponse<Object> likeRecipe(@RequestBody LikeRecipeDTO likeRecipeDTO) {
        return userService.likeRecipe(likeRecipeDTO);
    }
}
