package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.CollectionDTO;
import br.com.miaulabs.tcgscanner.model.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    // Instância do Mapper
    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    // Métodos de conversão
    @Mapping(source = "user.id", target = "userId")
    CollectionDTO collectionToCollectionDTO(Collection collection);
    @Mapping(source = "userId", target = "user.id")
    Collection collectionDTOToCollection(CollectionDTO collectionDTO);
}