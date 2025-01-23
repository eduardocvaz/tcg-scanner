package br.com.miaulabs.tcgscanner.model;

import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "collection")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseModel {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Set<CollectionItem> collectionItems;
}
