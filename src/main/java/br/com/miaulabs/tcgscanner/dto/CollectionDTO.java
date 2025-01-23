package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CollectionDTO {
    private Long id;
    private Long userId; // Em vez de expor a entidade User, referenciamos o id do usuário
    private Set<CollectionItemDTO> collectionItems; // Lista dos ids de Card associados à coleção
}
