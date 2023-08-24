package ac.uk.bolton.onlinerecipesharingplatform.controller;


import ac.uk.bolton.onlinerecipesharingplatform.dto.TokenDTO;
import ac.uk.bolton.onlinerecipesharingplatform.exception.InternalServerErrorException;
import ac.uk.bolton.onlinerecipesharingplatform.request.LoginRequest;
import ac.uk.bolton.onlinerecipesharingplatform.request.SignupRequest;
import ac.uk.bolton.onlinerecipesharingplatform.service.UserService;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public AjaxResponse<Object> signup(@RequestBody SignupRequest signupRequest) {
        try {
            userService.save(signupRequest);
        } catch (DataIntegrityViolationException e) {
            return AjaxResponse.error(HttpStatus.CONFLICT, "Username already exists");
        } catch (Exception e) {
            return AjaxResponse.error("Unknown error occurred");
        }
        return AjaxResponse.success();
    }

    @PostMapping("/login")
    public AjaxResponse<Object> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authentication.isAuthenticated()) {
                TokenDTO tokenDTO = userService.login(authentication, request, loginRequest);
                return AjaxResponse.success(tokenDTO);
            }
        } catch (AuthenticationException e) {
            if (e.getMessage().equals("Bad credentials")) {
                throw new BadCredentialsException("Invalid username or password");
            }

            if (e.getMessage().equals("Incorrect result size: expected 1, actual 0")) {
                throw new BadCredentialsException("Invalid username or password");
            }

            throw new InternalServerErrorException("Unknown error occurred");
        } catch (Exception e) {
            return AjaxResponse.error("Unknown error occurred");
        }

        return AjaxResponse.error(HttpStatus.BAD_REQUEST);
    }
}
