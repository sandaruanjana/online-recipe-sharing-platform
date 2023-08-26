package ac.uk.bolton.onlinerecipesharingplatform.service.impl;

import ac.uk.bolton.onlinerecipesharingplatform.dto.LikeRecipeDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.TokenDTO;
import ac.uk.bolton.onlinerecipesharingplatform.dto.UserDTO;
import ac.uk.bolton.onlinerecipesharingplatform.entity.Recipe;
import ac.uk.bolton.onlinerecipesharingplatform.entity.User;
import ac.uk.bolton.onlinerecipesharingplatform.enums.RoleType;
import ac.uk.bolton.onlinerecipesharingplatform.exception.InternalServerErrorException;
import ac.uk.bolton.onlinerecipesharingplatform.repository.RecipeRepository;
import ac.uk.bolton.onlinerecipesharingplatform.repository.UserRepository;
import ac.uk.bolton.onlinerecipesharingplatform.request.LoginRequest;
import ac.uk.bolton.onlinerecipesharingplatform.request.SignupRequest;
import ac.uk.bolton.onlinerecipesharingplatform.service.UserService;
import ac.uk.bolton.onlinerecipesharingplatform.util.AjaxResponse;
import ac.uk.bolton.onlinerecipesharingplatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final JwtUtil jwtUtils;

    @Override
    public AjaxResponse save(SignupRequest signupRequest) {
        try {
            signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            User user = mapper.map(signupRequest, User.class);
            user.setRole(RoleType.ROLE_USER.name());
            user.setCreated_at(new Timestamp(System.currentTimeMillis()));
            User registeUser = userRepository.save(user);
            if (registeUser == null) {
                return AjaxResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
            }
            return AjaxResponse.success();
        } catch (Exception e) {
            return AjaxResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public TokenDTO login(Authentication authentication, HttpServletRequest request, LoginRequest loginDTO) {
        TokenDTO tokenDTO = new TokenDTO();

        try {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String accessToken = jwtUtils.generateToken(authentication.getName(), request.getServletPath(), roles);
            String refreshToken = jwtUtils.generateRefreshToken(authentication.getName(), request.getServletPath(), roles);
            Long tokenExpirationTime = jwtUtils.getTokenExpirationTime(accessToken);

            tokenDTO.setAccess_token(accessToken);
            tokenDTO.setRefresh_token(refreshToken);
            tokenDTO.setExpires_in(tokenExpirationTime);
            tokenDTO.setToken_type("Bearer");
            tokenDTO.setRole(roles.get(0));

        } catch (Exception e) {
            throw new InternalServerErrorException("Internal server error");
        }
        return tokenDTO;
    }

    @Override
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) auth.getPrincipal();
        String email = principal.getClaim("sub");

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return mapper.map(user, UserDTO.class);
    }

    @Override
    public AjaxResponse likeRecipe(LikeRecipeDTO likeRecipeDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) auth.getPrincipal();
        String email = principal.getClaim("sub");
        User user = userRepository.findByEmail(email);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(likeRecipeDto.getRecipeId());
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            
            if (recipe.getLikedUsers().contains(user)) {
                recipe.getLikedUsers().remove(user);
                recipe.setLikes(recipe.getLikes() - 1);
                Recipe recipeObj = recipeRepository.save(recipe);

                if (recipeObj != null) {
                    return AjaxResponse.success(likeRecipeDto);
                } else {
                    return AjaxResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
                }
            }

            if (recipe.getLikes() == 0) {
                recipe.setLikes(1);
            } else {
                recipe.setLikes(recipe.getLikes() + 1);
            }
            recipe.getLikedUsers().add(user);
            Recipe recipeObj = recipeRepository.save(recipe);

            if (recipeObj != null) {
                return AjaxResponse.success(likeRecipeDto);
            } else {
                return AjaxResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
            }
        } else {
            return AjaxResponse.error(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            String message = String.format("User %s not found", username);
            throw new UsernameNotFoundException(message);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            RoleType userRole = RoleType.valueOf(user.getRole());

            if (StringUtils.hasText(user.getRole()) && userRole != null) {
                authorities.add(new SimpleGrantedAuthority(userRole.name()));
            } else {
                throw new UsernameNotFoundException("User role not found");
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
    }
}
