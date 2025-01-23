package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionItemDTO {

    private Long id;
    private CardDTO card;
    private Integer quantity;
}
