package ac.uk.bolton.onlinerecipesharingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
