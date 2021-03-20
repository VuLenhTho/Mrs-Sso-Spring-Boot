package com.vulenhtho.oauth2.service;

import com.vulenhtho.oauth2.dto.ItemDTO;
import com.vulenhtho.oauth2.dto.request.FilterProductRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    ModelAndView getWelcomePage();

    ModelAndView getListProductPage(FilterProductRequest filterProductRequest, boolean forAdmin);

    ModelAndView getDetailProductPage(Long productId);

    ModelAndView getDetailProductByAdmin(Long id);

    ModelAndView getCreateProductByAdmin();

    void addProductToCart(ItemDTO itemDTO);

    ModelAndView getCart();

    void updateCart(String productIds, String quantity, String productIdsToDelete);

    void updateBillInfo(HttpServletRequest request);

    void createBill();

}
