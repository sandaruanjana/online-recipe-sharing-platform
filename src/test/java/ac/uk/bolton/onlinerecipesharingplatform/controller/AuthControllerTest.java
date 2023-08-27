package ac.uk.bolton.onlinerecipesharingplatform.controller;

import ac.uk.bolton.onlinerecipesharingplatform.request.LoginRequest;
import ac.uk.bolton.onlinerecipesharingplatform.request.SignupRequest;
import ac.uk.bolton.onlinerecipesharingplatform.service.UserService;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupSuccess() {
        SignupRequest signupRequest = new SignupRequest(/* Initialize with required data */);

        when(userService.save(any())).thenReturn(AjaxResponse.success());

        AjaxResponse<Object> response = authController.signup(signupRequest);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(userService, times(1)).save(signupRequest);
    }

    @Test
    public void testLoginBadCredentials() {
        LoginRequest loginRequest = new LoginRequest("abc@gmail.com", "password");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        try {
            authController.login(request, loginRequest);
            fail("Expected BadCredentialsException to be thrown");
        } catch (BadCredentialsException e) {
            assertEquals("Invalid username or password", e.getMessage());
        }
        // Add more assertions based on your requirements
    }
}
