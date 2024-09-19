package com.project.config;

import com.project.BDs.Repositories.UsersRepository;
import com.project.BDs.Services.UsersService;
import com.project.BDs.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsersService usersService;

    @Autowired
    OAuth2LoginSuccessHandler(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String,Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();
        String name = attributes.getOrDefault("name", "").toString();
        Users users = usersService.getUserByEmail(email);
        if(users != null) {
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(users.getRole())),
                    attributes, "sub");
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser,
                    List.of(new SimpleGrantedAuthority(users.getRole())),
                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        } else {
            Users user = new Users();
            user.setEmail(email);
            user.setName(name);
            usersService.createUser(user);
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole())),
                    attributes, "sub");
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser,
                    List.of(new SimpleGrantedAuthority(user.getRole())),
                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        }

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:3000/pizzas");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
