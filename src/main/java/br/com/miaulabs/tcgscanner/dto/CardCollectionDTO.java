package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardCollectionDTO {

    private Long id;
    private Long cardId;
    private Long collectionId;
    private Integer quantity;
}
