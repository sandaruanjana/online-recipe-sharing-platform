package ac.uk.bolton.onlinerecipesharingplatform.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRequest {
    private String email;
    private String password;
}
