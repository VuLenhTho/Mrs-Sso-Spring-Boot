package com.vulenhtho.oauth2.dto.response;

import com.vulenhtho.oauth2.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;

    private String type;

    private UserDTO userDTO;
}
