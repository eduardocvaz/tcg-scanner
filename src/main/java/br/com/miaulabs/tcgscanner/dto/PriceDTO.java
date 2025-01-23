package br.com.miaulabs.tcgscanner.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDTO {

    private String type; // Ex: "Normal" ou "Foil"
    private String minPrice;
    private String avgPrice;
    private String maxPrice;
    }