package ac.uk.bolton.onlinerecipesharingplatform.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
}
