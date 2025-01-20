package br.com.miaulabs.tcgscanner.repository;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CardCollection;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CardCollectionRepository extends BaseRepository<CardCollection> {
    // Você pode adicionar métodos customizados, por exemplo, para encontrar por Collection ou Card
    Set<CardCollection> findByCollection(Collection collection);
    Set<CardCollection> findByCard(Card card);
}
