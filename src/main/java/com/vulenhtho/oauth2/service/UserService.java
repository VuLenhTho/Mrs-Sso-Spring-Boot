package com.vulenhtho.oauth2.service;

import com.vulenhtho.oauth2.dto.response.ChangeUserAndResult;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    ChangeUserAndResult updateByAdmin(HttpServletRequest request);

    ChangeUserAndResult createByAdmin(HttpServletRequest request);

    String deletesByAdmin(HttpServletRequest request);

    boolean isLogged();

    boolean isAdmin();
}
