package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.dto.CollectionItemRequestDTO;
import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CollectionItem;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CardRepository;
import br.com.miaulabs.tcgscanner.repository.CollectionItemRepository;
import br.com.miaulabs.tcgscanner.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CardCollectionService extends BaseService<CollectionItem, CollectionItemRepository> {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CollectionRepository collectionRepository;
    public Set<CollectionItem> findByCard(Card card) {
        return repository.findByCard(card);
    }

    public CollectionItem save(CollectionItemRequestDTO dto) {
        // Recuperar o Card pelo ID
        Card card = cardRepository.findById(dto.getCard())
                .orElseThrow(() -> new IllegalArgumentException("Card not found with ID: " + dto.getCard()));
        Collection collection = collectionRepository.findById(dto.getCollection())
                .orElseThrow(() -> new IllegalArgumentException("Card not found with ID: " + dto.getCard()));
        // Criar um novo CollectionItem
        CollectionItem collectionItem = CollectionItem.builder()
                .card(card)
                .collection(collection)
                .quantity(dto.getQuantity())
                .build();

        // Persistir no banco de dados
        return repository.save(collectionItem);
    }

}