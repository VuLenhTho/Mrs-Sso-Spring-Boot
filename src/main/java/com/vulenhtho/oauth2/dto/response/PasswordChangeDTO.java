package com.vulenhtho.oauth2.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {

    private String currentPassword;

    private String newPassword;
}
