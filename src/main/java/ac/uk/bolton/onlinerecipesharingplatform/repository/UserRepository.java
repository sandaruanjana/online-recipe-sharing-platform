package ac.uk.bolton.onlinerecipesharingplatform.repository;

import ac.uk.bolton.onlinerecipesharingplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
