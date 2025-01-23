package br.com.miaulabs.tcgscanner.model;

import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Price {

    @Column(nullable = false)
    private String type; // Ex: "Normal" ou "Foil"

    @Column(nullable = false)
    private String minPrice;

    @Column(nullable = false)
    private String avgPrice;

    @Column(nullable = false)
    private String maxPrice;
}
