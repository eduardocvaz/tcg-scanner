package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.CardDTO;
import br.com.miaulabs.tcgscanner.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardMapper {
    // Instância do Mapper
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    // Métodos de conversão
    CardDTO cardToCardDTO(Card card);
    Card cardDTOToCard(CardDTO cardDTO);
}