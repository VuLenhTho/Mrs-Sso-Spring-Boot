package com.vulenhtho.oauth2.dto.response;

import com.vulenhtho.oauth2.dto.UserDTO;
import lombok.Data;

@Data
public class ChangeUserAndResult {
    private UserDTO userDTO;

    private String result;
}
