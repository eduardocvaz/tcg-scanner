package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardDTO {

    private Long id;
    private String name;
    private String type;
    private String color;
    private String manaCost;
    private String urlImage;
    private String imageName;

    private List<PriceDTO> prices; // Lista de preços associados à carta
}
