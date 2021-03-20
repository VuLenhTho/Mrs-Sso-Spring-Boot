package com.vulenhtho.oauth2.security;

import com.vulenhtho.oauth2.dto.CartDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CustomUserDetail extends User implements OAuth2User {

    private String fullName;

    private String phone;

    private String address;

    private String token;

    private String type;

    private CartDTO cartDTO;

    private  Map<String, Object> attributes;

    private  String nameAttributeKey;

    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return nameAttributeKey;
    }
}
