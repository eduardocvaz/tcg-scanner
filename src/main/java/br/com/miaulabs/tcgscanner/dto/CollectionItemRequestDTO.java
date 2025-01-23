package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionItemRequestDTO {

    private Long id;
    private Long card;
    private Long collection;
    private Integer quantity;
}
