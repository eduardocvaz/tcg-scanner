package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CardCollection;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CardCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CardCollectionService extends BaseService<CardCollection, CardCollectionRepository> {

    // Qualquer operação específica do CardCollection pode ser adicionada aqui
    public Set<CardCollection> findByCollection(Collection collection) {
        return repository.findByCollection(collection);
    }

    public Set<CardCollection> findByCard(Card card) {
        return repository.findByCard(card);
    }

}