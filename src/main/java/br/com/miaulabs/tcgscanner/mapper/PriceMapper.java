package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.PriceDTO;
import br.com.miaulabs.tcgscanner.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PriceMapper {
    // Instância do Mapper
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    // Métodos de conversão
    PriceDTO priceToPriceDTO(Price price);
    Price priceDTOToPrice(PriceDTO priceDTO);
}