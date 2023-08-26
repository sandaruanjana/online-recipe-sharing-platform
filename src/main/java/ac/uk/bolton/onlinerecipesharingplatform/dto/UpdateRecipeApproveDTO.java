package ac.uk.bolton.onlinerecipesharingplatform.dto;

import lombok.Data;

@Data
public class UpdateRecipeApproveDTO {
    private Long id;
    private int is_approved;
}
