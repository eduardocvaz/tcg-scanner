package br.com.miaulabs.tcgscanner.dto;

import br.com.miaulabs.tcgscanner.model.CollectionItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionItemDTO {

    private Long id;
    private CardDTO card;
    private CollectionItemDTO collection;
    private Integer quantity;
}
