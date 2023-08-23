package ac.uk.bolton.onlinerecipesharingplatform.service;

import ac.uk.bolton.ecommercebackend.request.LoginRequest;
import ac.uk.bolton.ecommercebackend.request.SignupRequest;
import ac.uk.bolton.onlinerecipesharingplatform.dto.TokenDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UserDTO;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface UserService {
    AjaxResponse save(SignupRequest signupRequest);

    TokenDTO login(Authentication authentication, HttpServletRequest request, LoginRequest loginDTO);

    UserDTO getCurrentUser();
}
