package ac.uk.bolton.onlinerecipesharingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@Entity
@Table(name = "recipe_iteractions")
public class RecipeInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private boolean liked;
}
