package br.com.miaulabs.tcgscanner.model;

import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CardCollection> cardCollections;  // Relacionamento com Card_Collection
}