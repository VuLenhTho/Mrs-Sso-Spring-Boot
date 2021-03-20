package com.vulenhtho.oauth2.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public final class SecurityUtil {
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken){
            return ((OAuth2User) authentication.getPrincipal()).getAttribute("name");
        }
        return authentication.getName();
    }
}
