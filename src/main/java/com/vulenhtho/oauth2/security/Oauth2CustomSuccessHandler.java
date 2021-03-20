package com.vulenhtho.oauth2.security;


import com.vulenhtho.oauth2.config.APIConstant;
import com.vulenhtho.oauth2.config.Constant;
import com.vulenhtho.oauth2.dto.UserDTO;
import com.vulenhtho.oauth2.dto.response.JwtResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Oauth2CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RestTemplate restTemplate;

    public Oauth2CustomSuccessHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Authentication newAuth = getJWTAndUserInfo(authentication);
        String targetUrl = determineTargetUrl(newAuth);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // call API to login or register with userinfo from google/facebook and get JWT
    private Authentication getJWTAndUserInfo(Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDTO userDTO = new UserDTO();

        if (authentication instanceof OAuth2AuthenticationToken) {
            String registrationId= ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            userDTO.setFullName(((OAuth2User) authentication.getPrincipal()).getAttribute("name"));
            userDTO.setEmail(((OAuth2User) authentication.getPrincipal()).getAttribute("email"));

            if (Constant.FACEBOOK_REGISTRATION_ID.equals(registrationId)){
                userDTO.setUserName(((OAuth2User) authentication.getPrincipal()).getAttribute("id"));
            } else if (Constant.GOOGLE_REGISTRATION_ID.equals(registrationId)){
                userDTO.setUserName(((OAuth2User) authentication.getPrincipal()).getAttribute("email"));
            }

        }

         HttpEntity<?> entity = new HttpEntity<Object>(userDTO, headers);
        JwtResponse jwtResponse = restTemplate.postForObject(APIConstant.HOST + "/api/account/oauth2Login", entity, JwtResponse.class);

        Set<GrantedAuthority> authoritySet = jwtResponse.getUserDTO().getRoles().stream()
                .map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getName())).collect(Collectors.toSet());

        CustomUserDetail customUserDetail = new CustomUserDetail(jwtResponse.getUserDTO().getUserName()
                , Constant.OAUTH2_DEFAULT_PASSWORD, authoritySet);

        customUserDetail.setToken(jwtResponse.getToken());
        customUserDetail.setType(jwtResponse.getType());
        customUserDetail.setFullName(jwtResponse.getUserDTO().getFullName());
        customUserDetail.setPhone(jwtResponse.getUserDTO().getPhone());
        customUserDetail.setAddress(jwtResponse.getUserDTO().getAddress());

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oAuth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            customUserDetail.setAttributes(oAuth2User.getAttributes());
            customUserDetail.setNameAttributeKey(oAuth2User.getName());

            authentication = new OAuth2AuthenticationToken(customUserDetail
                    , authoritySet
                    , ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private String determineTargetUrl(Authentication authentication) {
        String url = "";

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(authentication.getAuthorities());
        if (isAdmin(grantedAuthorities)) {
            url = "/admin/home";
        } else if (isSale(grantedAuthorities)) {
            url = "/home";
        } else if (isUser(grantedAuthorities)) {
            url = "/home";
        }

        return url;
    }


    private boolean isUser(Set<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(role -> Constant.ROLE_USER.equals(role.getAuthority()));
    }

    private boolean isSale(Set<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(role -> Constant.ROLE_SALE.equals(role.getAuthority()));
    }

    private boolean isAdmin(Set<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(role -> Constant.ROLE_ADMIN.equals(role.getAuthority()));
    }
}
