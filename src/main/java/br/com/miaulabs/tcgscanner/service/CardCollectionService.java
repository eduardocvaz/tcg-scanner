package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CollectionItem;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CollectionItemRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CardCollectionService extends BaseService<CollectionItem, CollectionItemRepository> {

    public Set<CollectionItem> findByCard(Card card) {
        return repository.findByCard(card);
    }

}