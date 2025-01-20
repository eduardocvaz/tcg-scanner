package br.com.miaulabs.tcgscanner.model;

import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "collection")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseModel {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // Relacionamento com User

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CardCollection> cardCollections;  // Relacionamento com Card_Collection
}
