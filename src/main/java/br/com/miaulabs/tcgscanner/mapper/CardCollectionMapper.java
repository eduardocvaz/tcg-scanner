package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.CardCollectionDTO;
import br.com.miaulabs.tcgscanner.model.CardCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardCollectionMapper {
    // Instância do Mapper
    CardCollectionMapper INSTANCE = Mappers.getMapper(CardCollectionMapper.class);

    // Métodos de conversão
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "collection.id", target = "collectionId")
    CardCollectionDTO cardCollectionToCardCollectionDTO(CardCollection cardCollection);
    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "collectionId", target = "collection.id")
    CardCollection cardCollectionDTOToCardCollection(CardCollectionDTO cardCollectionDTO);
}
