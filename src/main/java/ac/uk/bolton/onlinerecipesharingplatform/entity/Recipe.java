package ac.uk.bolton.onlinerecipesharingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

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

    @Column(nullable = false)
    private boolean is_approved = false;

    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
