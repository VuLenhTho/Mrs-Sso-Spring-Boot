package com.vulenhtho.oauth2.dto.response;


import com.vulenhtho.oauth2.dto.DiscountDTO;
import com.vulenhtho.oauth2.dto.request.ItemShowInCartDTO;
import com.vulenhtho.oauth2.dto.request.PageHeaderDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ItemsForCartAndHeader {

    private List<ItemShowInCartDTO> itemShowInCartDTOS = new ArrayList<>();

    private Set<DiscountDTO> discountDTOS = new HashSet<>();

    private PageHeaderDTO headerDTO;
}
