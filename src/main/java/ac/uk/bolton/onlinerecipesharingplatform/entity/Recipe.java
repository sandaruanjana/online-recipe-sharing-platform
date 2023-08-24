package ac.uk.bolton.onlinerecipesharingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String ingredients;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(nullable = false)
    private int preparation_time;

    @Column(nullable = false)
    private int serving_size;
    @ColumnDefault("0")
    private int likes = 0;
    @Column(nullable = false)
    private boolean is_approved = false;

    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
    @ManyToMany
    @JoinTable(name = "like_recipe_user",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likedUsers;
}
