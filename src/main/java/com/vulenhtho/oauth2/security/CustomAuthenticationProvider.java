package com.vulenhtho.oauth2.security;

import com.vulenhtho.oauth2.config.APIConstant;
import com.vulenhtho.oauth2.dto.UserDTO;
import com.vulenhtho.oauth2.dto.response.JwtResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final RestTemplate restTemplate;

    public CustomAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(authentication.getName());
        userDTO.setPassword(authentication.getCredentials().toString());

        HttpEntity<?> entity = new HttpEntity<Object>(userDTO, headers);
        JwtResponse jwtResponse = restTemplate.postForObject(APIConstant.HOST + "/api/auth/login", entity, JwtResponse.class);

        Set<GrantedAuthority> authoritySet = jwtResponse.getUserDTO().getRoles().stream()
                .map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getName())).collect(Collectors.toSet());

        CustomUserDetail customUserDetail = new CustomUserDetail(jwtResponse.getUserDTO().getUserName()
                , authentication.getCredentials().toString(), authoritySet);

        customUserDetail.setToken(jwtResponse.getToken());
        customUserDetail.setType(jwtResponse.getType());
        customUserDetail.setFullName(jwtResponse.getUserDTO().getFullName());
        customUserDetail.setPhone(jwtResponse.getUserDTO().getPhone());
        customUserDetail.setAddress(jwtResponse.getUserDTO().getAddress());

        return new UsernamePasswordAuthenticationToken(customUserDetail, userDTO.getPassword(), authoritySet);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
