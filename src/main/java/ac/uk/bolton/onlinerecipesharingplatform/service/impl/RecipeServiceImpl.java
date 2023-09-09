package ac.uk.bolton.onlinerecipesharingplatform.service.impl;

import ac.uk.bolton.onlinerecipesharingplatform.dto.RecipeDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UpdateRecipeApproveDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UserDTO;
import ac.uk.bolton.onlinerecipesharingplatform.entity.Recipe;
import ac.uk.bolton.onlinerecipesharingplatform.entity.RecipeImage;
import ac.uk.bolton.onlinerecipesharingplatform.entity.User;
import ac.uk.bolton.onlinerecipesharingplatform.repository.RecipeImageRepository;
import ac.uk.bolton.onlinerecipesharingplatform.repository.RecipeRepository;
import ac.uk.bolton.onlinerecipesharingplatform.service.CategoryService;
import ac.uk.bolton.onlinerecipesharingplatform.service.RecipeService;
import ac.uk.bolton.onlinerecipesharingplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
        recipe.set_approved(false);
        recipe.setCreated_at(new Timestamp(System.currentTimeMillis()));
        recipe.setCategory(categoryService.getCategoryById(recipeDTO.getCategory_id()).orElse(null));

        UserDTO currentUser = userService.getCurrentUser();

        recipe.setUser(modelMapper.map(currentUser, User.class));
        recipe = recipeRepository.save(recipe);

        String imageUrl = saveBase64Image(recipe.getId(), recipeDTO.getImageUrl());

        RecipeImage recipeImage = new RecipeImage();
        recipeImage.setRecipe(recipe);
        recipeImage.setImage_url(imageUrl);
        recipeImage.setCreated_at(new Timestamp(System.currentTimeMillis()));
        recipeImageRepository.save(recipeImage);

        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return null;
        }

        RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);

        RecipeImage recipeImage = recipeImageRepository.findByRecipeId(recipe.getId());

        if (recipeImage != null) {
            recipeDTO.setImageUrl(recipeImage.getImage_url());
        }

        categoryService.getCategoryById(recipe.getCategory().getId()).ifPresent(category -> {
            recipeDTO.setCategory_name(category.getName());
        });

        recipe.getLikedUsers().forEach(user -> {
            if (user.getId().equals(userService.getCurrentUser().getId())) {
                recipeDTO.setCurrent_user_liked(true);
            }
        });

        return recipeDTO;
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipe -> {
                    RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);
                    RecipeImage recipeImage = recipeImageRepository.findByRecipeId(recipe.getId());

                    if (recipeImage != null) {
                        recipeDTO.setImageUrl(recipeImage.getImage_url());
                    }

                    categoryService.getCategoryById(recipe.getCategory().getId()).ifPresent(category -> {
                        recipeDTO.setCategory_name(category.getName());
                    });

                    recipe.getLikedUsers().forEach(user -> {
                        if (user.getId().equals(userService.getCurrentUser().getId())) {
                            recipeDTO.setCurrent_user_liked(true);
                        }
                    });

                    return recipeDTO;
                })
                .toList();
    }


    @Override
    public List<RecipeDTO> getAllByApproved(int isApproved) {
        return recipeRepository.findAllByIsApproved(isApproved == 1)
                .stream()
                .map(recipe -> {
                    RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);
                    RecipeImage recipeImage = recipeImageRepository.findByRecipeId(recipe.getId());

                    if (recipeImage != null) {
                        recipeDTO.setImageUrl(recipeImage.getImage_url());
                    }

                    categoryService.getCategoryById(recipe.getCategory().getId()).ifPresent(category -> {
                        recipeDTO.setCategory_name(category.getName());
                    });

                    recipe.getLikedUsers().forEach(user -> {
                        if (user.getId().equals(userService.getCurrentUser().getId())) {
                            recipeDTO.setCurrent_user_liked(true);
                        }
                    });

                    return recipeDTO;
                })
                .toList();
    }

    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return null;
        }
        recipe = modelMapper.map(recipeDTO, Recipe.class);
        recipe = recipeRepository.save(recipe);
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public RecipeDTO updateApproval(UpdateRecipeApproveDTO updateRecipeApproveDTO) {
        Recipe recipe = recipeRepository.findById(updateRecipeApproveDTO.getId()).orElse(null);
        if (recipe == null) {
            return null;
        }
        recipe.set_approved(updateRecipeApproveDTO.getIs_approved() == 1);
        recipe = recipeRepository.save(recipe);
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        recipeImageRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDTO getRecipeImageByRecipeId(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return null;
        }

        RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);

        RecipeImage recipeImage = recipeImageRepository.findByRecipeId(recipe.getId());

        if (recipeImage != null) {
            recipeDTO.setImageUrl(recipeImage.getImage_url());
        }

        categoryService.getCategoryById(recipe.getCategory().getId()).ifPresent(category -> {
            recipeDTO.setCategory_name(category.getName());
        });

        return recipeDTO;
    }

    private String saveBase64Image(Long recipeId, String base64Image) {

        int commaIndex = base64Image.indexOf(",");
        if (commaIndex != -1) {
            base64Image = base64Image.substring(commaIndex + 1);
        }

        // Generate a unique filename based on recipeId
        String filename = recipeId + "_" + System.currentTimeMillis() + ".jpg";

        try {
            // Decode the Base64 image string into bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Save the decoded image bytes to the upload directory
            Files.write(Paths.get(uploadDirectory, filename), imageBytes);
        } catch (IOException e) {
            log.error("Error saving image", e);
        }

        return filename; // Return the image URL (filename)
    }
}
