package com.vulenhtho.oauth2.dto.request;



import com.vulenhtho.oauth2.dto.DiscountDTO;
import com.vulenhtho.oauth2.dto.SubCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoToCreateProductDTO {

    private List<DiscountDTO> discountDTOS = new ArrayList<>();

    private List<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();
}
