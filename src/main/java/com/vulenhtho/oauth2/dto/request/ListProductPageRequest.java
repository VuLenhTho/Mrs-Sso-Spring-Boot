package com.vulenhtho.oauth2.dto.request;

import com.vulenhtho.oauth2.dto.response.ProductWebWindowViewResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductPageRequest {

    private List<ProductWebWindowViewResponseDTO> products;

    private Integer totalPage;

    private Integer currentPage;

    private PageHeaderDTO header;
}
