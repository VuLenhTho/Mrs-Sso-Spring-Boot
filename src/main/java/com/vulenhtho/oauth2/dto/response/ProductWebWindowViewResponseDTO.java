package com.vulenhtho.oauth2.dto.response;

import com.vulenhtho.oauth2.dto.enumeration.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWebWindowViewResponseDTO {
    private Long id;

    private String name;

    private Long price;

    private Long originalPrice;

    private String vnPrice;

    private ProductStatus status;

    private String thumbnail;

    private Boolean hot;

    private Boolean trend;

    private Boolean isDiscount;

}
