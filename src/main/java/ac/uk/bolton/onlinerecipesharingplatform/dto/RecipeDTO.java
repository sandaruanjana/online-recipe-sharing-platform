package ac.uk.bolton.onlinerecipesharingplatform.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link ac.uk.bolton.onlinerecipesharingplatform.entity.Recipe}
 */
@Data
public class RecipeDTO implements Serializable {
    Long id;
    String title;
    String ingredients;
    String instructions;
    int preparation_time;
    int serving_size;
    Timestamp created_at;
    String imageUrl;
    Long category_id;
    String category_name;
    int likes;
    boolean current_user_liked;


}
