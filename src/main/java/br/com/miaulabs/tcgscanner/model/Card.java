package br.com.miaulabs.tcgscanner.model;

import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "card")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseModel {

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String color;

    @Column
    private String manaCost;

    @Column
    private String urlImage;

    @Column(unique = true) // Garante que imageName seja único
    private String imageName;

    @ElementCollection
    @CollectionTable(name = "price", joinColumns = @JoinColumn(name = "card_id"))
    private List<Price> prices; // Lista de preços associados à carta
}