package br.com.miaulabs.tcgscanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {

    private Long id;
    private String name;
    private String type;
    private String color;
    private String manaCost;
    private String urlImage;
}
