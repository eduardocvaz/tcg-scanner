package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.CollectionItemDTO;
import br.com.miaulabs.tcgscanner.model.CollectionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CollectionItemMapper {
    // Instância do Mapper
    CollectionItemMapper INSTANCE = Mappers.getMapper(CollectionItemMapper.class);

    // Métodos de conversão
    CollectionItemDTO cardCollectionToCardCollectionDTO(CollectionItem collectionItem);
    CollectionItem cardCollectionDTOToCardCollection(CollectionItemDTO collectionItemDTO);
}
