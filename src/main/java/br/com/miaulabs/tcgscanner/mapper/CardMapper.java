package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.CardDTO;
import br.com.miaulabs.tcgscanner.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardMapper {
    // Instância do Mapper
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    // Métodos de conversão
    @Mapping(source = "prices", target = "prices")
    CardDTO cardToCardDTO(Card card);
    @Mapping(source = "prices", target = "prices")
    Card cardDTOToCard(CardDTO cardDTO);
}