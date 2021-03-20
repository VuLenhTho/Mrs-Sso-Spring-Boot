package com.vulenhtho.oauth2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoToReportDTO {

    private Long id;

    private String name;

    private String thumbnail;

    private Long quantity;

}
